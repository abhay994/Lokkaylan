package com.phone.abhayrastogi.phone;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public String temporary;
    public String userid;
    String ccity;
    public static int cx,cy ;
    private FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private static DatabaseReference myRef;
    private int MODE_PRIVATE;
    TextView textView;
    RecyclerView recyclerView;
    DatabaseReference mdatabase;
    FirebaseFirestore db ;
    RecyclerViewAdapter recyclerViewAdapter;
    Button bh,pu;
    CircleImageView propic;
    TextView citytxt,cattxt;
    LinearLayout pprofile,procity,proc;
    private List<ObjectRecycle> userslist;
    ObjectRecycle objectRecycle1;

    CardView ctutor,cdriver,chealth,ccook,clelec,cbeauty,cplumber,cother,crepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startauthentication();

        setContentView(R.layout.activity_main);



        db = FirebaseFirestore.getInstance();
       /* Toolbar toolbar=(Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

*/   phonepermistion();

       procity=(LinearLayout)findViewById(R.id.procity);
        proc=(LinearLayout)findViewById(R.id.cpro);
       pprofile=(LinearLayout)findViewById(R.id.profile);
       propic=(CircleImageView)findViewById(R.id.ppic);
        recyclerView = (RecyclerView) findViewById(R.id.rec);

        citytxt=(TextView)findViewById(R.id.citytext);

        cattxt=(TextView)findViewById(R.id.categorytxt);


        clelec=(CardView)findViewById(R.id.ele);
        cbeauty=(CardView)findViewById(R.id.beauty);
        chealth=(CardView)findViewById(R.id.health);
        ccook=(CardView)findViewById(R.id.cook);
        ctutor=(CardView)findViewById(R.id.tutor);
        cdriver=(CardView)findViewById(R.id.driver);
        cplumber=(CardView)findViewById(R.id.plumber);
        cother=(CardView)findViewById(R.id.other);
        crepair=(CardView)findViewById(R.id.repair);




        clelec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cityandcategory("electrican");


            }
        });


        cbeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityandcategory("beauty");


            }
        });

        chealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cityandcategory("health");


            }
        });

        ccook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityandcategory("cook");

            }
        });

        ctutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityandcategory("tutor");

            }
        });

        cdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityandcategory("driver");

            }
        });
        cplumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityandcategory("plumber");

            }
        });

        crepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityandcategory("repair");

            }
        });

        cother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityandcategory("other");

            }
        });









        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent y = new Intent(MainActivity.this, CategroyActivity.class);

                startActivity(y);


            }
        });




        procity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent y = new Intent(MainActivity.this, CityActivity.class);

                startActivity(y);


            }
        });




        pprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent y = new Intent(MainActivity.this, FormActivity.class);

                startActivity(y);


            }
        });






        SharedPreferences mPreferences;
        mPreferences = MainActivity.this.getSharedPreferences("User", MODE_PRIVATE);
       // SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        temporary = mPreferences.getString("saveuserid", "");
        CollectionReference citiesRef = db.collection("users");
        Query query = citiesRef.whereEqualTo("contact", temporary);


        if(temporary!= null && !temporary.isEmpty()){





            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {


                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            objectRecycle1 = doc.getDocument().toObject(ObjectRecycle.class);
                            String imagee = objectRecycle1.getImageurl();
                            ccity = objectRecycle1.getCity();
                            citytxt.setText(ccity);
                            String ccategory = objectRecycle1.getCategory();
                            cattxt.setText(ccategory);
                            Glide.with(MainActivity.this).load(imagee).into(propic
                            );


                            recyclerviewParserStart(objectRecycle1.getCity());



                        }

                    }

                }

            });


        }



        mdatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mdatabase.keepSynced(true);
       // textView = (TextView) findViewById(R.id.textview1);
       /* textView.setText(temporary);*/












/*
        bh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userslist= new ArrayList<>();
                recyclerViewAdapter =new RecyclerViewAdapter(userslist);



                GridLayoutManager horizontalLayoutManagaer = new GridLayoutManager(MainActivity.this, 1);

               // recyclerView = (RecyclerView) findViewById(R.id.rec);
                recyclerView.setLayoutManager(horizontalLayoutManagaer);
                recyclerView.setAdapter(recyclerViewAdapter);
                CollectionReference citiesRef = db.collection("users");
                Query query = citiesRef.whereEqualTo("city", "bahraich").whereEqualTo("category","tutor");


                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {



                        for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                ObjectRecycle objectRecycle=doc.getDocument().toObject(ObjectRecycle.class);
                                userslist.add(objectRecycle);
                                recyclerViewAdapter.notifyDataSetChanged();
                            }






                        }

                    }
                });
            }
        });



        pu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userslist= new ArrayList<>();
                recyclerViewAdapter =new RecyclerViewAdapter(userslist);



                GridLayoutManager horizontalLayoutManagaer = new GridLayoutManager(MainActivity.this, 1);


                recyclerView.setLayoutManager(horizontalLayoutManagaer);
                recyclerView.setAdapter(recyclerViewAdapter);

                CollectionReference citiesRef = db.collection("users");
                Query query = citiesRef.whereEqualTo("city", "pune").whereEqualTo("category","driver");


                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {



                        for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                ObjectRecycle objectRecycle=doc.getDocument().toObject(ObjectRecycle.class);
                                userslist.add(objectRecycle);
                                recyclerViewAdapter.notifyDataSetChanged();
                            }






                        }

                    }
                });
            }
        });*/







    }
