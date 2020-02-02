package com.example.producktivity.ui.scrolling_to_do;
//one to-do activity
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;

import static android.content.ContentValues.TAG;
//i'm pretty sure the bitmapdrawable is unnecessary
public class Info implements Parcelable {
    private String text = new String(" ");
    private BitmapDrawable image;
    private Uri imageUri; //a compact version of the image
    private MediaStore.Audio audio;
    private String fraction;
    private int numerator;
    private int denominator;
    private int difficulty = 3;
    private String nextHint;

    public String getNextHint() {
        return nextHint;
    }

    public void setNextHint(String nextHint) {
        this.nextHint = nextHint;
    }

    public Info(String text, String nextHint, BitmapDrawable image, Uri imageUri, MediaStore.Audio audio){
        this.text = text;
        this.image = image;
        this.audio = audio;
        this.imageUri = imageUri;
        this.nextHint = nextHint;
    }

    public Info(String text){
        this.text = text;
    }

    //used to unpack the parcel
    public Info (Parcel source){
        Log.v(TAG, "time to unpack the parcel!");
        numerator = source.readInt();
        denominator = source.readInt();
        difficulty = source.readInt(); //i have to actually set this one somewhere tho
        //somethingsomethingaudio

        text = source.readString();
        imageUri = Uri.parse(source.readString());
    }
    /*  public class MyCreator implements Parcelable.Creator<Info> {
          public Info createFromParcel(Parcel source) {
              return new Info(source);
          }  //uses the constructor that helps unpack the parcel
          public Info[] newArray(int size) {
              return new Info[size];
          }
      }
      */
    public Info copyInfo(Info original){
        return new Info(original.getText(), original.getNextHint(), original.getImage(), original.getUri(), original.getAudio());
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        Log.v(TAG, "write to parcel");
        dest.writeInt(numerator);
        dest.writeInt(denominator);
        dest.writeInt(difficulty);
        if (text != null)
            dest.writeString(text);
        else
            dest.writeString("");
        if (getUri() != null)
            dest.writeString(imageUri.toString()); //stores the uri as a string which can be converted to bitmapdrawable within the flashcard activity
        else
            dest.writeString("");
        //dest.writeBITMAPDRAWABLELOOOL not super possible. instead i'll just convert the imageUri to an image in my flashcard activity


    }

    public Info(BitmapDrawable image){
        this.image = image;
    }

    public Info(MediaStore.Audio audio){
        this.audio = audio;
    }

    public Info(){}

    public void setDifficulty(int diff){difficulty = diff;}

    public int getDifficulty(){return difficulty;}

    public int getNumerator(){ return numerator;}

    public int getDenominator(){return denominator;}

    public void setNumerator(int num){numerator = num;}

    public void setDenominator(int denom){denominator = denom;}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BitmapDrawable getImage() {
        return image;
    }

    public void setImage(BitmapDrawable image) {
        this.image = image;
    }

    public Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, getText() + "_" + getFraction(), null);
        return Uri.parse(path);
    }

    public Uri getUri(){return imageUri;}

    public void setUri(Uri in){ imageUri = in; }

    public MediaStore.Audio getAudio() {
        return audio;
    }

    public void setAudio(MediaStore.Audio audio) {
        this.audio = audio;
    }

    public boolean isEmpty(){
        return (getAudio() == null && getImage() == null && (getText() == " " || getText() == null));
    }

    public String getFraction(){
        return fraction = numerator + "/" + denominator;
    }

    public void setFraction(int num, int denom){
        numerator = num;
        denominator = denom;
        fraction = num + "/" + denom;
    }
    //if the text, audio, and image are the same, then set them equal. i don't know how to check image or audio
    public boolean equals(Info check) {
        boolean text, audio, image;
        if (getText() != " ") {
            if (check.getText() != " ")
                text = (getText().equals(check.getText()));
            else
                text = false; //false if one is not null
        } else
            text = check.getText() == null; //true if check is also null
        if (getAudio() != null) {
            if (check.getAudio() != null)
                audio = (getAudio().equals(check.getAudio()));
            else
                audio = false; //false if one is not null
        }
        else
            audio = check.getAudio() == null; //if check is also null, then text is truely equal
        if (getImage() != null) {
            if (check.getImage() != null)
                image = (getImage().equals(check.getImage()));
            else
                image = false; //false if one is not null
        } else
            image = check.getImage() == null;
        return text && image && audio;
    }

}

