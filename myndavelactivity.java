package isbhv2.hi.notandi.skater.service;

import android.os.Bundle;
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

/*Thessi klasi er mogulega nothaefur i myndavelastuss
 kemur i ljos hvor er betri
 tharf ad skjala betur
 er amk nyrri en gamli
 kannski ekki malid ad skipta ef hinn virkar
 skodum thad seinna
 nyrri adferd skv
 stackoverflow
 */

public class CameraActivity extends AppCompatActivity {

  //breytur
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Uri uriFilePath;


    PackageManager packageManager = getActivity().getPackageManager();
    if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
        File mainDirectory = new File(Environment.getExternalStorageDirectory(), "MyFolder/tmp");
              if (!mainDirectory.exists())
                mainDirectory.mkdirs();

          Calendar calendar = Calendar.getInstance();

          uriFilePath = Uri.fromFile(new File(mainDirectory, "IMG_" + calendar.getTimeInMillis()));
          intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);
          startActivityForResult(intent, 1);
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
          if (uriFilePath != null)
              outState.putString("uri_file_path", uriFilePath.toString());
          super.onSaveInstanceState(outState);
}

        @Override
        protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          if (savedInstanceState != null) {
            if (uriFilePath == null && savedInstanceState.getString("uri_file_path") != null) {
              uriFilePath = Uri.parse(savedInstanceState.getString("uri_file_path"));
            }
          }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
              String filePath = uriFilePath.getPath(); // Here is path of your captured image, so you can create bitmap from it, etc.
            }
          }
        }
}