/*
        if(temporary!= null && !temporary.isEmpty()) {

            mAuth = FirebaseAuth.getInstance();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            userid = currentFirebaseUser.getUid();
            textView.setText(temporary);

        } else
        {


        }*/


//        Query firebaseQuer=mdatabase.orderByValue().equalTo("abhay").endAt("abhay");


    /*    FirebaseRecyclerAdapter<ObjectRecycle,BloggerView> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<ObjectRecycle, BloggerView>(
                ObjectRecycle.class,
                R.layout.workcard,
                BloggerView.class,
                firebaseQuer

        ) {
            @Override
            protected void populateViewHolder(BloggerView viewHolder, ObjectRecycle model, int position) {
                viewHolder.setCategory(model.getCategory());
                viewHolder.setCity(model.getCity());
                viewHolder.setName(model.getName());
                viewHolder.setContact(model.getContact());
                viewHolder.setImg(getApplicationContext(),model.getImageurl());

            }
        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);


















    }
*/


    public void startauthentication(){

        SharedPreferences mPreferences;
        mPreferences = getSharedPreferences("User", MODE_PRIVATE);

        temporary = mPreferences.getString("saveuserid", "");


        if(temporary!= null && !temporary.isEmpty()){


        /*    CollectionReference citiesRef = db.collection("users");
            Query query = citiesRef.whereEqualTo("contact", temporary);


            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {


                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            ObjectRecycle objectRecycle1 = doc.getDocument().toObject(ObjectRecycle.class);
                            String imagee=objectRecycle1.getImageurl();
                            ccity=objectRecycle1.getCity();
                            citytxt.setText(ccity);
                            String ccategory=objectRecycle1.getCategory();
                            cattxt.setText(ccategory);
                            Glide.with(MainActivity.this).load(imagee).into(propic
                            );


                        }


                    }

                }

            });*/


            //recyclerviewParserStart(ccity);
        }

        else{


            Intent y = new Intent(MainActivity.this, Main2Activity.class);
            y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(y);

        }
    }


    public void signoutbutton(View s) {
        if (s.getId() == R.id.sign_out) {



            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you really want to Log Out ?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            SharedPreferences mPreferences;

                            mPreferences = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mPreferences.edit();
                            editor.clear();
                            editor.apply();
                            mAuth.signOut();

                            Intent y = new Intent(MainActivity.this, MainActivity.class);
                            y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(y);



                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setTitle("Confirm");
            dialog.show();


        }
    }






public void recyclerviewParser(String city,String category) {


    userslist = new ArrayList<>();
    recyclerViewAdapter = new RecyclerViewAdapter(userslist);


    GridLayoutManager horizontalLayoutManagaer = new GridLayoutManager(MainActivity.this, 1);


    recyclerView.setLayoutManager(horizontalLayoutManagaer);
    recyclerView.setAdapter(recyclerViewAdapter);

    CollectionReference citiesRef = db.collection("users");
    Query query = citiesRef.whereEqualTo("city", city).whereEqualTo("category", category);


    query.addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {


            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                if (doc.getType() == DocumentChange.Type.ADDED) {
                    ObjectRecycle objectRecycle = doc.getDocument().toObject(ObjectRecycle.class);
                    userslist.add(objectRecycle);
                    recyclerViewAdapter.notifyDataSetChanged();
                }


            }

        }


    });


}
    public void recyclerviewParserStart(String city) {


        userslist = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(userslist);


        GridLayoutManager horizontalLayoutManagaer = new GridLayoutManager(MainActivity.this, 1);


        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setAdapter(recyclerViewAdapter);

        CollectionReference citiesRef = db.collection("users");
        Query query = citiesRef.whereEqualTo("city", city);


        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {


                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        ObjectRecycle objectRecycle = doc.getDocument().toObject(ObjectRecycle.class);
                        userslist.add(objectRecycle);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }


                }

            }


        });


}


   void cityandcategory(final String categorycanc) {

       CollectionReference citiesRef = db.collection("users");
       Query query = citiesRef.whereEqualTo("contact", temporary);

       query.addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {


               for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                   if (doc.getType() == DocumentChange.Type.ADDED) {




                       recyclerviewParser(objectRecycle1.getCity(),categorycanc);



                   }

               }

           }

       });

  }

  public void phonepermistion(){
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

          if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

              Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
              ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

          }
      }

    }

}
