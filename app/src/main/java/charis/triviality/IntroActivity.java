package charis.triviality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class IntroActivity extends Activity {

    Button butNext;
    RadioButton RbTrain;
    RadioButton Rblevel;
    RadioButton Rbsubject;
    boolean train;
    String level, subject;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        butNext=(Button)findViewById(R.id.button1);
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroupSubject);
                RadioGroup grplevel = (RadioGroup) findViewById(R.id.radioGrouplevel);
                if (grp.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Πρέπει να επιλέξεις θέμα!",
                            Toast.LENGTH_LONG).show();
                } else {
                    RbTrain = (RadioButton) findViewById(R.id.selfTrain);
                    Rbsubject = (RadioButton) findViewById(R.id.math);
                    Intent intent = new Intent(IntroActivity.this, QuizActivity.class);
                    Bundle Bu = new Bundle();
                    train = RbTrain.isChecked();
                    int id = grp.getCheckedRadioButtonId();
                    //int idlevel = grplevel.getCheckedRadioButtonId();
                    if (id == Rbsubject.getId()) {
                        subject = "math";
                    } else {
                        subject = "physics";
                    }
                    level = "easy";
                    Bu.putBoolean("IsTraining", train);
                    Bu.putString("level", level);
                    Bu.putString("subject", subject);
                    intent.putExtras(Bu);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    public void onClickMusic(View v)
    {
        Intent i = new Intent(this, MusicService.class);
        startService(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

      switch (item.getItemId()) {
            case R.id.music_on:
                if (item.isChecked()) item.setChecked(false);
                else
                {item.setChecked(true);
                Intent i = new Intent(this, MusicService.class);
                Log.i("music", "music service started");
                startService(i);
                return true;}
            case R.id.music_off:
                if (item.isChecked()) item.setChecked(false);
                else
                {item.setChecked(true);
                Intent in = new Intent(this, MusicService.class);
                Log.i("music", "music service stopped");
                stopService(in);
                return true;}
            case R.id.action_settings:
                if (item.isChecked()) item.setChecked(false);
                else
                {item.setChecked(true);
                //mainLayout.setBackgroundColor(android.graphics.Color.YELLOW);
                return true;}
            default:
                return super.onOptionsItemSelected(item);
        }
    }








    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_red:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                mainLayout.setBackgroundColor(android.graphics.Color.RED);
                return true;
            case R.id.menu_green:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                mainLayout.setBackgroundColor(android.graphics.Color.GREEN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    protected void onDestroy()
    {
        Intent i = new Intent(this, MusicService.class);
        //stopService(i);
        super.onDestroy();
    }

    public void onBackPressed()
    {

    }
}
