package tumblrr.utd.com.steptracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
        buttonClickListener();
    }

    private void buttonClickListener(){

        Button start = (Button) findViewById(R.id.button_start);
        Button track = (Button) findViewById(R.id.button_track);
        Button help = (Button) findViewById(R.id.button_help);
        Button exit = (Button) findViewById(R.id.button_exit);

        //Start count Button onclick listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentPlay = new Intent(getApplicationContext(),StepDetectorActivity.class);
                intentPlay.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentPlay);
            }
        });

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentScore = new Intent(getApplicationContext(), TrackStepsActivity.class);
                intentScore.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentScore);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentHelp = new Intent(getApplicationContext(), HelpActivity.class);
                intentHelp.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentHelp);
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentExit = new Intent(Intent.ACTION_MAIN);
                intentExit.addCategory(Intent.CATEGORY_HOME);
                intentExit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentExit);
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    Toast.makeText(MainActivity.this, "The app was allowed to access storage", Toast.LENGTH_LONG).show();
                } else
                {
                    Toast.makeText(MainActivity.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
