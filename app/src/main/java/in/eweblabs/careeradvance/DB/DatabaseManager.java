package in.eweblabs.careeradvance.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Akash.Singh on 7/30/2015.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    private static String DB_NAME = "db_careeradvance.el";
 //   private static String DB_PATH = "/data/data/"+ StaticConstant.PACKAGE_NAME+"/databases/";
    public static int WAITING_TIME = 3000;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseManager(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
       /* if(android.os.Build.VERSION.SDK_INT >= 4.2){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }*/
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
   /* public void createDataBase() throws IOException{

       // boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            *//*try {

                copyDataBase();

            } catch (IOException e) {
                throw new RuntimeException(e);

            }*//*
        }

    }*/

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
   /* private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            Logger.e("DatabaseManager",""+e.getMessage());
            //e.printStackTrace();
            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }*/

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName =  DB_NAME;

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

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath =  DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ca_recent_search (id INTEGER PRIMARY KEY  NOT NULL" +
                " ,keyword TEXT NOT NULL ,location TEXT NOT NULL  DEFAULT (null) )");
        db.execSQL("CREATE TABLE ca_job_keywords (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , keyword TEXT)");
        db.execSQL("CREATE TABLE ca_job_location (id INTEGER PRIMARY KEY  NOT NULL  UNIQUE , location TEXT NOT NULL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}


