package com.nikitaend.polproject.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.activity.MainActivity;
import com.nikitaend.polproject.activity.ScheduleActivity;
import com.nikitaend.polproject.adapter.holder.TemperatureHolder;

/**
 * 
 * i have made two same edit dialogs one for adding and editing and next
 * for schedule which called from more_dialog
 * these for  dialog_more_edit
 * @author Endaltsev Nikita
 *         start at 11.05.15.
 */
public class EditDialogListVIew  extends DialogFragment {
    
    int indexOfElement;

    public static EditDialogListVIew newInstance(int index) {
        EditDialogListVIew editDialogListVIew = new EditDialogListVIew();
        
        Bundle args = new Bundle();
        args.putInt("index", index);
        
        editDialogListVIew.setArguments(args);
        return editDialogListVIew;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        indexOfElement = getArguments().getInt("index");
    }

    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Edit interval");
        View v = inflater.inflate(R.layout.dialog_more_edit, null);

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
        
        final Switch dayNightSwitch = (Switch) v.findViewById(R.id.dayNight_switch);

        Button okBtn = (Button) v.findViewById(R.id.more_edit_dismiss_button);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemperatureHolder holder = MainActivity.temperatureHolderList.get(indexOfElement);
                
                holder.startTime = startTimeTextView.getText().toString();
                holder.endTime = endTimeTextView.getText().toString();
                
                if (dayNightSwitch.isChecked()) {
                    holder.dayNight = "PM";
                } else { holder.dayNight = "AM"; }
                ScheduleActivity.adapterCard.notifyDataSetChanged();
                
                MainActivity.temperatureHolderList.set(indexOfElement, holder);
                dismiss();
            }
        });

        return v;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
