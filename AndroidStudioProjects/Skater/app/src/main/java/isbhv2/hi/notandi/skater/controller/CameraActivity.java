package isbhv2.hi.notandi.skater.controller;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import isbhv2.hi.notandi.skater.R;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.HashMap;
import java.lang.Object;
import java.text.Format;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Environment;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.util.Collections;



public class CameraActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_camera);
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    File photoFile;

    @Override
    public void onClick(View v) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                //error
            }
            if (photoFile != null) {
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePhotoIntent, CAMERA_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            Map config = new HashMap();
            config.put("cloud_name", "hbv2skater");
            config.put("api_key","459114518896268");
            config.put("api_secret","caVLsNO_5Amx89RBTroN2MUZP-w");
            //MediaManager.init(this, config);

            Cloudinary cloudinary = new Cloudinary(config);
            try {
                cloudinary.uploader().upload(photoFile.getAbsolutePath(), ObjectUtils.emptyMap());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_CANCELED){
            //gera ekkert og hætta
        }

    }


    /*Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_camera);

        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(this);

    }
    public static final int TAKE_PHOTO_REQUEST = 0;

    File photoFile;

    @Override
    public void onClick(View v) {
        Intent takePhotoIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePhotoIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
                //File to upload to cloudinary
                Map config = new HashMap();
                config.put("cloud_name", "dkepfkeuu");
                config.put("api_key", "552563677649679");
                config.put("api_secret", "7n8wJ42Hr_6nqZ4aOMDXjTIZ4P0");
                Cloudinary cloudinary = new Cloudinary(config);
                try {
                    cloudinary.uploader().upload(photoFile.getAbsolutePath(),          Cloudinary.emptyMap());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                //finish();
            }
        }
    }*/

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "capturedImage";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        //Save a file: path for use with ACTION_VIEW intents
        return image;
    }


}
