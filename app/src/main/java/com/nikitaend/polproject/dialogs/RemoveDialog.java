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

/**
 * @author Endaltsev Nikita
 *         start at 09.05.15.
 */
public class RemoveDialog extends DialogFragment {
    
    int indexOfElement = -1;

    static RemoveDialog newInstance(int indexToRemove) {
        RemoveDialog removeDialog = new RemoveDialog();
        
        Bundle args = new Bundle();
        args.putInt("index", indexToRemove);
        removeDialog.setArguments(args);
        return removeDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indexOfElement = getArguments().getInt("index");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Remove interval");
        View v = inflater.inflate(R.layout.dialog_more_remove, null);
        
        Button removeBtn = (Button) v.findViewById(R.id.more_remove_button);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.temperatureHolderList.remove(indexOfElement);
                    ScheduleActivity.adapterCard.notifyDataSetChanged();
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
