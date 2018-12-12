package com.phone.abhayrastogi.phone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikelau.croperino.Croperino;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FormActivity extends AppCompatActivity {
    private Button mSavebutton,mcheck;
    public String temporary;
    Uri sourceUri,destinationUri=null;
    private StorageReference mStorageRef;
    private CircleImageView setupImage;
    String      user_no,user_id;
    Uri resultUri;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    Uri downloadUrl;
    private StorageReference storageReference;
    ProgressBar progressBar;
    EditText textn;
    TextView textView;
    String pin;
    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        setupImage = findViewById(R.id.setup_image);

        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        user_no = firebaseAuth.getCurrentUser().getPhoneNumber();
        user_id = firebaseAuth.getCurrentUser().getUid();
        myRef = mFirebaseDatabase.getReference().child("users");

        progressBar=(ProgressBar)findViewById(R.id.progressBar4);

        textn=(EditText)findViewById(R.id.field_name);
        storageReference = FirebaseStorage.getInstance().getReference();
        progressBar.setVisibility(View.INVISIBLE);

        mSavebutton=(Button)findViewById(R.id.saveData);


        db = FirebaseFirestore.getInstance();


        SharedPreferences mPreferences;
        mPreferences = FormActivity.this.getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        temporary = mPreferences.getString("saveuserid", "");


        if(temporary!= null && !temporary.isEmpty()) {
            mSavebutton.setText("Save");
        }



            setupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(FormActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(FormActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(FormActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                        BringImagePicker();

                    }

                } else {

                    BringImagePicker();


                }

            }

        });



                mSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* String uname=name.getText().toString();
                String bname=bussinessname.getText().toString();
                myRef.child(userID).child("username").setValue(uname);


                myRef.child(userID).child("business").setValue(bname);*/


             String name=textn.getText().toString();


                Map<String, Object> put= new HashMap<>();
                put.put("name", name);
                db.collection("users").document(user_no).set(put,SetOptions.merge());
             if(!name.equals("")){







                 if(temporary!= null && !temporary.isEmpty()){

                     Intent y = new Intent(FormActivity.this,  MainActivity.class);
                     y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(y);

                 }

                 else{


                     Intent y = new Intent(FormActivity.this,  CityActivity.class);
                     y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(y);

                 }



               //  myRef.child(user_no).child("name").setValue(name);

             }else {
                 textn.setError("Enter name");
             }




             /*   setupImage.setDrawingCacheEnabled(true);
                setupImage.buildDrawingCache();
                Bitmap bitmap = setupImage.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
*/
               // UploadTask uploadTask = mountainsRef.putBytes(data);

             //   myRef.child(user_no).child("imageurl").setValue(downloadpic);




            }
        });


    }



    private void Imagesave(){
        final StorageReference image_path = storageReference.child("profile_images").child(user_id + ".jpg");

        image_path.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if (task.isSuccessful()) {
                    // storeFirestore(task, user_name);



                    downloadUrl = task.getResult().getDownloadUrl();


                    String downloadpic= String.valueOf(downloadUrl);



                    Map<String, Object> put= new HashMap<>();
                    put.put("imageurl", downloadpic);
                    db.collection("users").document(user_no).set(put, SetOptions.merge());


                    //
                    // Toast.makeText(FormActivity.this, downloadpic, Toast.LENGTH_LONG).show();

                  //  myRef.child(user_no).child("imageurl").setValue(downloadpic);
                   /* Glide.with(FormActivity.this)



                            .load(downloadUrl)

                            .into(setupImage);




                    mSavebutton.setVisibility(View.VISIBLE);


                  if(setupImage.getDrawable()!=null){

                  }
*/                  mSavebutton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);


                } else {

                    String error = task.getException().getMessage();
                           /* Toast.makeText(SetupActivity.this, "(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();

                            setupProgress.setVisibility(View.INVISIBLE);*/

                }
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                //downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();

            }
        });



    }



    private void BringImagePicker() {




        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(FormActivity.this);
      /*  UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(16, 9)

                .start(FormActivity.this);*/
       // Croperino.prepareChooser(FormActivity.this, "Capture photo...", ContextCompat.getColor(FormActivity.this, android.R.color.background_dark));

      //  Croperino.prepareGallery(FormActivity.this);

     progressBar.setVisibility(View.VISIBLE);
     mSavebutton.setVisibility(View.GONE);













    }









    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
               resultUri = result.getUri();
                setupImage.setImageURI(resultUri);

                Imagesave();


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }




}
