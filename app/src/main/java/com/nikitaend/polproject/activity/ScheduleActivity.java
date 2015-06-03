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
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        
        ArrayList<TemperatureHolder> daySchedule = ScheduleActivity.temperatureHoldersHash.get(title);
        if (daySchedule.size() == 0) {
            ScheduleActivity.temperatureHoldersHash.get(title).add(temperatureHolder);
            adapterCard.notifyDataSetChanged();
        } else {
            boolean isAdd = true;
            for (TemperatureHolder holder : daySchedule) {
                try {
                    if (compareTimes(holder.startTime, temperatureHolder.endTime)) {
                        isAdd = true;
                    }

                    if (compareTimes(temperatureHolder.startTime, holder.endTime)) {
                        isAdd = true;
                    } else {
                        isAdd = false;
                        break;
                    }
                } catch (Exception e) {
                }
            }
            if (isAdd) {
                ScheduleActivity.temperatureHoldersHash.get(title).add(temperatureHolder);
                adapterCard.notifyDataSetChanged();
                
                if (MainActivity.thermostat != null) {
                    try {
                        String sub = title.substring(0,3);
                        int sHours = 0;
                        int eHours = 0;
                        int sMinutes = 0;
                        int eMinutes = 0;

                        String newStartTimes[] = temperatureHolder.startTime.split(" ");
                        if (newStartTimes[1].contains("PM")) {
                            sHours += 12;
                        }
                        String[] start = newStartTimes[0].split(":");
                        sHours += Integer.parseInt(start[0]);
                        sMinutes += Integer.parseInt(start[1]);
                        
                        String[] newEndTimes = temperatureHolder.endTime.split(" ");
                        if (newEndTimes[1].contains("PM")) {
                            eHours += 12;
                        }
                        String[] end = newEndTimes[0].split(":");
                        eHours += Integer.parseInt(end[0]);
                        eMinutes += Integer.parseInt(end[1]);
                        
                        String toStart = sub + " " + sHours + ":" + sMinutes;
                        String toEnd = sub + " " + eMinutes + ": " + eMinutes;
                        
                        MainActivity.thermostat.addInterval(toStart, toEnd);
                    } catch (Exception e) {}
                }
            } else {
                Toast.makeText(this, "try to add correct time", Toast.LENGTH_LONG).show();
            }
        }

//        MainActivity.thermostat.insertIntervalDataHash();
    }
    
    private boolean compareTimes(String startTime, String endTime) {
        int startHour = 0;
        int startMinute = 0;
        int endHour = 0;
        int endMinute = 0;

        String[] startTimes = startTime.split(" ");
        if (startTimes[1].contains("PM")) {
            startHour += 12;
        }
        String[] startLeftTime = startTimes[0].split(":");
        startHour += Integer.parseInt(startLeftTime[0]);
        startMinute += Integer.parseInt(startLeftTime[1]);

        String[] endTimes = endTime.split(" ");
        if (endTimes[1].contains("PM")) {
            endHour += 12;
        }
        String[] endLeftTime = endTimes[0].split(":");
        endHour += Integer.parseInt(endLeftTime[0]);
        endMinute += Integer.parseInt(endLeftTime[1]);

        if (startHour > endHour) {
            return true;
        } else if (startHour == endHour && startMinute >= endMinute) {
            return true;
        } else {
            return false;
        }
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

    @Override
    public void onComplete(int indexOfElement, String startTime, String endTime, String dayNight, String title) {
        DialogFragment editDialogListVIew =
                EditDialogListVIew.newInstance(indexOfElement, startTime, endTime, dayNight, title);
        editDialogListVIew.show(getFragmentManager(), "editDialog");
    }

}
