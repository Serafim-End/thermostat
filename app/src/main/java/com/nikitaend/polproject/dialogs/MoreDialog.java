package com.nikitaend.polproject.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.activity.ScheduleActivity;
import com.nikitaend.polproject.adapter.holder.TemperatureHolder;

/**
 * when we press more button on schedule card
 * @author Endaltsev Nikita
 *         start at 09.05.15.
 */
public class MoreDialog extends DialogFragment {
    int indexOfElement = -1;
    String title;
    boolean isEnabled = false;

    public static MoreDialog newInstance(int indexOrElement, String title) {
        MoreDialog moreDialog = new MoreDialog();
        
        Bundle args = new Bundle();
        args.putInt("index", indexOrElement);
        args.putString("title", title);
        
        moreDialog.setArguments(args);
        return moreDialog;
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
        View v = inflater.inflate(R.layout.dialog_more, null);

        isEnabled = true;
//        LinearLayout enabledSwitch = (LinearLayout) v.findViewById(R.id.enabled_dialog_layout);
//        enabledSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // something if on and else
//            }
//        });
//
//        final Switch isEnabledSwitch = (Switch) v.findViewById(R.id.enabled_element);
//        if (isEnabledSwitch.isChecked()) {
//            isEnabled = true;
//        }
//
//        isEnabledSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isEnabledSwitch.isChecked()) {
//                    isEnabled = true;
//                } else { isEnabled = false; }
//
//                TemperatureHolder holder =
//                        ScheduleActivity.temperatureHoldersHash.get(title).get(indexOfElement);
//                holder.isEnabled = isEnabled;
//                ScheduleActivity.temperatureHoldersHash.get(title).set(indexOfElement, holder);
//            }
//        });
        
//        LinearLayout editLayout = (LinearLayout) v.findViewById(R.id.edit_dialog_layout);
//        editLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TemperatureHolder holder =
//                        ScheduleActivity.temperatureHoldersHash.get(title).get(indexOfElement);
//
//                DialogFragment editDialog =
//                        EditDialogListVIew.newInstance(indexOfElement,
//                                holder.startTime, holder.endTime, holder.dayNight, title);
//
//                editDialog.show(getFragmentManager(), "dialogEditListView");
//                dismiss();
//            }
//        });
        
        LinearLayout removeLayout = (LinearLayout) v.findViewById(R.id.remove_dialog_layout);
        removeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DialogFragment removeDialog = RemoveDialog.newInstance(indexOfElement, title);
               removeDialog.show(getFragmentManager(), "dialogRemove");
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


    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
