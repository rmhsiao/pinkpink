package m.mcoupledate;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import org.json.JSONObject;

import java.util.Arrays;
//test0713
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener  {

    private CallbackManager fbCallbackManager;
    private GoogleApiClient mGoogleApiClient;

    private AQuery mAQuery;

    private String conAPI = "http://140.117.71.216/pinkCon/pinkCon.php";
    RequestQueue mQueue;

    private final int REQ_FB_LOGIN = 64206;
    private final int REQ_GPLUS_LOGIN = 0;

    private TextView mDialog;
    private LoginButton fbLogin;
    private ImageView profilePic;
    private SignInButton gplusLogin;
    private Button gplusLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);

        mAQuery = new AQuery(this);

        mDialog = (TextView) findViewById(R.id.mDialog);
        fbLogin = (LoginButton) findViewById(R.id.fbLogin);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        gplusLogin = (SignInButton) findViewById(R.id.gplusLogin);
        gplusLogout = (Button) findViewById(R.id.gplusLogout);

        gplusLogin.setOnClickListener(this);
        gplusLogout.setOnClickListener(this);

        initFBLoginBtn();
        initGPlusLoginBtn();



    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.gplusLogin:
                gplusLogin();
                break;
            case R.id.gplusLogout:
                gplusLogout();
                break;
//            case R.id.forsql:
////                initUserProfile();
//                break;
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_FB_LOGIN:
                fbCallbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case REQ_GPLUS_LOGIN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
                break;
        }

    }


    protected void initUserProfile(final String id, final String name, final String gender, final String birthday, final String relationDate)
    {

        PinkCon.exec("INSERT INTO `member` VALUES ("+id+", '"+name+"', '"+gender+"', '"+birthday+"', '"+relationDate+"')", mQueue, conAPI);

//        mDialog.setText("INSERT INTO `member` VALUES ("+id+", '"+name+"', '"+gender+"', '"+birthday+"', '"+relationDate+"')");
//        final String sql = "INSERT INTO `member` VALUES ("+id+", '"+name+"', "+gender+", '"+birthday+"', '"+relationDate+"'";
//
//        mDialog.setText(sql);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, conAPI,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
////                        mDialog.setText(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        mDialog.setText(error.getMessage()+"-------------"+error.toString());
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//
//                map.put("exec", sql);
//
//                return map;
//            }
//        };
//
//        mQueue.add(stringRequest);

    }


    protected void initFBLoginBtn()
    {
        fbCallbackManager = CallbackManager.Factory.create();
        fbLogin.setReadPermissions(Arrays.asList("user_birthday"));

        fbLogin.registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

//                AccessToken accessToken = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                String gender;
                                if (object.optString("gender").compareTo("female")==0) gender = "1";
                                else gender = "0";

                                String bd = object.optString("birthday");
                                bd = bd.substring(6, 10)+"-"+bd.substring(0,2)+"-"+bd.substring(3,5);

                                initUserProfile(object.optString("id"), object.optString("name"), gender, bd, "");

//                                mDialog.setText(object.optString("name"));
                            }
                        }
                );

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        });

    }

    protected void initGPlusLoginBtn()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        gplusLogin.setSize(SignInButton.SIZE_STANDARD);
        gplusLogin.setScopes(gso.getScopeArray());

    }

    private void gplusLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQ_GPLUS_LOGIN);
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
//            try {
//                Plus.PeopleApi.load(mGoogleApiClient, acct.getId()).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
//                    @Override
//                    public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
//                        Person me = loadPeopleResult.getPersonBuffer().get(0);
//
//                        mDialog.setText(me.getId()+"---"+me.getDisplayName()+"---"+Integer.toString(me.getGender())+"---"+me.getBirthday());
//                        initUserProfile(me.getId(), me.getDisplayName(), Integer.toString(me.getGender()), me.getBirthday(), "");
//
//                    }
//                });
//            }catch(Exception e){
//
//
//                mDialog.setText(e.toString()+"----"+e.getMessage());
//            }
            // sdasdsadsadas
            mDialog.setText(acct.getPhotoUrl().toString());
//            mAQuery.id(profilePic).image(acct.getPhotoUrl().toString(), true, true, 0, android.R.drawable.ic_menu_gallery);
            initUserProfile(acct.getId(), acct.getDisplayName(), "", null, null);
//            mDialog.setText(acct.getPhotoUrl().toString());
        } else {
            // Signed out, show unauthenticated UI.
            mDialog.setText("login fail");
//            mDialog.setText();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this, REQ_GPLUS_LOGIN);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            mDialog.setText(e.getMessage());
        }
    }

    private void gplusLogout() {
        try {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mDialog.setText("gplus out");
        } catch (Exception e) {
            mDialog.setText(e.getMessage());
        }
    }

}
