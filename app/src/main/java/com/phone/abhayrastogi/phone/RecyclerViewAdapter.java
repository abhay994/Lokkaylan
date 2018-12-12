package com.phone.abhayrastogi.phone;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by abhayrastogi on 31/03/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BloggerView> {

    public List<ObjectRecycle> usersList;
    View view;

    Context context;

    public RecyclerViewAdapter(List<ObjectRecycle> usersList) {
        this.usersList = usersList;


    }

    @Override
    public BloggerView onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workcard, parent, false);


        return new BloggerView(view);
    }

    @Override
    public void onBindViewHolder(final BloggerView holder, int position) {
        holder.setName(usersList.get(position).getName());
        holder.setCategory(usersList.get(position).getCategory());
        holder.setCity(usersList.get(position).getCity());
        holder.setContact(usersList.get(position).getContact());
        holder.setImg(context, usersList.get(position).getImageurl());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Toast.makeText(context,holder.cont.getText().toString(),Toast.LENGTH_LONG).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + holder.cont.getText().toString()));
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }else {
                    context.startActivity(callIntent);

                }




             }
         });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public  class BloggerView extends RecyclerView.ViewHolder{
        View mView;
        ImageView imageView;
        CircleImageView circleImageView;
        TextView cont,nam,cit,cat;
        public BloggerView(View itemView) {
            super(itemView);

            mView=itemView;
            imageView=(ImageView)mView.findViewById(R.id.phonecall);
        }





        public void setCategory(String ccategory){
            cat=(TextView)mView.findViewById(R.id.category);
            cat.setText(ccategory);



        }



        public void setCity(String ccity){
            cit=(TextView)mView.findViewById(R.id.cccity);
            cit.setText(ccity);



        }





        public void setName(String cname){
            nam=(TextView)mView.findViewById(R.id.name);
            nam.setText(cname);



        }


        public void setContact(String ccontact){
            cont=(TextView)mView.findViewById(R.id.contact);
            cont.setText(ccontact);



        }


        public void setImg(Context cnt, String image) {
            circleImageView=(CircleImageView)mView.findViewById(R.id.ppic);

            Glide.with(cnt)

                    .load(image)


                    .into(circleImageView)
            ;



        }


    }
    public void removeItem(ObjectRecycle infoData) {

        int currPosition = usersList.indexOf(infoData);

       /* usersList.remove(currPosition);
        notifyItemRemoved(currPosition);*/

    }



}
