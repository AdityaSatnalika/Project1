package inqube.aditya.com.project1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import static inqube.aditya.com.project1.MainActivity.ccount;

public class Cameraeventlistener extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        SharedPreferences app_preferences;
        app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ccount = app_preferences.getInt("ccount",0);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt("ccount",++ccount);
        editor.commit();

        Toast.makeText(context, "New Photo Clicked" + ccount, Toast.LENGTH_LONG).show();


    }
}
