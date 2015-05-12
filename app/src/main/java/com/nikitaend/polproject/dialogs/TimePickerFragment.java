package com.nikitaend.polproject.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * @author Endaltsev Nikita
 *         start at 12.05.15.
 */
public class TimePickerFragment extends DialogFragment 
        implements TimePickerDialog.OnTimeSetListener {

    boolean startEndTime;


    public static interface OnCompleteListener {

        public abstract void onComplete(boolean startEndTime, String timeHolder);
    }

    private OnCompleteListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startEndTime = getArguments().getBoolean("startEndTime");
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
        if (hourOfDay > 12) {
            timeToHolder = (hourOfDay - 12) + ":" + minute + " PM";
        } else {
            timeToHolder = hourOfDay + ":" + minute + " AM";
        }

        mListener.onComplete(startEndTime, timeToHolder);
    }
}
