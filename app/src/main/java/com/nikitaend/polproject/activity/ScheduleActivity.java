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
//    public static TemperatureNewAdapter adapterCard;
    
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

//        System.out.println("substring is: " + title.substring(0,3));
//        ArrayList<TimeInterval> intervals =
//                MainActivity.thermostat.getSchedule(Weekday.getWeekDayByString(title.substring(0, 3).toLowerCase()));
//        adapterCard = new TemperatureNewAdapter(this,
//                R.layout.card_schedule,
//               intervals, title);
        
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
        
//        try {
//            String newTitle = title.substring(0,3);
//            MainActivity.thermostat.addInterval(newTitle + " " + parseTime(temperatureHolder.startTime, temperatureHolder.dayNight),
//                    newTitle + " " + parseTime(temperatureHolder.endTime, dayOrNight));
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
        adapterCard.notifyDataSetChanged();
    }
    
    private String parseTime(String time, String dayOrNight) {
        String newStartTime;
        if (dayOrNight == "AM") newStartTime = time;
        else {
            String[] tempTimes = time.split(":");
            newStartTime = Integer.parseInt(tempTimes[0]) + ":" + tempTimes[1];
        }
        
        return newStartTime;
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
