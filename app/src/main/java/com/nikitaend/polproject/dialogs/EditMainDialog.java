package com.nikitaend.polproject.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.view.CircleView;
import com.nikitaend.polproject.view.VerticalSeekBar;

/**
 * 
 * it is editing on main screen with seek bar and clocks
 * implementation of interface - put info to MainActivity
 * @author Endaltsev Nikita
 *         start at 09.05.15.
 */
public class EditMainDialog extends DialogFragment implements DialogInterface.OnClickListener {
    
    private double targetTemperature;
    
    public static EditMainDialog newInstance(double targetTemperature) {
        EditMainDialog editMainDialog = new EditMainDialog();
        
        Bundle args = new Bundle();
        args.putDouble("targetTemperature", targetTemperature);
        editMainDialog.setArguments(args);
        
        return editMainDialog;
    }

    public static interface OnCompleteListener {

        public abstract void onComplete(double targetTemperature, Boolean setPermanently);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetTemperature = getArguments().getDouble("targetTemperature");
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Temperature editing");
        final View v = inflater.inflate(R.layout.dialog_main_edit, null);

        final CheckBox permanently = (CheckBox) v.findViewById(R.id.permanently_checkBox);
        final CircleView editTarget = (CircleView) v.findViewById(R.id.edit_target_circleView);
        editTarget.setTitleText(targetTemperature + "");
        
        final VerticalSeekBar seekBar = (VerticalSeekBar) v.findViewById(R.id.vertical_Seekbar);
        seekBar.setMax(25);
        seekBar.setProgress((int)targetTemperature);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                targetTemperature = progress + 5;
                editTarget.setTitleText(targetTemperature + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        
        Button editBtn = (Button) v.findViewById(R.id.edit_main_dialog_button);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetTemperature = seekBar.getProgress() + 5;
                mListener.onComplete(targetTemperature, permanently.isEnabled());
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
