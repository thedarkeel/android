package charis.triviality;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;



public class ResultActivity extends Activity {

    public String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //get rating bar object
        RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1);
        bar.setNumStars(5);
        bar.setStepSize(0.5f);
        //get text view
        TextView t=(TextView)findViewById(R.id.textResult);
        //get score
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        time = b.getString("time");
        //display time
        TextView TextView3 = (TextView) findViewById(R.id.textView3);
        TextView3.setText(time);
        //display score
        bar.setRating(score);
        switch (score)
        {
            case 1:
            case 2: t.setText("Δεν τα πήγες και πολύ καλά!");
                break;
            case 3:
            case 4:t.setText("Χμ... χρειάζεσαι λίγο διάβασμα ακόμα!");
                break;
            case 5:t.setText("Μπράβο! Είσαι πολύ καλός!");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }


    public void onClick (View v)
    {
        Intent ri = new Intent (this, IntroActivity.class);
        startActivity(ri);
    }

}