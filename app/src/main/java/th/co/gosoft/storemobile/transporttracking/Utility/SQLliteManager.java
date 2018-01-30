package th.co.gosoft.storemobile.transporttracking.Utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import th.co.gosoft.storemobile.transporttracking.Controller.DB.DBHelper;

/**
 * Created by naphatbur on 12/8/2017 AD.
 */

public class SQLliteManager {

    DBHelper helper;
    SQLiteDatabase db;
    Context ctx;

    public SQLliteManager(Context ctx) {
        this.ctx = ctx;
    }

    public String getSingleRowLocalDB(String field, String table) {
        helper = new DBHelper(ctx);
        db = helper.getWritableDatabase();
        String qr0 = "SELECT "+field+" FROM "+table;
        Cursor cursor0 = db.rawQuery(qr0,null);
        cursor0.moveToFirst();
        return cursor0.getString(0);
    }

    public Integer getCount(String column,String table,int type){
        helper = new DBHelper(ctx);
        db = helper.getWritableDatabase();
        String sql = "SELECT count(*) FROM "+ table+" WHERE "+column+" = '"+type+"'";
        Cursor cusor = db.rawQuery(sql,null);
        cusor.moveToFirst();
        return cusor.getInt(0);
    }

    public Cursor getTypeStore(String table,String column,int type){
        helper = new DBHelper(ctx);
        db = helper.getWritableDatabase();
        String qr = "SELECT * FROM "+table+" WHERE "+column+" = '"+type+"'";
        Cursor cursor = db.rawQuery(qr,null);
        return cursor;
    }

    public Integer getTypeProduct(String table,String columnStore,String StoreID,String columnProduct,int type){
        helper = new DBHelper(ctx);
        db = helper.getWritableDatabase();
        String sql = "SELECT count(*) FROM "+ table+" WHERE "+columnStore+" = '"+StoreID+"' and "+columnProduct+" = '"+type+"'";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Cursor getDataAll(String tableName){
        helper = new DBHelper(ctx);
        db = helper.getWritableDatabase();
        String sql = "SELECT * FROM " + tableName;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

//    private void addPerson(Context context, model_person person){
//        helper = new DBHelper(context);
//        db = helper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Constants.WORK_ID, person.getWorkID());
//        values.put(Constants.STORE_ID,product_store_id);
//        values.put(Constants.PRODUCT_CODE,product_code);
//        values.put(Constants.PRODUCT_NAME,product_name);
//        values.put(Constants.PRODUCT_TYPE,product_type);
//        values.put(Constants.PRODUCT_STATUS,product_status);
//        values.put(Constants.PRODUCT_ADD, product_add);
//        db.insertOrThrow(Constants.TABLE_NAME, null, values);
//    }

}
