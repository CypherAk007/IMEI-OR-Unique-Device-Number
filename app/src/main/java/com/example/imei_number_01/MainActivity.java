package com.example.imei_number_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.provider.Settings.Secure;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TelephonyManager tm;
    TextView imeitxt;
    Button imeibtn;
    String imei;
    String uId;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();

        imeitxt = (TextView)findViewById(R.id.imei_Number);
        imeibtn = (Button)findViewById(R.id.getimeibtn);

//        int permissionI = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
//
//        if(permissionI == PackageManager.PERMISSION_GRANTED){
////            If user has granted the permission then we store the imei no. in tm varible
//            tm =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//            imei = tm.getDeviceId().toString();
//        }
//        else{
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},123);
//        }

        imeibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                uId = getDeviceId(context);
//                uId =  android_id;
                uId =  getIMEIDeviceId( MainActivity.getAppContext());
                imeitxt.setText(uId);
            }
        });
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
//    private String android_id = Secure.getString(context.getApplicationContext().getContentResolver(),Secure.ANDROID_ID);

//    public static String getDeviceId(Context context) {
//
//        String deviceId;
//
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            deviceId = Settings.Secure.getString(
//                    context.getContentResolver(),
//                    Settings.Secure.ANDROID_ID);
//        } else {
//            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (mTelephony.getDeviceId() != null) {
//                deviceId = mTelephony.getDeviceId();
//            } else {
//                deviceId = Settings.Secure.getString(
//                        context.getContentResolver(),
//                        Settings.Secure.ANDROID_ID);
//            }
//        }
//
//        return deviceId;
//    }

    public static String getIMEIDeviceId(Context context) {

        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    deviceId = mTelephony.getImei();
                }else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        Log.d("deviceId", deviceId);
        return deviceId;
    }
}