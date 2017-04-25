package com.example.rajatha.rcameratest_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Rajatha on 18-Apr-2017.
 */

public class PhotoAdapter extends BaseAdapter {
    ArrayList<PhotoDetails> selfiePhotos;
    Context mContext;


    public PhotoAdapter(Context context,ArrayList<PhotoDetails> photos){
        mContext=context;
        selfiePhotos=photos;
    }

    @Override
    public int getCount() {
        return selfiePhotos.size();
    }

    @Override
    public Object getItem(int position) {
        return selfiePhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(PhotoDetails photos){
         selfiePhotos.add(photos);
        notifyDataSetChanged();

    } public void deleteItem(int position){
        deletefile(position);
      selfiePhotos.remove(position);
        notifyDataSetChanged();
    }

    public void deletefile(int position){
        PhotoDetails rmPhoto=(PhotoDetails)this.getItem(position);
        rmPhoto.getmPhotoPath();
        File removefile=new File(rmPhoto.getmPhotoPath());
        removefile.delete();

    }


    public ArrayList<PhotoDetails> getList(){

        return new ArrayList<>(selfiePhotos);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       PhotoDetails photos=selfiePhotos.get(position);
        View mview;
        LayoutInflater inflater = (LayoutInflater) parent.getContext ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);

        if (convertView==null){

            mview=inflater.inflate(R.layout.customlayout,null);
        } else{
            mview=convertView;

        }

        ImageView image =(ImageView) mview.findViewById(R.id.imageView);
        if(photos.getMdata()!=null){

            image.setImageBitmap(photos.getMdata());
        }
        TextView mPhotoName=(TextView)mview.findViewById(R.id.textView);
        mPhotoName.setText(photos.getmPhotoName());

        return mview;




    }

}
