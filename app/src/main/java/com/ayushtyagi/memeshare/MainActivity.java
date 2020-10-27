package com.ayushtyagi.memeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView ivmeme;
    Button btnshare,btnnext;
    String memeurl;
    //SimpleArcLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivmeme=findViewById(R.id.ivmeme);
        btnnext=findViewById(R.id.btnnext);
        btnshare=findViewById(R.id.btnshare);
        //loader=findViewById(R.id.loader);
        loadmeme();
    }
    private void  loadmeme(){
      //  loader.start();
      //  loader.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://meme-api.herokuapp.com/gimme";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            memeurl = response.getString("url");
                            Glide.with(MainActivity.this).load(memeurl).into(ivmeme);
//                            loader.stop();
                         //   loader.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });




        queue.add(jsonObjectRequest);
    }


    public void Sharememe(View view) {
        Intent intent =new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"Check this cool meme "+ memeurl);
        intent.setType("text/plain");
        Intent chosser = Intent.createChooser(intent,"Share meme via...");
        startActivity(chosser);

    }

    public void nextmeme(View view) {
        loadmeme();
    }
}
