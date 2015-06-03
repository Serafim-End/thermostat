package com.nikitaend.polproject.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.activity.MainActivity;
import com.nikitaend.polproject.activity.ScheduleActivity;
import com.nikitaend.polproject.time.Weekday;

/**
 * dialog which called from more dialog
 * @author Endaltsev Nikita
 *         start at 09.05.15.
 */
public class RemoveDialog extends DialogFragment {
    
    int indexOfElement = -1;
    String title;
    
    static RemoveDialog newInstance(int indexToRemove, String title) {
        RemoveDialog removeDialog = new RemoveDialog();
        
        Bundle args = new Bundle();
        args.putInt("index", indexToRemove);
        args.putString("title", title);
        removeDialog.setArguments(args);
        return removeDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle args = getArguments();
        if (args != null) {
            indexOfElement = args.getInt("index");
            title = args.getString("title");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Remove interval");
        View v = inflater.inflate(R.layout.dialog_more_remove, null);
        
        
        Button cancelBtn = (Button) v.findViewById(R.id.cancel_remove_button);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        
        Button removeBtn = (Button) v.findViewById(R.id.more_remove_button);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ScheduleActivity.temperatureHoldersHash.get(title).remove(indexOfElement);
                    ScheduleActivity.adapterCard.notifyDataSetChanged();
                    
                    if (MainActivity.thermostat != null) {
                        try {
                            String sub = title.substring(0, 3);
                            MainActivity
                                    .thermostat
                                    .removeIntervalByIndex(indexOfElement,
                                            Weekday.getWeekDayByString(sub.toLowerCase()));
                        } catch (Exception e) {}
                    }
                } catch (Exception e) { }
                
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
