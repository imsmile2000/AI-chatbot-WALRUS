package org.techtown.chatbot1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;


public class Parceldata implements Parcelable {


    HashMap<String, Integer> datalist;


    public Parceldata(HashMap<String,Integer> datalist){
        this.datalist = datalist;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(datalist.size());
        for(Map.Entry<String,Integer> entry: datalist.entrySet()){
            parcel.writeString(entry.getKey());
            parcel.writeInt(entry.getValue());
        }


    }

    protected Parceldata(Parcel in) {
        int size = in.readInt();
        if(size !=0){
            for(int i=0; i<size; i++){
                datalist.put(in.readString(),in.readInt());
            }
        }

    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Parceldata> CREATOR = new Creator<Parceldata>() {
        @Override
        public Parceldata createFromParcel(Parcel in) {
            return new Parceldata(in);
        }

        @Override
        public Parceldata[] newArray(int size) {
            return new Parceldata[size];
        }
    };


}
