package com.example.rajatha.rcameratest_2;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static String TAG ="RCameraTest_2";
    public static final String ALBUM_NAME="Daily Selfie";
    static final String PHOTO_PREFIX="DS_";
    static final String PHOTO_SUFFIX=".jpg";
    static final String DELETE="Do you want to delete the photo?";
    public String mCurrentphotopath;
    public String mCurrentPhotoName;
    private PhotoAdapter mdapter;
    private ArrayList<PhotoDetails> mselfie;
    private AlarmManager mAlarmManager;
    private Intent mNotificationReceiver;
    private PendingIntent mPendingIntent;
    private static final long INITIAL_ALARM_DELAY = 1 *60* 1000L;
    private static final  long TWO_MIN=2*60*1000L;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       ImageView photo=(ImageView)findViewById(R.id.imageView);
        mselfie=new ArrayList<PhotoDetails>();
        final ListView lisview=(ListView)findViewById(R.id.listview1);
        final ProgressBar spinner= (ProgressBar)findViewById(R.id.progressBar);
        alarmControl();




    if (savedInstanceState == null) {
        SelfieReader oldselfies = new SelfieReader(spinner);

        try {

            mselfie = oldselfies.execute(this.getAlbumDirectory()).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



}


        mdapter=new PhotoAdapter(this,mselfie);
        lisview.setAdapter(mdapter);

        lisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoDetails mparcable=(PhotoDetails)mdapter.getItem(position);
                Intent parcableIntent= new Intent(MainActivity.this,DisplayActivity.class);
                parcableIntent.putExtra("Selfie",mparcable);
                startActivity(parcableIntent);
            }

        });

        lisview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog(position);
                return true;
            }
        });


    }

    private void alarmControl(){

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mNotificationReceiver=new Intent(MainActivity.this,NotificationReceiver.class);
        mPendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,mNotificationReceiver,0);
        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+INITIAL_ALARM_DELAY,TWO_MIN,mPendingIntent);
        Log.i(TAG,"Alarm is Set");
    }





    public void AlertDialog(final int position){

        AlertDialog.Builder alertDialog= new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(DELETE);
        TextView myMsg = new TextView(this);
        myMsg.setText(DELETE);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(20);
        myMsg.setTextColor(Color.WHITE);
        alertDialog.setView(myMsg);
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
               mdapter.deleteItem(position);
            }
        });
        AlertDialog dialog=alertDialog.create();

        alertDialog.show();
    }




    private void goToCameraApp() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File mphoto=null;
        try {

            mphoto=createImageFile();
            mCurrentphotopath=mphoto.getAbsolutePath();
            mCurrentPhotoName=mphoto.getName();

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                if (mphoto!=null){

                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.rajatha.rcameratest_2",
                            mphoto);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }




        } catch(IOException e){
            e.printStackTrace();
            mphoto=null;
            mCurrentphotopath=null;

        }

    }



    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat ("yyyyMMdd_HHmmss").format (new Date ());
        String imageFileName = PHOTO_PREFIX + timeStamp + "_";
        File storgaeDir=getAlbumDirectory();
        File image= File.createTempFile(imageFileName,PHOTO_SUFFIX,storgaeDir);
        return image;
    }

    private File getAlbumDirectory() throws IOException{
     File storageDir=null;

        if(isExternalStorageWritable()){

            storageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES),ALBUM_NAME);
                if (storageDir!=null) {
                      if (!storageDir.mkdirs()) {

                              if (!storageDir.exists()) {

                                   Log.i(TAG, "Directory not created");
                              }
                      }

                 }

        } else {
            Log.i(TAG,"There was no place to create a Directory");
        }
        Log.i(TAG,"Returning the Directory");
        return storageDir;

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
               ) {
            return true;
        }
        return false;
    }

    private Bitmap setPic(){
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:

                    goToCameraApp();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bitmap mphototaken=setPic();
            if(mphototaken!=null) {
                PhotoDetails mdetails = new PhotoDetails(mCurrentphotopath, mCurrentPhotoName, mphototaken);
                mdapter.addItem(mdetails);
            }




        }


    }
}
