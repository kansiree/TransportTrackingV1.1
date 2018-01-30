package th.co.gosoft.storemobile.transporttracking.Controller.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import th.co.gosoft.storemobile.transporttracking.Models.Constants;

/**
 * Created by Jubjang on 10/2/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "item.db";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + Constants.TABLE_NAME +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.WORK_ID + " TEXT, " + Constants.STORE_ID + " TEXT, " +
                Constants.PRODUCT_CODE + " TEXT, " + Constants.PRODUCT_NAME + " TEXT, " +
                Constants.PRODUCT_TYPE + " TEXT, " + Constants.PRODUCT_STATUS + " TEXT, " +
                Constants.PRODUCT_ADD + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
        Log.d("CREATE TABLE","Create ITEM Table Successfully.");

        String CREATE_STORE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_STORE +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.WORK_ID_STORE + " TEXT, " + Constants.STORE_ID_STORE + " TEXT, " +
                Constants.STORE_NAME + " TEXT, " + Constants.STORE_PHONE + " TEXT, " +
                Constants.STORE_LAT + " TEXT, " + Constants.STORE_LNG + " TEXT, " +
                Constants.STORE_STATUS + " TEXT, " +Constants.STORE_TYPE + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_STORE_TABLE);
        Log.d("CREATE TABLE","Create STORE Table Successfully.");

        String CREATE_PERSON_TABLE = "CREATE TABLE "+ Constants.TABLE_NAME_PERSON +
                " (" + Constants.PERSON_ID + " TEXT PRIMARY KEY, "
                + Constants.PERSON_WORKID + " TEXT, " + Constants.PERSON_DAY + " TEXT, " +
                Constants.PERSON_TIME + " TEXT, "+ Constants.PERSON_NAME + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_PERSON_TABLE);
        Log.d("CREATE TABLE","Create PERSON Table Successfully.");

        String CREATE_PICTURES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_PIC +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.PIC_STOREID + " TEXT, "
                + Constants.PIC_IMAGE + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_PICTURES_TABLE);
        Log.d("CREATE TABLE","Create PICTURES Table Successfully.");

        String CREATE_SIGNATURES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_SIGN +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.SIGN_STOREID + " TEXT, "
                + Constants.SIGN_IMAGE + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_SIGNATURES_TABLE);
        Log.d("CREATE TABLE","Create SIGNATURES Table Successfully.");

        String CREATE_COMMENT_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_COMMENT +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.COM_STOREID + " TEXT, "
                + Constants.COM_TEXT + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_COMMENT_TABLE);
        Log.d("CREATE TABLE","Create SIGNATURES Table Successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS item";
//        sqLiteDatabase.execSQL(DROP_ITEM_TABLE);
//        onCreate(sqLiteDatabase);
//        Log.d("DROP TABLE","DROP TABLE Successfully.");
    }
}
