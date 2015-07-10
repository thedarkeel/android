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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        //Intent i = new Intent(this, MusicService.class);
        //startService(i);
        butNext=(Button)findViewById(R.id.button1);
        butNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RadioGroup grp = (RadioGroup)findViewById(R.id.radioGroupSubject);
                if(grp.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Πρέπει να επιλέξεις θεματολογία!",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(IntroActivity.this, QuizActivity.class);
                    //Bundle b = new Bundle();
                    //b.putInt("score", score); //Your score
                    //intent.putExtras(b); //Put your score to your next Intent
                    startActivity(intent);
                    finish();
                }
            }
        });
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        Intent i = new Intent(this, MusicService.class);
        //stopService(i);
        super.onDestroy();
    }
}
