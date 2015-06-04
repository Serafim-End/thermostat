package com.nikitaend.polproject.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.adapter.TemperatureAdapter;
import com.nikitaend.polproject.adapter.holder.TemperatureHolder;
import com.nikitaend.polproject.dialogs.EditDialog;
import com.nikitaend.polproject.dialogs.EditDialogListVIew;
import com.nikitaend.polproject.dialogs.SameDialog;
import com.nikitaend.polproject.dialogs.TimePickerFragment;
import com.nikitaend.polproject.view.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * activity with list of cards (temperatures in out schedule)
 * for every list somewhere exists adapter because of adapter
 * we can form out cards with temperature customizes
 * TimePickerFragment.OnCompleteListener  - it is way to get information from picker
 * EditDialog.OnCompleteListener - way to get info from edit dialog
 * xml screen activity_schedule
 */
public class ScheduleActivity extends Activity
        implements EditDialog.OnCompleteListener, TimePickerFragment.OnCompleteListener,
        TimePickerFragment.OnCompleteEditListener {

    public static HashMap<String, ArrayList<TemperatureHolder>> temperatureHoldersHash;


    public static TemperatureAdapter adapterCard;
    DialogFragment editFragment;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_schedule);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            title = args.getString("title");
        }

        if (title != null) {
            setTitle(title);
        }

        View fabButton;
        fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getDrawable(R.drawable.plus_icon))
                .withButtonColor(Color.WHITE)
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 4, 4)
                .create();


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFragment = EditDialog.newInstance(title);
                editFragment.show(getFragmentManager(), "editMainDialog");
            }
        });

        ListView cardListView = (ListView) findViewById(R.id.card_schedule_listView);
        adapterCard = new TemperatureAdapter(this, R.layout.card_schedule,
                ScheduleActivity.temperatureHoldersHash.get(title), title);
        cardListView.setAdapter(adapterCard);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.the_same_for_all) {
            DialogFragment sameDialog = SameDialog.newInstance(title);
            sameDialog.show(getFragmentManager(), "sameDialog");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(String startTime, String endTime, Boolean dayNight) {

        String dayOrNight = "";
        if (dayNight) {
            dayOrNight = "AM";
        } else {
            dayOrNight = "PM";
        }

        TemperatureHolder temperatureHolder =
                new TemperatureHolder(startTime, endTime, dayOrNight, false);

        ScheduleActivity.temperatureHoldersHash.get(title).add(temperatureHolder);

        try {
            insertIntervalDataHash();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            ScheduleActivity.temperatureHoldersHash.get(title).remove(temperatureHolder);
            return;
        }
        adapterCard.notifyDataSetChanged();
    }

    @Override
    public void onComplete(boolean startEndTime, String timeHolder) {
        if (editFragment.getView() != null) {
            if (startEndTime) {
                TextView startView =
                        (TextView) editFragment.getView().findViewById(R.id.start_time_textView);
                startView.setText(timeHolder);
            } else {
                TextView endView =
                        (TextView) editFragment.getView().findViewById(R.id.end_time_textView);
                endView.setText(timeHolder);
            }
        } else if (adapterCard.dialogFragment.getView() != null) {
            if (startEndTime) {
                TextView startView =
                        (TextView) adapterCard.dialogFragment.getView()
                                .findViewById(R.id.start_time_textView);
                startView.setText(timeHolder);
            } else {
                TextView endView =
                        (TextView) adapterCard.dialogFragment.getView()
                                .findViewById(R.id.end_time_textView);
                endView.setText(timeHolder);
            }
        }
    }

    public void insertIntervalDataHash() throws Exception {

        String amPmSeparator = " ";
        String hoursMinutesSeparator = ":";
        for (int i = 0; i < ScheduleActivity.temperatureHoldersHash.size(); i++) {
            ArrayList<TemperatureHolder> temperatureHolders = ScheduleActivity.temperatureHoldersHash.get(ScheduleDaysActivity.weekDays[i]);
            for (TemperatureHolder temperatureHolder : temperatureHolders) {
                if (temperatureHolder.isAdded) {
                    continue;
                }
                String currentWeekday = ScheduleDaysActivity.weekDays[i].substring(0, 3);

//                String[] startTimeArray = temperatureHolder.startTime.split(amPmSeparator);
//                String[] startTimeHours = startTimeArray[0].split(hoursMinutesSeparator);
//                int startTimeInt = Integer.parseInt(startTimeHours[0]);
//                if (Objects.equals(startTimeArray[1], "PM")) {
//                    startTimeInt += 12;
//                }
//                String startTime = newTitle + " " + startTimeInt + hoursMinutesSeparator + startTimeHours[1];
                String startTime = currentWeekday + " " + temperatureHolder.startTime;
//                String[] temp2 = temperatureHolder.endTime.split(amPmSeparator);
//                String[] endTimeHours = temp2[0].split(hoursMinutesSeparator);
//                int endTimeInt = Integer.parseInt((endTimeHours[0]));
//                if (Objects.equals(temp2[1], "PM")) {
//                    endTimeInt += 12;
//                }
//                String endTime = newTitle + " " + endTimeInt + hoursMinutesSeparator + endTimeHours[1];
                String endTime = currentWeekday + " " + temperatureHolder.endTime;

                MainActivity.thermostat.addInterval(startTime, endTime);
                temperatureHolder.isAdded = true;

            }
        }

    }

    @Override
    public void onComplete(int indexOfElement, String startTime, String endTime, String dayNight, String title) {
        DialogFragment editDialogListVIew =
                EditDialogListVIew.newInstance(indexOfElement, startTime, endTime, dayNight, title);
        editDialogListVIew.show(getFragmentManager(), "editDialog");
    }

}
