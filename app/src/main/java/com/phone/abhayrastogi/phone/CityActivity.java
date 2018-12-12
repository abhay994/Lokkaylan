package com.phone.abhayrastogi.phone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CityActivity extends AppCompatActivity {
private  TextView textView;
private  EditText text1;
private  String pin,idname;
private  Button bckech,bnext;

    public String temporary;
private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
  private   String      user_no,user_id;
    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        SharedPreferences mPreferences;
        mPreferences = getSharedPreferences("User", MODE_PRIVATE);

        temporary = mPreferences.getString("saveuserid", "");






            bckech=(Button)findViewById(R.id.savecity);
       bnext=(Button) findViewById(R.id.next);
        textView=(TextView)findViewById(R.id.field_city);
        text1=(EditText)findViewById(R.id.field_pin);
        bnext.setVisibility(View.GONE);
        progressBar=(ProgressBar)findViewById(R.id.pcity);
        db = FirebaseFirestore.getInstance();


        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        user_no = firebaseAuth.getCurrentUser().getPhoneNumber();
        user_id = firebaseAuth.getCurrentUser().getUid();
        myRef = mFirebaseDatabase.getReference().child("users");

        bckech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);

                //textView.setVisibility(View.VISIBLE);
                pin=text1.getText().toString();
                new GetContacts().execute("pin");



            }
        });

        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ccity=textView.getText().toString();
               // myRef.child(user_no).child("city").setValue(ccity);

                Map<String, Object> put= new HashMap<>();
                put.put("city", ccity);
                db.collection("users").document(user_no).set(put, SetOptions.merge());







                if(temporary!= null && !temporary.isEmpty()){

                    Intent y = new Intent(CityActivity.this,  MainActivity.class);
                    y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(y);

                }

                else{


                    Intent y = new Intent(CityActivity.this,  CategroyActivity.class);
                    y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(y);

                }








            }
        });
    }


    private  class GetContacts extends AsyncTask<String, String,String> {
        private String resp;
        private String name;

        @Override
        protected void onPreExecute() {
            bnext.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

        }



        @Override
        protected String doInBackground(String... strings) {

            String pincode=strings[0];
            HttpHandler sh = new HttpHandler();

            String url="http://postalpincode.in/api/pincode/"+pin;

            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     name = jsonObj.optString("Status");

                    if(name.equals("Success")){

                       /* mSavebutton.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);*/


                        JSONArray contacts = jsonObj.getJSONArray("PostOffice");
                        JSONObject c = contacts.getJSONObject(0);
                        resp = c.getString("Name");



                        //progressBar.setVisibility(View.INVISIBLE);


                    }else {
                       // text1.setError("invalid pin");
                    }



                }catch (Exception e){
                    e.printStackTrace();
                }



            }






            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
           // progressDialog.dismiss();
            progressBar.setVisibility(View.GONE);


            textView.setText(result);
            bnext.setVisibility(View.VISIBLE);

            if(temporary!= null && !temporary.isEmpty()) {
                bnext.setText("Save");

            }



            if(!name.equals("Success")){
                text1.setError("Invalid pincode");
                bnext.setVisibility(View.GONE);
            }

        }


    }
private  void Visible(){
    //textView.setText(idname);

}

}
