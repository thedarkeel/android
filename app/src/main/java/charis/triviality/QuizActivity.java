package charis.triviality;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity
{
    int score=0;
    int qid=0;
    public String timeFinal;
    long startTime = 0;
    long millis = System.currentTimeMillis() - startTime;
    int seconds = (int) (millis / 1000);
    int minutes = seconds / 60;
    List<Question> quesList;
    Question currentQ;
    TextView txtQuestion;
    RadioButton rda, rdb, rdc;
    Button butNext;
    TextView timerTextView;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable()
    {
        @Override
        public void run() {
            millis = System.currentTimeMillis() - startTime;
            seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            timerTextView.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        DataBaseHelper myDbHelper;

        myDbHelper = new DataBaseHelper(this);
        quesList = myDbHelper.getAllQuestions();//εδώ πρέπει να αλλάξω ανάλογα με την επιλογή
        currentQ=quesList.get(qid);
        txtQuestion=(TextView)findViewById(R.id.textView1);
        rda=(RadioButton)findViewById(R.id.radio0);
        rdb=(RadioButton)findViewById(R.id.radio1);
        rdc=(RadioButton)findViewById(R.id.radio2);
        butNext=(Button)findViewById(R.id.button1);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        setQuestionView();
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        butNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RadioGroup grp = (RadioGroup)findViewById(R.id.radioGroup1);
                RadioButton answer = (RadioButton)findViewById(grp.getCheckedRadioButtonId());
                if (grp.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Πρέπει να επιλέξεις μία απάντηση!",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (currentQ.getANSWER().equals(answer.getText()))
                    {
                        score++;
                        Log.d("score", "Your score" + score);
                    }
                    if (qid < 5)
                    {
                        currentQ = quesList.get(qid);
                        setQuestionView();
                    }
                    else
                    {
                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("score", score); //Your score
                        timeFinal = String.format("%d:%02d", minutes, seconds);
                        b.putString("time", timeFinal);
                        intent.putExtras(b); //Put your score to your next Intent
                        timerHandler.removeCallbacks(timerRunnable);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    private void setQuestionView()
    {
        txtQuestion.setText(currentQ.getQUESTION());
        rda.setText(currentQ.getOPTA());
        rdb.setText(currentQ.getOPTB());
        rdc.setText(currentQ.getOPTC());
        RadioGroup grp = (RadioGroup)findViewById(R.id.radioGroup1);
        grp.clearCheck();
        qid++;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }
}