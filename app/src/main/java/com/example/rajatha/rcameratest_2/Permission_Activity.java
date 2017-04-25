package com.example.rajatha.rcameratest_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Permission_Activity extends AppCompatActivity {
    private final String[] permissions=new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    static final String TAG="Permission_Activity";
    static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_);


        Button Click = (Button) findViewById(R.id.Click);

        Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(Permission_Activity.this,
                        permissions[0])
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(Permission_Activity.this,
                        permissions[1])
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(Permission_Activity.this,
                        permissions[2])
                        != PackageManager.PERMISSION_GRANTED)) {
                    Log.i(TAG, "Entered the check permission");
                    checkpermissionRationale();

                } else {
                    Log.i(TAG, "Permission was already granted");
                    intent();

                }


            }
        });
    }

    private void checkpermissionRationale(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(Permission_Activity.this,
                permissions[0])) {

            Log.i(TAG, "Request Rationale was true for Camera");
            Toast.makeText(Permission_Activity.this, "You need to provide Camera Access in order to use this app", Toast.LENGTH_SHORT).show();
            requestallpermission();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(Permission_Activity.this,
                permissions[1])){
            Log.i(TAG, "Request Rationale was true for External Storage-Read");
            Toast.makeText(Permission_Activity.this, "You need to provide Read Access to Extrenal Storage in order to use this app", Toast.LENGTH_SHORT).show();
            requestallpermission();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(Permission_Activity.this,
                permissions[2])){

            Log.i(TAG, "Request Rationale was true for External Storage-Read");
            Toast.makeText(Permission_Activity.this, "You need to provide Write Access to Extrenal Storage in order to use this app", Toast.LENGTH_SHORT).show();
            requestallpermission();
        } else
        {
            requestallpermission();

        }

    }

    /*Requesting the permission*/

    private void requestallpermission(){
        Log.i(TAG,"Requesting the permission");

        ActivityCompat.requestPermissions(Permission_Activity.this,
                permissions,
                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

    }
private void intent(){

    Intent mainactivity = new Intent(Permission_Activity.this,MainActivity.class);
    startActivity(mainactivity);

}

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                   intent();

                } else {

                    Toast.makeText(Permission_Activity.this, "Permission wasn't granted", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        }

    }
}
