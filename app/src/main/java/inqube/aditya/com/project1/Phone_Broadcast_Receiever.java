package inqube.aditya.com.project1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import static inqube.aditya.com.project1.MainActivity.pcount;

public class Phone_Broadcast_Receiever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String S = intent.getAction();
        if (S == "") ;
        {
            Toast.makeText(context, "Received Broadcast", Toast.LENGTH_SHORT).show();

        }

        if (S == "android.intent.action.PHONE_STATE")
        {
            SharedPreferences app_preferences;
            app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
            pcount = app_preferences.getInt("pcount",0);
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("pcount",++pcount);
            editor.commit();
            try {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    Toast.makeText(context, "Ringing State Number is -" + incomingNumber, Toast.LENGTH_SHORT).show();
                    Log.i("_____________________","This is Phone");

                }
                if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
                    Toast.makeText(context, "Received State", Toast.LENGTH_SHORT).show();
                }
                if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    Toast.makeText(context, "Idle State", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
            // Vibrate the mobile phone
            /* Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);*/
        }
    }


}
