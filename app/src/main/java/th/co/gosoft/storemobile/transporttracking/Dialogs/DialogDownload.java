package th.co.gosoft.storemobile.transporttracking.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import th.co.gosoft.storemobile.transporttracking.Controller.DB.DBHelper;
import th.co.gosoft.storemobile.transporttracking.Controller.DB.DataManager;
import th.co.gosoft.storemobile.transporttracking.LoginActivity;
import th.co.gosoft.storemobile.transporttracking.MainActivity;
import th.co.gosoft.storemobile.transporttracking.Models.Constants;
import th.co.gosoft.storemobile.transporttracking.Models.HQ.RequestStoreList;

/**
 * Created by Jubjang on 9/25/2017.
 */

public class DialogDownload extends DialogFragment {
    Activity activity;
    Button btn_download;
    TextView textView_faildownload, indownload;
    ProgressBar progressBar ;
    public static RequestStoreList storeList = new RequestStoreList();
    Handler handler;
    DBHelper helper ;
    SQLiteDatabase db;

    public DialogDownload(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(th.co.gosoft.storemobile.transporttracking.R.layout.dialog_download, container, false);
        btn_download = (Button) v.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.btn_download_downloadretry);
        textView_faildownload = (TextView) v.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.textView_faildownload);
        progressBar = (ProgressBar) v.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.progressBar);
        indownload = (TextView) v.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.indownload);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar.setVisibility(View.VISIBLE);
        indownload.setVisibility(View.VISIBLE);
        btn_download.setVisibility(View.GONE);
        textView_faildownload.setVisibility(View.GONE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        RequestStoreList res = (RequestStoreList) msg.obj;
                        if (res.getReturnDescription().equals("Success")) {
                            //get data
                            storeList.setStoremodel(res.getStoremodel());
                            storeList.setItem(res.getItem());

                            //get first time login
                            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
                            Date currentLocalTime = cal.getTime();
                            DateFormat date = new SimpleDateFormat("HH:mm:ss a");
                            date.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                            String login_time = date.format(currentLocalTime);
                            System.out.println("login_time : "+login_time);

                            //have data in TABLE_NAME_PERSON or not
                            String count3 = "SELECT count(*) FROM "+ Constants.TABLE_NAME_PERSON;
                            helper = new DBHelper(activity);
                            db = helper.getWritableDatabase();
                            Cursor mcursor3 = db.rawQuery(count3,null);
                            mcursor3.moveToFirst();
                            int icount3 = mcursor3.getInt(0);
                            if (icount3>0){
                                String qrDay = "SELECT "+ Constants.PERSON_DAY+" FROM "+ Constants.TABLE_NAME_PERSON;
                                Cursor cursor = db.rawQuery(qrDay,null);
                                cursor.moveToFirst();
                                String db_day = cursor.getString(0);
                                System.out.println("db_day : "+db_day);
                                if (!db_day.equals(currentDateFormat())){
                                    System.out.println("DELETE TABLE");
                                    helper = new DBHelper(getContext());
                                    db = helper.getWritableDatabase();
                                    db.execSQL("delete from "+ Constants.TABLE_NAME);
                                    db.execSQL("delete from "+ Constants.TABLE_NAME_PERSON);
                                    db.execSQL("delete from "+ Constants.TABLE_NAME_STORE);

                                    System.out.println("PERSON TABLE icount3>0");
                                    addPerson(activity,
                                            LoginActivity.person.getPersonID(),
                                            LoginActivity.person.getWorkID(),
                                            login_time,
                                            currentDateFormat(),
                                            LoginActivity.person.getPersonName());
                                }
                                else {
                                    System.out.println("SAME DAY.");
                                }
                            }
                            else {
                                System.out.println("PERSON TABLE else");
                                System.out.println(LoginActivity.person.getPersonID()+ LoginActivity.person.getWorkID() + login_time+currentDateFormat()+ LoginActivity.person.getPersonName());
                                addPerson(activity,
                                        LoginActivity.person.getPersonID(),
                                        LoginActivity.person.getWorkID(),
                                        login_time,
                                        currentDateFormat(),
                                        LoginActivity.person.getPersonName());
                            }

                            //check item table has data or not
                            String count = "SELECT count(*) FROM "+ Constants.TABLE_NAME;
                            Cursor mcursor = db.rawQuery(count, null);
                            mcursor.moveToFirst();
                            int icount = mcursor.getInt(0);
                            if(icount>0){
                                //do nothing
                            }
                            else {
                            //store item into the database
                                System.out.println("ITEM TABLE");
                                for (int i = 0; i < res.getItem().size(); i++){
                                    for (int j = 0; j < res.getItem().get(i).getItemInOut().size(); j++){
                                        addItem(activity,
                                                LoginActivity.person.getWorkID(),
                                                storeList.getItem().get(i).getProductStoreID(),
                                                storeList.getItem().get(i).getItemInOut().get(j).getProductCode(),
                                                storeList.getItem().get(i).getItemInOut().get(j).getProductName(),
                                                storeList.getItem().get(i).getItemInOut().get(j).getProductType(),
                                                storeList.getItem().get(i).getItemInOut().get(j).getProductStatus(),
                                                storeList.getItem().get(i).getItemInOut().get(j).getProductAdd()
                                        );
                                    }
                                }
                            }

                            //check item table has data or not
                            String count2 = "SELECT count(*) FROM "+ Constants.TABLE_NAME_STORE;
                            Cursor mcursor2 = db.rawQuery(count2, null);
                            mcursor2.moveToFirst();
                            int icount2 = mcursor2.getInt(0);
                            if(icount2>0){
                                //do nothing
                            }
                            else {
                                //store into the database
                                System.out.println("STORE TABLE");
                                for (int i = 0; i < res.getStoremodel().size(); i++){
                                    addStore(activity,
                                             LoginActivity.person.getWorkID(),
                                             storeList.getStoremodel().get(i).getStoreID(),
                                             storeList.getStoremodel().get(i).getStoreName(),
                                             storeList.getStoremodel().get(i).getStorePhone(),
                                             storeList.getStoremodel().get(i).getStoreLat(),
                                             storeList.getStoremodel().get(i).getStoreLng(),
                                             storeList.getStoremodel().get(i).getStoreType(),
                                             "0");
                                }
                            }

                            //change to another dialog
                            if (storeList.getStoremodel().size() != 0) {
                                dismiss();
                                final Dialog dialog = new Dialog(activity);
                                dialog.setContentView(th.co.gosoft.storemobile.transporttracking.R.layout.dialog_after_download);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                TextView totalstore = (TextView) dialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.totalstore);
                                TextView tv_date = (TextView) dialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.textsetDate);
                                TextView workID = (TextView) dialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.textsetWorkID);
                                Button btn_download = (Button) dialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.btn_download);

                                tv_date.setText(currentDateFormat());
                                workID.setText("เลขที่ใบงาน "+ LoginActivity.person.getWorkID());
                                totalstore.setText("จำนวนร้านทั้งหมด " + res.getStoremodel().size() + " ร้าน");
                                btn_download.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(activity, MainActivity.class);
                                        activity.startActivity(intent);
                                        activity.finish();
                                        dialog.dismiss();

                                    }
                                });
                                dialog.show();
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            indownload.setVisibility(View.GONE);
                            textView_faildownload.setVisibility(View.VISIBLE);
                            btn_download.setVisibility(View.VISIBLE);
                            btn_download.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    RequestStoreList(handler, LoginActivity.person.getWorkID());
                                    btn_download.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    indownload.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        break;
                    case 1:
                        progressBar.setVisibility(View.GONE);
                        indownload.setVisibility(View.GONE);
                        textView_faildownload.setVisibility(View.VISIBLE);
                        btn_download.setVisibility(View.VISIBLE);
                        btn_download.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                RequestStoreList(handler, LoginActivity.person.getWorkID());
                                btn_download.setVisibility(View.GONE);
                                dismiss();
                            }
                        });
                        System.out.println("StoreList case 1 ");
                        break;
                }
            }
        };
        RequestStoreList(handler, LoginActivity.person.getWorkID());
    }

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    //send request to hq use username and password to check
    public void RequestStoreList(final Handler handler, final String workID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestStoreList res = DataManager.getInstance().downloadWork(workID);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = res;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    //request fail
                    Message message = new Message();
                    message.what = 1;
                    message.obj = e.toString();
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addItem(Context context, String workID, String product_store_id, String product_code,
                         String product_name, String product_type, String product_status, String product_add){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.WORK_ID,workID);
        values.put(Constants.STORE_ID,product_store_id);
        values.put(Constants.PRODUCT_CODE,product_code);
        values.put(Constants.PRODUCT_NAME,product_name);
        values.put(Constants.PRODUCT_TYPE,product_type);
        values.put(Constants.PRODUCT_STATUS,product_status);
        values.put(Constants.PRODUCT_ADD, product_add);
        db.insertOrThrow(Constants.TABLE_NAME, null, values);
    }

    private void addStore(Context context, String workID, String store_id, String store_name,
                         String store_phone, String store_lat, String store_lng, String store_type, String store_status){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.WORK_ID_STORE,workID);
        values.put(Constants.STORE_ID_STORE,store_id);
        values.put(Constants.STORE_NAME,store_name);
        values.put(Constants.STORE_PHONE,store_phone);
        values.put(Constants.STORE_LAT,store_lat);
        values.put(Constants.STORE_LNG,store_lng);
        values.put(Constants.STORE_TYPE, store_type);
        values.put(Constants.STORE_STATUS, store_status);
        db.insertOrThrow(Constants.TABLE_NAME_STORE, null, values);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismiss();
//        db.close();
    }

    public void addPerson(Context context,String person_id, String person_work_id,String person_time, String person_day, String person_name){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.PERSON_ID,person_id);
        values.put(Constants.PERSON_WORKID,person_work_id);
        values.put(Constants.PERSON_TIME,person_time);
        values.put(Constants.PERSON_DAY,person_day);
        values.put(Constants.PERSON_NAME,person_name);
        db.insertOrThrow(Constants.TABLE_NAME_PERSON, null, values);
    }

}
