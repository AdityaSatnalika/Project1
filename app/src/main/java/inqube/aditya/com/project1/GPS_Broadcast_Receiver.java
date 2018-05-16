package inqube.aditya.com.project1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import static inqube.aditya.com.project1.MainActivity.Lcount;

public class GPS_Broadcast_Receiver extends BroadcastReceiver {
    private final static String TAG = "LocationProviderChanged";

    boolean isGpsEnabled;
    boolean isNetworkEnabled;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED") || intent.getAction().matches("com.google.android.apps.photos"))
        {
            Toast.makeText(context,"Location Providers changed",Toast.LENGTH_SHORT).show();

            SharedPreferences app_preferences;
            app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
            Lcount = app_preferences.getInt("Lcount",0);
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("Lcount",++Lcount);
            editor.commit();

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            //Start your Activity if location was enabled:
            if (isGpsEnabled || isNetworkEnabled) {
                Toast.makeText(context,"Both Enabled",Toast.LENGTH_SHORT).show();
                Log.i("_____________________","This is network");
            }
        }
    }
}