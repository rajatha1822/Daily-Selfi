package com.example.rajatha.rcameratest_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Rajatha on 19-Apr-2017.
 */

public class SelfieReader extends AsyncTask<File,Void,ArrayList<PhotoDetails>> {
    ArrayList<PhotoDetails>myselfie;
    ProgressBar bar;

    public SelfieReader(ProgressBar bar){
        this.bar=bar;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<PhotoDetails> doInBackground(File... file) {


        File dir=file[0];
        myselfie=new ArrayList<PhotoDetails>();
        if(dir!=null){
            Log.i("ASYNC","The Dir is not null");
            if(dir.exists()) {
                for (String file1 : dir.list()) {

                        String absolutepath = dir.getAbsolutePath() + "/" + file1;
                        Bitmap image = setPic(absolutepath);
                        PhotoDetails photos = new PhotoDetails(absolutepath, file1, image);
                        myselfie.add(photos);




                }
            }

        }
        return myselfie;
    }



    @Override
    protected void onPostExecute (ArrayList<PhotoDetails> selfies) {

        super.onPostExecute (selfies);
        bar.setVisibility(View.GONE);

    }



    private Bitmap setPic(String mCurrentphotopath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds=true;
        Bitmap mphototaken = BitmapFactory.decodeFile(mCurrentphotopath, options);
        int scaleFactor = 4;
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentphotopath,options);
        return bitmap;


    }

}

