package com.nikitaend.polproject.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * it is time picker dialog to choose time and 
 * * return changing to MoreDialog and ScheduleActivity
 * @author Endaltsev Nikita
 *         start at 12.05.15.
 */
public class TimePickerFragment extends DialogFragment 
        implements TimePickerDialog.OnTimeSetListener {

    int indexOfElement;
    boolean startEndTime;
    String startTime;
    String endTime;
    String dayNight;
    String title;
    
    

    public static interface OnCompleteEditListener {
        public abstract void onComplete(int indexOfLElement, String startTime, String endTime,
                                        String dayNight, String title);
        
    }
    
    public static interface OnCompleteListener {
        public abstract void onComplete(boolean startEndTime, String timeHolder);
        
    }
    
    private OnCompleteEditListener mOnCompleteEditListener;
    private OnCompleteListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
            this.mOnCompleteEditListener = (OnCompleteEditListener) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    
    /**
     * @param startEndTime
     * @return
     */
    public static TimePickerFragment newInstance(boolean startEndTime) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        
        Bundle args = new Bundle();

        args.putBoolean("startEndTime", startEndTime);
        
        timePickerFragment.setArguments(args);
        return timePickerFragment;        
    }
    
    public static TimePickerFragment newInstance(boolean startEndTime, 
                                                 int indexOfElement, String startTime,
                                                 String endTime, String dayNight, String title) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        Bundle args = new Bundle();

        args.putString("title", title);
        args.putBoolean("startEndTime", startEndTime);
        args.putInt("indexOfElement", indexOfElement);
        args.putString("startTime", startTime);
        args.putString("endTime", endTime);
        args.putString("dayNight", dayNight);

        timePickerFragment.setArguments(args);
        return timePickerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            try {
                startEndTime = args.getBoolean("startEndTime");
            } catch (Exception e) {}
            try {
                title = args.getString("title");
                indexOfElement = args.getInt("indexOfElement");
                startTime = args.getString("startTime");
                endTime = args.getString("endTime");
                dayNight = args.getString("dayNight");
            } catch (Exception e) {}
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }
    
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String timeToHolder = "";
        minute = minute - (minute % 5);
//                + (int)(Math.abs((double)(minute % TIME_PICKER_INTERVAL)/TIME_PICKER_INTERVAL) * TIME_PICKER_INTERVAL);
        if (minute == 60) {
            minute = 0;
            hourOfDay += 1;
        }
        
        if (hourOfDay > 12) {
            timeToHolder = (hourOfDay - 12) + ":" + minute + " PM";
        } else {
            timeToHolder = hourOfDay + ":" + minute + " AM";
        }

        if (startEndTime) {
            startTime = timeToHolder;
        } else { endTime = timeToHolder; }
        
        if (startTime != null && endTime != null) {
            try {
                mOnCompleteEditListener.onComplete(indexOfElement, startTime, endTime, dayNight, title);
            } catch (Exception e) { }
            
        } else { mListener.onComplete(startEndTime, timeToHolder); }
    }

    private static final int TIME_PICKER_INTERVAL = 5;
}
