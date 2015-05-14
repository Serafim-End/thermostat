package com.nikitaend.polproject.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nikitaend.polproject.R;

/**
 * when we press more button on schedule card
 * @author Endaltsev Nikita
 *         start at 09.05.15.
 */
public class MoreDialog extends DialogFragment {
    int indexOfElement = -1;

    public static MoreDialog newInstance(int indexOrElement) {
        MoreDialog moreDialog = new MoreDialog();
        
        Bundle args = new Bundle();
        args.putInt("index", indexOrElement);
        
        moreDialog.setArguments(args);
        return moreDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        indexOfElement = getArguments().getInt("index");
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_more, null);
        
        LinearLayout enabledSwitch = (LinearLayout) v.findViewById(R.id.enabled_dialog_layout);
        enabledSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // something if on and else
            }
        });
        
        LinearLayout editLayout = (LinearLayout) v.findViewById(R.id.edit_dialog_layout);
        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment editDialog = EditDialogListVIew.newInstance(indexOfElement);
                editDialog.show(getFragmentManager(), "dialogEditListView");
                dismiss();
            }
        });
        
        LinearLayout removeLayout = (LinearLayout) v.findViewById(R.id.remove_dialog_layout);
        removeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
//                if (prev != null) {
//                    ft.remove(prev);
//                }
//                ft.addToBackStack(null);
                
               DialogFragment removeDialog = RemoveDialog.newInstance(indexOfElement);
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
