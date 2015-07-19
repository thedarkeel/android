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
        mp.setLooping(true);

    }

    public void onDestroy()
    {
        mp.stop();
        mp.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mp.start();
        return Service.START_NOT_STICKY;
    }

}
