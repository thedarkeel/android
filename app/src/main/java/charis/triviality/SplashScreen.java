package charis.triviality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashScreen extends Activity
{
    boolean DbFlag = false;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);

        /** New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/

        int SPLASH_DISPLAY_LENGTH = 6000; //Duration of wait
        CheckDB();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Create an Intent that will start the Menu-Activity.
                Intent mainIntent = new Intent(SplashScreen.this, IntroActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void CheckDB ()
    {
        InputStream Src;
        OutputStream Dest;
        File DF = getApplicationContext().getDatabasePath ("triviaTei.sqlite");
        byte[] Buff;
        int r;
        try
        {
            if (!DF.exists ())
            {
                getApplicationContext ().getDatabasePath ("triviaTei.sqlite").getParentFile().mkdir ();
                Src = getAssets().open("triviaTei.sqlite");
                Dest = new FileOutputStream(DF);
                Buff = new byte[1024];
                while ((r = Src.read (Buff)) > 0)
                    Dest.write (Buff, 0,  r);
                Src.close ();
                Dest.close ();
                Toast.makeText(getApplicationContext(), "Copy OK!", Toast.LENGTH_LONG).show();
            }
            DbFlag = true;
        }
        catch (Exception e)
        {
            Toast.makeText (getApplicationContext (), e.getMessage (), Toast.LENGTH_LONG).show();
        }
    }
}