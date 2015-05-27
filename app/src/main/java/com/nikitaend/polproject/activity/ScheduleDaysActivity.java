package com.nikitaend.polproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nikitaend.polproject.R;

public class ScheduleDaysActivity extends Activity {
    
    public static String[] weekDays = 
            {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_days);
        
        onClickDay(getString(R.string.monday), R.id.monday);
        onClickDay(getString(R.string.tuesday), R.id.tuesday);
        onClickDay(getString(R.string.wednesday), R.id.wednesday);
        onClickDay(getString(R.string.thursday), R.id.thursday); 
        onClickDay(getString(R.string.friday), R.id.friday);
        onClickDay(getString(R.string.saturday), R.id.saturday);
        onClickDay(getString(R.string.sunday), R.id.sunday);
        
    }

    private void onClickDay(final String title, int resId) {
        findViewById(resId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleDaysActivity.this, ScheduleActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule_days, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
