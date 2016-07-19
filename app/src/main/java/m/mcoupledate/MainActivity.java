package m.mcoupledate;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener  {

    private CallbackManager fbCallbackManager;
    private GoogleApiClient mGoogleApiClient;

    private AQuery mAQuery;

    private String conAPI = "http://140.117.71.216/pinkCon/";
    RequestQueue mQueue;

    private final int REQ_FB_LOGIN = 64206;
    private final int REQ_GPLUS_LOGIN = 0;

    private TextView mDialog;
    private ImageView profilePic;
    private SignInButton gplusLogin;
    private Button gplusLogout;
    private ImageButton fbLogin, fbLogout;

    //存使用者ID
    private static String id;


    //  初始化頁面和變數設定
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);

        mAQuery = new AQuery(this);

        mDialog = (TextView) findViewById(R.id.mDialog);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        gplusLogin = (SignInButton) findViewById(R.id.gplusLogin);
        gplusLogout = (Button) findViewById(R.id.gplusLogout);
        fbLogin = (ImageButton) findViewById(R.id.fbLogin);
        fbLogout = (ImageButton) findViewById(R.id.fbLogout);

        gplusLogin.setOnClickListener(this);
        gplusLogout.setOnClickListener(this);
        fbLogin.setOnClickListener(this);
        fbLogout.setOnClickListener(this);

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
            case R.id.fbLogin:
                fbLogin();
                break;
            case R.id.fbLogout:
                fbLogout();
                break;
        }

    }



    //  當FB或GooglePlus傳回結果時在此操作
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


    //  將使用者資訊存入資料庫
    protected void initUserProfile(final String id, final String name, final String gender, final String birthday, final String relationDate)
    {
        PinkCon.exec("INSERT INTO `member` VALUES ('"+id+"', '"+name+"', '"+gender+"', '"+birthday+"', '"+relationDate+"')", mQueue, conAPI+"pinkCon.php");
    }


    //  初始化FB登入按鈕
    protected void initFBLoginBtn()
    {
        fbCallbackManager = CallbackManager.Factory.create();
//        fbLogin.setReadPermissions(Arrays.asList("user_birthday, user_likes, user_tagged_places"));

        LoginManager.getInstance().registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(final JSONObject object, GraphResponse response) {

                                //使用者ID
                                id = object.optString("id");

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, conAPI+"fbLogin.php",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                mDialog.setText(response);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                mDialog.setText(error.getMessage()+"-----"+error.toString());
                                            }
                                        }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> map = new HashMap<String, String>();

                                        map.put("fbUser", object.toString());

                                        return map;
                                    }
                                };

                                mQueue.add(stringRequest);
                                // 設定從這個活動跳至 home 的活動
                                Intent intent = new Intent(MainActivity.this, Home.class);
                                // 開始跳頁
                                startActivity(intent);
                            }
                        });

                Bundle params = new Bundle();
                params.putString("fields", "id,name,gender,birthday,likes{id,name,about,description,location{latitude,longitude,street},phone,public_transit,emails,website,category},tagged_places{place{id,name,about,description,location{latitude,longitude,street},phone,public_transit,emails,website,category}}");
                params.putString("locale", "zh_TW");
                request.setParameters(params);
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


    protected void fbLogin()
    {
        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("user_birthday, user_likes, user_tagged_places"));

        /*
                            登入後結果
                            在 initFBLoginBtn() 的 onSuccess中處理

         */
    }


    protected void fbLogout()
    {
        LoginManager.getInstance().logOut();

        /*
                            在此處理F登出後結果

         */
    }

    //  初始化GooglePlus登入按鈕
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

    //  GooglePlus登入
    private void gplusLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQ_GPLUS_LOGIN);
    }

    //  GooglePlus處理登入結果
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
            mDialog.setText(acct.getPhotoUrl().toString());
//            mAQuery.id(profilePic).image(acct.getPhotoUrl().toString(), true, true, 0, android.R.drawable.ic_menu_gallery);
            initUserProfile(acct.getId(), acct.getDisplayName(), "", null, null);


            /**
             *       GooglePlus 登入成功後在此處理內容
             * */


//            mDialog.setText(acct.getPhotoUrl().toString());
        } else {
            // Signed out, show unauthenticated UI.
            mDialog.setText("login fail");
//            mDialog.setText();
        }
    }

    // GooglePlus 登入失敗處理
    public void onConnectionFailed(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this, REQ_GPLUS_LOGIN);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            mDialog.setText(e.getMessage());
        }
    }

    //  GooglePlus 登出
    private void gplusLogout() {
        try {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);

            /**
             *          登出後在此處理
             */
            mDialog.setText("gplus out");
        } catch (Exception e) {
            mDialog.setText(e.getMessage());
        }
    }

    //給其他頁面要求使用者id
    public static String getUserId(){
        return id;
    }
}
