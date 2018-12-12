package com.phone.abhayrastogi.phone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class CategroyActivity extends AppCompatActivity {
 CardView ctutor,cdriver,chealth,ccook,clelec,cbeauty,cplumber,cother,crepair;
     FirebaseAuth firebaseAuth;
     FirebaseDatabase mFirebaseDatabase;
     DatabaseReference myRef;
      String      user_no,user_id;
    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categroy);
         clelec=(CardView)findViewById(R.id.ele);
         cbeauty=(CardView)findViewById(R.id.beauty);
         chealth=(CardView)findViewById(R.id.health);
         ccook=(CardView)findViewById(R.id.cook);
         ctutor=(CardView)findViewById(R.id.tutor);
         cdriver=(CardView)findViewById(R.id.driver);
         cplumber=(CardView)findViewById(R.id.plumber);
         cother=(CardView)findViewById(R.id.other);
         crepair=(CardView)findViewById(R.id.repair);

        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        user_no = firebaseAuth.getCurrentUser().getPhoneNumber();
        user_id = firebaseAuth.getCurrentUser().getUid();



        myRef = mFirebaseDatabase.getReference().child("users");
        db = FirebaseFirestore.getInstance();
        clelec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> put= new HashMap<>();
                put.put("category", "electrican");
                db.collection("users").document(user_no).set(put, SetOptions.merge());
                sharedprefernce();

            }
        });


        cbeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ;
                Map<String, Object> put= new HashMap<>();
                put.put("category", "beauty");
                db.collection("users").document(user_no).set(put, SetOptions.merge());
                sharedprefernce();
            }
        });

        chealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> put= new HashMap<>();
                put.put("category", "health");
                db.collection("users").document(user_no).set(put, SetOptions.merge());
                sharedprefernce();
            }
        });

        ccook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> put= new HashMap<>();
                put.put("category", "cook");
                db.collection("users").document(user_no).set(put, SetOptions.merge());
                sharedprefernce();
            }
        });

        ctutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> put= new HashMap<>();
                put.put("category", "tutor");
                db.collection("users").document(user_no).set(put, SetOptions.merge());
                sharedprefernce();
            }
        });

        cdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> put= new HashMap<>();
                put.put("category", "driver");
                db.collection("users").document(user_no).set(put, SetOptions.merge());
                sharedprefernce();
            }
        });
        cplumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> put= new HashMap<>();
                put.put("category", "plumber");
                db.collection("users").document(user_no).set(put, SetOptions.merge());
                sharedprefernce();
            }
        });

        crepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> put= new HashMap<>();
                put.put("category", "repair");
                db.collection("users").document(user_no).set(put, SetOptions.merge());
                sharedprefernce();
            }
        });

        cother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> put= new HashMap<>();
                put.put("category", "other");
                db.collection("users").document(user_no).set(put, SetOptions.merge());
                sharedprefernce();
            }
        });

    }

  private   void sharedprefernce(){

        SharedPreferences mPreferences;





        mPreferences = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("saveuserid", user_no);
        editor.commit();
     //String temporary = mPreferences.getString("saveuserid", "");


      Intent y = new Intent(CategroyActivity.this,  MainActivity.class);
      y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
      startActivity(y);


    }


}
