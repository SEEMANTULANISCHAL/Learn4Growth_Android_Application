package com.example.smartlearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    ActionBar actionBar;
    Button btnadd,btnclear;
    LinearLayout loutuser;
    EditText etname,etemail,etpassword,etmobile,vnum,roll;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private static final String URL="http://wizzie.tech/learn4growth/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        vnum=findViewById(R.id.vn);
        roll=findViewById(R.id.rill);

        btnadd = (Button)findViewById(R.id.btnadd);
        btnclear = (Button)findViewById(R.id.btnclear);
        loutuser = (LinearLayout)findViewById(R.id.loutuser);

        etname = (EditText)findViewById(R.id.etname);
        etemail = (EditText)findViewById(R.id.etemail);
        etpassword = (EditText)findViewById(R.id.etpassword);


        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#2196F3"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        actionBar.setTitle(Html.fromHtml("<small><font color='#FFFFFF'>Signup</font></small>"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.appcolor));
        }
        String eml=etemail.getText().toString().trim();

        /*etemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (eml.matches(emailPattern) && editable.length() > 0)
                {
                    Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                    // or
                    etemail.setText("valid email");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                    //or
                    etemail.setText("invalid email");
                }
            }
        });*/

        btnadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String eml=etemail.getText().toString().trim();

                    /*if(etname.getText().toString().isEmpty() || etemail.getText().toString().isEmpty() || etpassword.getText().toString().isEmpty() ||
                            etmobile.getText().toString().isEmpty()){*/

                        if(etname.getText().toString().isEmpty()){
                            etname.setError("Fill the details properly");
                            etname.requestFocus();
                        }else if(etemail.getText().toString().isEmpty()){
                            etemail.setError("Fill the details properly");
                            etemail.requestFocus();
                        }
                        else if(etpassword.getText().toString().isEmpty()){
                            etpassword.setError("Fill the details properly");
                            etpassword.requestFocus();
                        }else if(etmobile.getText().toString().isEmpty()){
                            etmobile.setError("Fill the details properly");
                            etmobile.requestFocus();
                        }
                        else if(!Patterns.EMAIL_ADDRESS.matcher(eml).matches()){
                            etemail.setError("Fill Valid Email Address");
                            etemail.requestFocus();
                        }


                   /* }*/
                    else{
                        final ProgressDialog pdd = new ProgressDialog(Signup.this);
                        pdd.setMessage("Please Wait, While We Add");
                        pdd.setCancelable(false);
                        pdd.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                       // Toast.makeText(Signup.this, ""+response, Toast.LENGTH_SHORT).show();
                                        pdd.dismiss();
                                       // System.out.println(response);
                                        JSONObject object = null;
                                        try {
                                            object = new JSONObject(response);
                                            if (object.getString("result").equalsIgnoreCase("you are registered successfully")) {
                                                Intent i = new Intent(getApplicationContext(),Login.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override

                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();

                                params.put("nm", etname.getText().toString().trim());
                                params.put("em", etemail.getText().toString().trim());
                                params.put("ps", etpassword.getText().toString().trim());
                                params.put("mb",etmobile.getText().toString().trim());

                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);

                 }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}