package charis.triviality;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper
{
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/charis.triviality/databases/";
    private static String DB_NAME = "triviaTei.sqlite";
    private static final String TABLE_NAME = "quest";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DataBaseHelper(Context context)
    {//Constructor. Takes and keeps a reference of the passed context in order to access
    // the application assets and resources.
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    //Creates a empty database on the system and rewrites it with your own database.

    /*public void createDataBase() throws IOException
    {//Creates a empty database on the system and rewrites it with your own database.
        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");
            }
        }
    }*/

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
        try
        {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        }
        catch(SQLiteException e)
        {
            //database doesn't exist yet.
        }

        if(checkDB != null)
        {
            checkDB.close();

        }

        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException
    {
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException
    {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close()
    {

        if (myDataBase != null)
        {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }


    public List<Question> getQuestions(String level, String subject)
    {
        List<Question> quesList = new ArrayList<>();
        //myDataBase = this.getReadableDatabase();
        openDataBase();
        String Query1 = "SELECT * FROM " + TABLE_NAME;// +" WHERE subject like " + "'" + subject + "'";
        //+ " AND level like " + "'" +level+ "'";
        //String Query1 = "SELECT * FROM " + TABLE_NAME +" WHERE level like ? AND subject like ?";

        Log.i("skatakia", Query1);
        Log.i("level", level);
        Log.i("subject", subject);
        String[] Query2;
        //Query2 = new String [] {String.valueOf (level), String.valueOf (subject) };
        //Query2 = new String [] {"hard", "physics"};

        //Cursor cursor = myDataBase.rawQuery(Query1, new String[] {"hard", "math"});
        Cursor cursor = myDataBase.rawQuery(Query1, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

}