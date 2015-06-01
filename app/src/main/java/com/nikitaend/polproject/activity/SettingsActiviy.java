package com.nikitaend.polproject.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.dialogs.EditSettingsDialog;
import com.nikitaend.polproject.dialogs.TimePickerFragment;

/**
 * @author Endaltsev Nikita
 *         start at 28.05.15.
 */
public class SettingsActiviy extends Activity implements TimePickerFragment.OnCompleteListener,
        EditSettingsDialog.OnCompleteListener, TimePickerFragment.OnCompleteEditListener
{

    public static interface TemperatureChangedListener {
        public abstract void OnTemperatureChanged(double dayTemperature, double nightTemperature);
    }
    
    public TemperatureChangedListener temperatureChangedListener;
    
    public static double dayTemperature = 25.2;
    public static double nightTemperature = 19.3;
    public static String dayTime = "08:30 AM";
    private String degree =  (char) 0x00B0 + "C";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);
        
        setTitle("Day/Night Settings");
        
        TextView daytimeTextView = (TextView) findViewById(R.id.settings_daytime);
        daytimeTextView.setText(dayTime);
        daytimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = TimePickerFragment.newInstance(true);
                dialogFragment.show(getFragmentManager(), "timePickerDayTime");
            }
        });
        
        TextView dayTempTextView = (TextView) findViewById(R.id.settings_day_temperature);
        dayTempTextView.setText(dayTemperature + degree);
        dayTempTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = EditSettingsDialog.newInstance(true, dayTemperature);
                dialogFragment.show(getFragmentManager(), "editSettingsDay");
            }
        });
        
        TextView nightTempTextView = (TextView) findViewById(R.id.settings_night_temperature);
        nightTempTextView.setText(nightTemperature + degree);
        nightTempTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = EditSettingsDialog.newInstance(false, nightTemperature);
                dialogFragment.show(getFragmentManager(), "editSettingsNight");
            }
        });
        
    }

    @Override
    public void onComplete(boolean startEndTime, String timeHolder) {
        dayTime = timeHolder;
        TextView dayTimeTextView = (TextView) findViewById(R.id.settings_daytime);
        dayTimeTextView.setText(timeHolder);
    }

    @Override
    public void onComplete(boolean dayNight, double temperature) {
        if (dayNight) {
            TextView dayTextView = (TextView) findViewById(R.id.settings_day_temperature);
            dayTextView.setText(temperature + degree);
            SettingsActiviy.dayTemperature = temperature;
            
            

        } else {
            TextView nightTextView = (TextView) findViewById(R.id.settings_night_temperature);
            nightTextView.setText(temperature + degree);
            SettingsActiviy.nightTemperature = temperature;
        }
    }

    @Override
    public void onComplete(int indexOfLElement, String startTime, String endTime, String dayNight, String title) {
        // do nothing
    }
}
