package inqube.aditya.com.project1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import static inqube.aditya.com.project1.MainActivity.ccount;

public class newserviceclas extends Service {
    @Nullable
    Context context=this;
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            manager.registerAvailabilityCallback(
                    new CameraManager.AvailabilityCallback()
                    {

                        @Override
                        public void onCameraUnavailable(String cameraId)
                        {
                            super.onCameraUnavailable(cameraId);
                            super.onCameraAvailable(cameraId);
                            SharedPreferences app_preferences;
                            app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            ccount = app_preferences.getInt("ccount",0);
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putInt("ccount",++ccount);
                            editor.commit();

                            Toast.makeText(context, "Camera Is Opened " + ccount, Toast.LENGTH_LONG).show();
                            Log.i("_____________________", "Camera " + cameraId
                                    + " is unavailable");
                        }
                    }, null);

        }
        return super.onStartCommand(intent, flags, startId);
    }
}
