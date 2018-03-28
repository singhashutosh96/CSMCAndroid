package cn.fanrunqi.materiallogin.Activity.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.fanrunqi.materiallogin.Activity.Instance.Utils;
import cn.fanrunqi.materiallogin.Activity.Instance.VolleyInstance;
import cn.fanrunqi.materiallogin.BuildConfig;
import cn.fanrunqi.materiallogin.R;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername,etPassword;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;
    private AwesomeValidation awesomeValidation;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SPtoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        SPtoken = sharedPreferences.getString("Token","null");
        if(!SPtoken.equals("null")){

            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
            Intent i2 = new Intent(MainActivity.this, ProjectScreen.class);
            startActivity(i2, oc2.toBundle());
            finish();
        }

        String regexpassowrd = getString(R.string.password);
        //Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Validation Rules
        awesomeValidation.addValidation(MainActivity.this, R.id.et_password, regexpassowrd, R.string.err_password);
    }

    private void setListener() {
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                validate();


            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(MainActivity.this, RegisterActivity.class), options.toBundle());
            }
        });
    }

    public  void validate(){

        if(awesomeValidation.validate()){

            CheckCredentials(etUsername.getText().toString(),etPassword.getText().toString());
        }

    }

    public void CheckCredentials(final String name, final String password)
    {
        //Volley Request



        if(!name.isEmpty())
        {

            String loginURL = "http://ec2-13-127-77-185.ap-south-1.compute.amazonaws.com/oauth/token";
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show();

            StringRequest request = new StringRequest(Request.Method.POST, loginURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                    Toast.makeText(MainActivity.this,jsonObject.getString("access_token"), Toast.LENGTH_LONG).show();

                                    editor.putString("Token", jsonObject.getString("access_token"));
                                    editor.commit();
                            }
                            catch (Exception e){}

                            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                            Intent i2 = new Intent(MainActivity.this, ProjectScreen.class);
                            startActivity(i2, oc2.toBundle());
                            finish();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this, Utils.volleyError(error),Toast.LENGTH_LONG).show();

                        }
                    }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();
                    params.put("username",name);
                    params.put("password",password);
                    params.put("grant_type","password");
                    params.put("client_id","2");
                    params.put("client_secret","Pn5HbXq55yVWc5unGuRJPuUaFI6FS7jpV712rpXd");
                    return params;
                }

            };

            VolleyInstance.getInstance(MainActivity.this).addToRequestQue(request);
        }
        else
        {
            Toast.makeText(this, "Name Field Is Empty", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }
}
