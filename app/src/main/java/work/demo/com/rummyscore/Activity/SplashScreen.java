package work.demo.com.rummyscore.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import work.demo.com.rummyscore.Constants.Constant;
import work.demo.com.rummyscore.R;

public class SplashScreen extends AppCompatActivity {

    boolean aBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!hasPermissions()){
            Log.d("check_block_1: ", "true");
            requestNecessaryPermissions();
        }else {
            if (!aBoolean) {
                aBoolean = true;
                Log.d("check_block_2: ", "true");
                GoToNext();
            }
        }
    }

    private void GoToNext(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length ==2 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            //The Camera Permission is granted to you... Continue your left job...
            Log.d("check_block_3: ", "true");
            GoToNext();
        }
    }

    private void requestNecessaryPermissions(){
        if (ActivityCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
                ActivityCompat.requestPermissions(SplashScreen.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constant.REQUEST_CODE_STORAGE_PERMS
                        );
        }
    }


    @SuppressLint("WrongConstant")
    private boolean hasPermissions()
    {
        int res = 0;
        String[] permissions = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String perms : permissions)
        {
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                // it return false because your app dosen't have permissions.
                return false;
            }
        }
        return true;
    }
}
