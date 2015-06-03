package com.nikitaend.polproject.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.view.CircleView;
import com.nikitaend.polproject.view.VerticalSeekBar;

/**
 * @author Endaltsev Nikita
 *         start at 29.05.15.
 */
public class EditSettingsDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private double temperature;
    private boolean dayNight;
    
    public static EditSettingsDialog newInstance(boolean dayNight, double temperature) {
        EditSettingsDialog editDialog = new EditSettingsDialog();

        Bundle args = new Bundle();
        args.putDouble("temperature", temperature);
        args.putBoolean("dayNight", dayNight);
        editDialog.setArguments(args);

        return editDialog;
    }

    public static interface OnCompleteListener {

        public abstract void onComplete(boolean dayNight, double temperature);
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
        
        Bundle args = getArguments();
        if (args != null) {
            temperature = args.getDouble("temperature");
            dayNight = args.getBoolean("dayNight");
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Temperature editing");
        final View v = inflater.inflate(R.layout.dialog_settings_edit, null);

        
        final CircleView editTarget = (CircleView) v.findViewById(R.id.edit_settings_circleView);
        editTarget.setTitleText(temperature + "");

        final VerticalSeekBar seekBar = (VerticalSeekBar) v.findViewById(R.id.vertical_settings_Seekbar);
        seekBar.setMax(250);
        seekBar.setProgress((int)temperature * 10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temperature = (progress / 10.0) + 5;
                editTarget.setTitleText(temperature + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button editBtn = (Button) v.findViewById(R.id.edit_settings_dialog_button);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temperature = (seekBar.getProgress() / 10.0) + 5;
                mListener.onComplete(dayNight, temperature);
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
