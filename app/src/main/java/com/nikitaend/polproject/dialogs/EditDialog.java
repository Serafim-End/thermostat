package com.nikitaend.polproject.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nikitaend.polproject.R;

/**
 * In our application every dialog type of Fragment and we need fragmentManger to show dialog
 * also we can exchange information
 * get information with Bundle in newInstance static method
 * and post information with OnCompleteListener interface
 * 
 * for every dialog i have made xml screen for these  dialog_more_edit
 * @author Endaltsev Nikita
 *         start at 09.05.15.
 */
public class EditDialog extends DialogFragment 
        implements DialogInterface.OnClickListener {

    Context mContext;
    String title;
    
    public static EditDialog newInstance(String title) {
        EditDialog editDialog = new EditDialog();
        Bundle args = new Bundle();
        
        args.putString("title", title);
        editDialog.setArguments(args);
        return editDialog;
    }
    public static interface OnCompleteListener {
        /**
         * @param startTime - start time
         * @param endTime - end time
         * @param dayNight - 1 - day and 0 - night
         */
        public abstract void onComplete(String startTime, String endTime, Boolean dayNight);
    }

    private OnCompleteListener mListener;
    
    @Override
    public void onClick(DialogInterface dialog, int which) {
        
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        getDialog().setTitle("Edit interval");
        
        View v = inflater.inflate(R.layout.dialog_more_edit, null);
        mContext = v.getContext();

        final TextView startTimeTextView = (TextView) v.findViewById(R.id.start_time_textView);
        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment =  TimePickerFragment.newInstance(true);
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
        
        final TextView endTimeTextView = (TextView) v.findViewById(R.id.end_time_textView);
        endTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = TimePickerFragment.newInstance(false);
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });


//        final Switch dayNightSwitch = (Switch) v.findViewById(R.id.dayNight_switch);
        
        Button okBtn = (Button) v.findViewById(R.id.more_edit_dismiss_button);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Boolean dayNight = !(dayNightSwitch).isChecked();
                Boolean dayNight = true;
                String startTime = startTimeTextView.getText().toString();
                String endTime = endTimeTextView.getText().toString();
                
                mListener.onComplete(startTime, endTime, dayNight);
                dismiss();
            }
        });
        
        
        Button backBtn = (Button) v.findViewById(R.id.cancel_edit_dismiss_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        
        return v;
    }

    public void onClick(View v) {
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

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

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
    
}
