package inqube.aditya.com.project1;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends AppCompatActivity
{
        public static int pcount=0,ccount=0,wcount=0,Lcount=0;
        ListView listView;
        Context context=this;
        String[] nameArray = {"Phone","Camera","Wifi","Location", };

        int permsRequestCode = 200;

        public static  String[] infoArray =
            {
                    "The Count is :" + pcount,
                    "The Count is :"+ ccount,
                    "The Count is :" +wcount,
                    "The Count is :" +Lcount,

            };

        Integer[] imageArray =
            {       R.drawable.phone,
                    R.drawable.camera,
                    R.drawable.wifi,
                    R.drawable.location};


    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_main);

            startService(new Intent(this, Camera_Service.class));
            if(checkPermission()==false)
                requestPermission();

            final List_Adapter whatever = new List_Adapter(this, nameArray, infoArray, imageArray);
            final SharedPreferences app_preferences;
            app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).registerOnSharedPreferenceChangeListener(
                    new SharedPreferences.OnSharedPreferenceChangeListener() {
                        @Override
                        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
                        {
                            onResume();
                            Log.i("_________", "testOnSharedPreferenceChangedWrong key =" + key);
                        }
                    });

         }

        public void startAlert(View view){
          //  EditText text = (EditText) findViewById(R.id.time);
            int i =10; //Integer.parseInt(text.getText().toString());

            Intent intent = new Intent(this, Phone_Broadcast_Receiever.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1200202, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (i * 1000), pendingIntent);

            Toast.makeText(this, "Alarm set in " + i + " seconds",Toast.LENGTH_SHORT).show();
        }
        public void reset(View view){
            SharedPreferences app_preferences;
            app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = app_preferences.edit();
            pcount = app_preferences.getInt("pcount",0);
            editor.putInt("pcount",0);

            ccount = app_preferences.getInt("ccount",0);
            editor.putInt("ccount",0);

            wcount = app_preferences.getInt("wcount",0);
            editor.putInt("wcount",0);

            Lcount = app_preferences.getInt("Lcount",0);
            editor.putInt("Lcount",0);

            editor.commit();

        Toast.makeText(this, "All Values Reset to 0",Toast.LENGTH_SHORT).show();
    }
        private void requestPermission() {
            ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE,READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION}, permsRequestCode);

    }
        public void onResume() {

        super.onResume();

        SharedPreferences app_preferences;
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        ccount = app_preferences.getInt("ccount",0);
        pcount = app_preferences.getInt("pcount",0);
        wcount = app_preferences.getInt("wcount",0);
        Lcount = app_preferences.getInt("Lcount",0);

        infoArray[0]="The Count is :"+ pcount;
        infoArray[1]="The Count is :"+ ccount;
        infoArray[2]="The Count is :"+ wcount;
        infoArray[3]="The Count is :"+ Lcount;


        List_Adapter whatever = new List_Adapter(this, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(whatever);

    }
        private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),READ_PHONE_STATE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
        }
        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0)
                {   boolean readexternal = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean locationAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean phoneAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                     if (locationAccepted && readexternal && phoneAccepted )
                    {
                        Toast.makeText(this, "All Permission Granted.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION))
                            {
                                showMessageOKCancel("You need to allow access to All the permissions",
                                        new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_PHONE_STATE,READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION},
                                                            permsRequestCode);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }
        private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}

