package com.example.rajatha.rcameratest_2;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rajatha on 18-Apr-2017.
 */

public class PhotoDetails implements Parcelable {
    private String mPhotoPath;
    private String mPhotoName;
    private Bitmap mdata;


    public PhotoDetails(String PhotoPath, String PhotoName, Bitmap data) {
        mPhotoName = PhotoName;
        mPhotoPath = PhotoPath;
        mdata = data;

    }

    public PhotoDetails(Parcel P) {

        mPhotoPath = P.readString();
        mPhotoName = P.readString();
        mdata = P.readParcelable(null);
    }


    public void setPhtoPath(String mPhotoPath) {

        this.mPhotoPath = mPhotoPath;
    }

    public void setmPhotoName(String mPhotoName) {

        this.mPhotoName = mPhotoName;
    }

    public void setMdata(Bitmap mdata) {

        this.mdata = mdata;
    }

    public String getmPhotoPath() {
        return mPhotoPath;
    }

    public String getmPhotoName() {

        return mPhotoName;
    }

    public Bitmap getMdata() {

        return mdata;
    }


    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPhotoPath);
        dest.writeString(mPhotoName);
        dest.writeParcelable(mdata, 1);

    }

    public static final Parcelable.Creator<PhotoDetails> CREATOR = new Parcelable.Creator<PhotoDetails>() {


        @Override
        public PhotoDetails createFromParcel(Parcel source) {
            return new PhotoDetails(source);
        }

        @Override
        public PhotoDetails[] newArray(int size) {
            return new PhotoDetails[0];
        }
    };
}

