package tumblrr.utd.com.steptracker;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class StepCountingService extends Service implements SensorEventListener {
    SensorManager sensorManager;
    Sensor stepDetectorSensor;

    int currentStepsDetected;

    int steptracker;
    int newsteptracker;

    boolean serviceStopped;

    Intent intent;

    private static final String TAG = "StepService";
    public static final String BROADCAST_ACTION = ".StepCountingService";

    private final Handler handler = new Handler();

    int counter = 0;


    @Override
    public void onCreate() {
        super.onCreate();

        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sensorManager.registerListener(this, stepDetectorSensor, 0);

        currentStepsDetected = 0;

        steptracker = 0;
        newsteptracker = 0;

        serviceStopped = false;

        handler.removeCallbacks(updateBroadcastData);
        handler.post(updateBroadcastData);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        serviceStopped = true;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int countSteps = (int) event.values[0];

            if (steptracker == 0) {
                steptracker = (int) event.values[0];
            }
            newsteptracker = countSteps - steptracker;
        }

        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            int detectSteps = (int) event.values[0];
            currentStepsDetected += detectSteps;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private Runnable updateBroadcastData = new Runnable() {
        public void run() {
            if (!serviceStopped) {
                // Broadcast data to the Activity
                broadcastSensorValue();
                handler.postDelayed(this, 1000);
            }
        }
    };

    private void broadcastSensorValue() {
        Log.d(TAG, "Data to Activity");
        // add step counter to intent.
        intent.putExtra("Counted_Step_Int", newsteptracker);
        intent.putExtra("Counted_Step", String.valueOf(newsteptracker));
        // add step detector to intent.
        intent.putExtra("Detected_Step_Int", currentStepsDetected);
        intent.putExtra("Detected_Step", String.valueOf(currentStepsDetected));
        //sendBroadcast sends a message to whoever is registered to receive it.
        sendBroadcast(intent);
    }
}
