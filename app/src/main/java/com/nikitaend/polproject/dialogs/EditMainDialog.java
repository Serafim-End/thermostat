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
import com.nikitaend.polproject.activity.MainActivity;
import com.nikitaend.polproject.view.CircleView;
import com.nikitaend.polproject.view.VerticalSeekBar;

/**
 * it is editing on main screen with seek bar and clocks
 * implementation of interface - put info to MainActivity
 *
 * @author Endaltsev Nikita
 *         start at 09.05.15.
 */
public class EditMainDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private double targetTemperature;
    private boolean hasChanged = false;

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
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
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
        System.out.println("CLICK!");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Temperature editing");
        final View v = inflater.inflate(R.layout.dialog_main_edit, null);

        final CheckBox permanently = (CheckBox) v.findViewById(R.id.permanently_checkBox);
        permanently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.thermostat.isVacationMode) {
                    MainActivity.thermostat.setVacationMode(false);
                } else {
                    MainActivity.thermostat.setVacationMode(true);
                }
            }
        });

        if (MainActivity.thermostat.isVacationMode == true) {
            permanently.setChecked(true);
        } else {
            permanently.setChecked(false);
        }

//        final CheckBox permanently = (CheckBox) v.findViewById(R.id.permanently_checkBox);
        final CircleView editTarget = (CircleView) v.findViewById(R.id.edit_target_circleView);
        editTarget.setTitleText(targetTemperature + "");


        final VerticalSeekBar seekBar = (VerticalSeekBar) v.findViewById(R.id.vertical_Seekbar);
        seekBar.setMax(250);
        seekBar.setProgress((int) targetTemperature*10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hasChanged = true;
                targetTemperature = (progress / 10.0) + 5;
                editTarget.setTitleText(targetTemperature + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        ImageButton minBtn = (ImageButton) v.findViewById(R.id.min_temperature);
//        minBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                seekBar.setProgress((seekBar.getProgress() - 1));
//                targetTemperature = (seekBar.getProgress() / 10) + 5;
//            }
//        });
//
//        ImageButton maxBtn = (ImageButton) v.findViewById(R.id.max_temperature);
//        maxBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                seekBar.setProgress((seekBar.getProgress() + 1));
//                targetTemperature = (seekBar.getProgress() / 10) + 5;
//            }
//        });

        Button editBtn = (Button) v.findViewById(R.id.edit_main_dialog_button);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double temp = (seekBar.getProgress() / 10.0);
                if (hasChanged) {
                    targetTemperature = (seekBar.getProgress() / 10.0) + 5;
                } else {
                    targetTemperature = temp;
                }
                try {
                    MainActivity.thermostat.setManualTemperatureValue(targetTemperature);

                    System.out.println(targetTemperature);
                    MainActivity.thermostat.setManualMode(true);

                } catch (Exception e) {

                }


                mListener.onComplete(targetTemperature, MainActivity.thermostat.isVacationMode);
                dismiss();
            }
        });

        Button cancelBtn = (Button) v.findViewById(R.id.cancel_main_dialog_button);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    public static double roundToDecimals(double d, int c) {
        int temp = (int) ((d * Math.pow(10, c)));
        return (((double) temp) / Math.pow(10, c));
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
