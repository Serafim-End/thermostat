package com.nikitaend.polproject.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikitaend.polproject.NavigationDrawerFragment;
import com.nikitaend.polproject.R;
import com.nikitaend.polproject.adapter.holder.TemperatureHolder;
import com.nikitaend.polproject.dialogs.EditMainDialog;
import com.nikitaend.polproject.time.NewTime;
import com.nikitaend.polproject.time.Prototype;
import com.nikitaend.polproject.view.CircleView;
import com.nikitaend.polproject.view.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * The main activity of the application
 * the main screen in xml called  activity_main
 */
public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        EditMainDialog.OnCompleteListener {
    
    double targetTemperature = 25;

    final Context mContext = this;

    private Prototype.ChangeTimeTemperatureListener mChangeTimeTemperatureListener;
    


    // time
//    TimeZone timeZone = new SimpleTimeZone(3, TimeZone.getAvailableIDs(3)[0]);
//    Calendar calendar = Calendar.getInstance(timeZone, Locale.getDefault());

    
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
        

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        makeSomeGoal();
        
        if (ScheduleActivity.temperatureHoldersHash == null) {
            ScheduleActivity.temperatureHoldersHash = new HashMap<>();
            for (String day : ScheduleDaysActivity.weekDays) {
                ScheduleActivity.temperatureHoldersHash.put(day, new ArrayList<TemperatureHolder>());
            }
        }
        
        setContentView(R.layout.activity_main);


        final CircleView targetCircle = (CircleView) findViewById(R.id.main_screen_target);
        targetTemperature = Double.parseDouble(targetCircle.getTitleText());
        
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        View fabButton;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fabButton = new FloatingActionButton.Builder(this)
                    .withDrawable(getDrawable(R.drawable.edit))
                    .withButtonColor(Color.WHITE)
                    .withGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                    .withMargins(0, 0, 4, -48)
                    .create();
        } else {
            fabButton = new Button(this);
            fabButton.bringToFront();
        }
        
        targetCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = EditMainDialog.newInstance(targetTemperature);
                dialogFragment.show(getFragmentManager(), "editMainDialog");
            }
        });
        
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = EditMainDialog.newInstance(targetTemperature);
                dialogFragment.show(getFragmentManager(), "editMainDialog");
            }
        });
        
        ((LinearLayout) findViewById(R.id.calendarLayout)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent weekDays = new Intent(mContext, ScheduleDaysActivity.class);
                        startActivity(weekDays);
                    }
                });

        fastTime();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Thermostat");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.day_and_night) {
            Intent intent = new Intent(this, SettingsActiviy.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(double target, Boolean setPermanently) {
        CircleView targetCircle = (CircleView) findViewById(R.id.main_screen_target);
        targetTemperature = target;
        targetCircle.setTitleText(targetTemperature + "");
    }


    /**
     * Methods to make time higher 
     */
    
    private int currentDay;
    private NewTime currentTime = parseNewTimeForInstance();
    
    private NewTime parseNewTimeForInstance() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new NewTime(hour, minute);
        
    }
    
    private double delta;
    public long lastTick = System.currentTimeMillis();

    public void addToCurrentTime(long millis) {
        double min = millis / (60.0 * 1000.0) + delta;
        int m = (int) min;
        int h = currentTime.getHours();
        delta = (min - m);
        m = m + currentTime.getMinutes();
        h += m / 60;
        m %= 60;
        boolean dayChanged = false;
        while (h >= 24) {
            h -= 24;
            currentDay++;
            dayChanged = true;
        }
        currentDay = currentDay % 7;
        NewTime newTime = new NewTime(h, m);
//        if (!permanent) {
//            if (!dayChanged) {
//                updateTemperature(currentTime, newTime);
//            } else {
//                updateTemperature(new NewTime(0, 0), newTime);
//            }
//        }
        currentTime = newTime;
    }

    private void tickTack() {
        final long currentTimeMillis = System.currentTimeMillis();
        addToCurrentTime((currentTimeMillis - lastTick) * 300);
        lastTick = currentTimeMillis;
        updateClock();

    }

    public void updateClock() {
        TextView timeTextView = (TextView) findViewById(R.id.main_time_textView);
        timeTextView.setText(
                NewTime.getWeekDay(currentDay) + " " + currentTime);
    }
    
    private static int timeID = 0;
    private void fastTime() {
        final android.os.Handler handler = new android.os.Handler();
        final int id = ++timeID;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (id == timeID) {
                    tickTack();
                    handler.postDelayed(this, 200);
                }
            }
        }, 200);
    }
    // end of methods that make time higher
    
    
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
