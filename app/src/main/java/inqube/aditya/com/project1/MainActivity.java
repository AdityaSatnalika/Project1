package inqube.aditya.com.project1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends AppCompatActivity
{
        public static int pcount=0,ccount=0,wcount=0,Lcount=0,Gcount=0,count=0;

        String[] perms = {"READ_PHONE_STATE", "android.permission.CAMERA"};
        int permsRequestCode = 200;
        String[] nameArray = {"Phone","Camera","Wifi","Location","Gallery" };

        public static  String[] infoArray =
            {
                    "The Count is :" + pcount,
                    "The Count is :"+ ccount,
                    "The Count is :" +wcount,
                    "The Count is :" +Lcount,
                    "The Count is :" +Gcount,
                    "The Count is :"+count
            };

        Integer[] imageArray =
            {R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_background,
                    R.drawable.ic_launcher_background};
    ListView listView;

    @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            SharedPreferences app_preferences;
            app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

            ccount = app_preferences.getInt("ccount",0);
            pcount = app_preferences.getInt("pcount",0);
            wcount = app_preferences.getInt("wcount",0);
            Lcount = app_preferences.getInt("Lcount",0);
            Gcount = app_preferences.getInt("Gcount",0);

            infoArray[0]="The Count is :"+ pcount;
            infoArray[1]="The Count is :"+ ccount;
            infoArray[2]="The Count is :"+ wcount;
            infoArray[3]="The Count is :"+ Lcount;

            infoArray[4]="The Count is :"+ Gcount;





            customadapter whatever = new customadapter(this, nameArray, infoArray, imageArray);
            listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(whatever);
            requestPermission();


        }
            public void startAlert(View view){
          //  EditText text = (EditText) findViewById(R.id.time);
            int i =10; //Integer.parseInt(text.getText().toString());

            Intent intent = new Intent(this, Receiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1200202, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (i * 1000), pendingIntent);

            Toast.makeText(this, "Alarm set in " + i + " seconds",Toast.LENGTH_LONG).show();
        }
            private void requestPermission(){
            ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE,ACCESS_FINE_LOCATION, CAMERA}, permsRequestCode);

    }


    public void onResume() {

    super.onResume();

    SharedPreferences app_preferences;
    app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

    ccount = app_preferences.getInt("ccount",0);
    pcount = app_preferences.getInt("pcount",0);
    wcount = app_preferences.getInt("wcount",0);
    Lcount = app_preferences.getInt("Lcount",0);
    Gcount = app_preferences.getInt("Gcount",0);

    infoArray[0]="The Count is :"+ pcount;
    infoArray[1]="The Count is :"+ ccount;
    infoArray[2]="The Count is :"+ wcount;
    infoArray[3]="The Count is :"+ Lcount;

    infoArray[4]="The Count is :"+ Gcount;

    customadapter whatever = new customadapter(this, nameArray, infoArray, imageArray);
    listView = (ListView) findViewById(R.id.listview);
    listView.setAdapter(whatever);

}
    public void onVisible() {

        super.onStart();
        super.onResume();

        SharedPreferences app_preferences;
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        ccount = app_preferences.getInt("ccount", 0);
        pcount = app_preferences.getInt("pcount", 0);
        wcount = app_preferences.getInt("wcount", 0);
        Lcount = app_preferences.getInt("Lcount", 0);
        Gcount = app_preferences.getInt("Gcount", 0);

        infoArray[0] = "The Count is :" + pcount;
        infoArray[1] = "The Count is :" + ccount;
        infoArray[2] = "The Count is :" + wcount;
        infoArray[3] = "The Count is :" + Lcount;

        infoArray[4] = "The Count is :" + Gcount;

        customadapter whatever = new customadapter(this, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(whatever);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean phoneAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted && phoneAccepted)
                        Toast.makeText(this, "All Permission Granted.", Toast.LENGTH_LONG).show();
                    else {

                        Toast.makeText(this, "Permission Denied.", Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION))
                            {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_PHONE_STATE,ACCESS_FINE_LOCATION, CAMERA},
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

