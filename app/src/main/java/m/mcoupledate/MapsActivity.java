package m.mcoupledate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import m.mcoupledate.classes.ClusterSite;
import m.mcoupledate.classes.ClusterSiteRenderer;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener,
        ClusterManager.OnClusterClickListener<ClusterSite>,
        ClusterManager.OnClusterInfoWindowClickListener<ClusterSite>,
        ClusterManager.OnClusterItemClickListener<ClusterSite>,
        ClusterManager.OnClusterItemInfoWindowClickListener<ClusterSite> {

    private GoogleMap mMap;
    private ClusterManager<ClusterSite> mClusterManager;

    private Context context;

    RequestQueue mQueue;
    String pinkCon = "http://140.117.71.216/pinkCon/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mQueue = Volley.newRequestQueue(this);

        context = getApplicationContext();

    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {
        LatLng y = new LatLng(22.797169, 120.30421);

        String origns, dest;
        origns = y.latitude+","+y.longitude;
        dest = latLng.latitude+","+latLng.longitude;

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=walking&origins="+origns+"&destinations="+dest+"|22.794169,120.30821&language=zh-TW&key=AIzaSyBcFbgQq0C0q-HLvjciZIhRjzy4yx0zV7c";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONArray jArr = new JSONObject(response).getJSONArray("rows").getJSONObject(0).getJSONArray("elements");

                            JSONObject o = jArr.getJSONObject(0);

                            Toast.makeText(MapsActivity.this, o.getJSONObject("distance").optString("text")+" - "+o.getJSONObject("duration").optString("text"), Toast.LENGTH_LONG).show();
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(MapsActivity.this, e.getMessage()+"-----"+e.toString()+"--vvv", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivity.this, error.getMessage()+"-----"+error.toString()+"--vvv", Toast.LENGTH_LONG).show();
                    }
                });

        mQueue.add(stringRequest);


//        mClusterManager.addItem(new ClusterSite(latLng, "123", context));
//        mClusterManager.cluster();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMapLongClickListener(this);

        LatLng y = new LatLng(22.797169, 120.30421);
        mMap.addMarker(new MarkerOptions().position(y).title("Y"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(y, 10));



        setUpClusterer();
    }

    private void setUpClusterer()
    {
        mClusterManager = new ClusterManager<ClusterSite>(this, mMap);

        mClusterManager.setRenderer(new ClusterSiteRenderer(MapsActivity.this, mMap, mClusterManager));

        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager); //  當點擊資訊視窗時引發事件

        /*
                 *  當點擊群集時引發事件
                 */
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        markSites();

        setPolyline();
    }


    public void markSites()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, pinkCon+"getSites.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONArray jArr = new JSONArray(response);
                            JSONObject o;

                            for (int a=0; a<jArr.length(); ++a)
                            {
                                o = jArr.getJSONObject(a);
//                                mMap.addMarker(new MarkerOptions().position(new LatLng(o.optDouble("Py"), o.optDouble("Px"))).title(o.optString("sName")));
//                                if (a>800)
//                                    break;

                                setOneMarker(o);
                            }

                            mClusterManager.cluster();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MapsActivity.this, e.getMessage()+"-----"+e.toString()+"--vvv", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivity.this, error.getMessage()+"-----"+error.toString()+"--vvv", Toast.LENGTH_LONG).show();
                    }
                });

        mQueue.add(stringRequest);

    }

    private void setOneMarker(final JSONObject o)
    {

        Glide.with(context)
                .load(pinkCon + "images/sitePic/" + o.optString("picId") + ".jpg")
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(50, 50)
                      {
                          @Override
                          public void onResourceReady(Bitmap bitmap, GlideAnimation anim)
                          {
                              mClusterManager.addItem(new ClusterSite(new LatLng(o.optDouble("Py"), o.optDouble("Px")), o.optString("sName"), bitmap));
                          }
                          @Override
                          public void onLoadFailed(Exception e, Drawable errorDrawable)
                          {
                              //                            mClusterManager.addItem(new ClusterSite(new LatLng(o.optDouble("Py"), o.optDouble("Px")), o.optString("sName"), context));
                          }

                      }
                );
    }

    private void setPolyline()
    {
        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(22.633815, 120.3144))
                .add(new LatLng(22.631362, 120.301087))  // North of the previous point, but at the same longitude
                .add(new LatLng(22.633011, 120.301568))
                .color(Color.BLUE)
                .geodesic(true);  // Same latitude, and 30km to the west
// Get back the mutable Polyline
        Polyline polyline = mMap.addPolyline(rectOptions);
    }


    @Override
    public boolean onClusterClick(Cluster<ClusterSite> cluster)
    {
        // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().name;
        Toast.makeText(this,  firstName+"和"+(cluster.getSize()-1)+"個景點", Toast.LENGTH_SHORT).show();

        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
        // inside of bounds, then animate to center of the bounds.

        // Create the builder to collect all essential cluster items for the bounds.
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();

        // Animate camera to the bounds
        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<ClusterSite> cluster) {
        // Does nothing, but you could go to a list of the users.
    }

    @Override
    public boolean onClusterItemClick(ClusterSite item) {
        // Does nothing, but you could go into the user's profile page, for example.
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(ClusterSite item) {
        // Does nothing, but you could go into the user's profile page, for example.
    }
}
