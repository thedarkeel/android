package charis.triviality;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service
{
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

   MediaPlayer mp;

        public void onCreate()
        {
            mp = MediaPlayer.create(this, R.raw.born_in_winter);
            mp.setLooping(false);
        }
        public void onDestroy()
        {
            mp.stop();
        }
        @Override
        public void onStart(Intent intent,int startid){

            Log.d("tag", "On start");
            mp.start();
        }

}
