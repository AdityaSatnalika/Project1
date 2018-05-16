package inqube.aditya.com.project1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import static inqube.aditya.com.project1.MainActivity.wcount;

public class Wifi_Broadcast_Receiver extends BroadcastReceiver
{

    String TAG = getClass().getSimpleName();
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent)
    {


        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);


        int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE , WifiManager.WIFI_STATE_UNKNOWN);

        switch(extraWifiState)
        {
            case WifiManager.WIFI_STATE_DISABLED:
                 break;

            case WifiManager.WIFI_STATE_DISABLING:

                Toast.makeText(context, "WIFI STATE DISABLED" , Toast.LENGTH_SHORT).show();

                break;

            case WifiManager.WIFI_STATE_ENABLED:
                SharedPreferences app_preferences;
                app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
                wcount = app_preferences.getInt("wcount",0);
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putInt("wcount",++wcount);
                editor.commit();
                Toast.makeText(context, "WIFI STATE ENABLED " + wcount, Toast.LENGTH_SHORT).show();
                Log.i("_____________________","This is Wifi_Broadcast_Receiver");

                break;

            case WifiManager.WIFI_STATE_ENABLING:

                Toast.makeText(context, "WIFI STATE ENABLING" , Toast.LENGTH_SHORT).show();

                break;

            case WifiManager.WIFI_STATE_UNKNOWN:
                Toast.makeText(context, "WIFI STATE UNKNOWN" , Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
