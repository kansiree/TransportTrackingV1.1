package th.co.gosoft.storemobile.transporttracking.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import th.co.gosoft.storemobile.transporttracking.Adapters.CustomDropdownAdapter;
import th.co.gosoft.storemobile.transporttracking.Adapters.PhotoAdapter;
import th.co.gosoft.storemobile.transporttracking.Controller.DB.DBHelper;
import th.co.gosoft.storemobile.transporttracking.Dialogs.DialogAdd;
import th.co.gosoft.storemobile.transporttracking.Dialogs.onClickfunction;
import th.co.gosoft.storemobile.transporttracking.Models.Constants;
import th.co.gosoft.storemobile.transporttracking.Models.model_inout;
import th.co.gosoft.storemobile.transporttracking.Models.model_photo;
import th.co.gosoft.storemobile.transporttracking.R;
import th.co.gosoft.storemobile.transporttracking.Utility.ClickAdd;
import th.co.gosoft.storemobile.transporttracking.Utility.ListDetailListener;

/**
 * Created by Jubjang on 9/11/2017.
 */

public class ListDetail extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int CAMERA_REQUEST = 1888;
    Button dialog_btn_save, dialog_btn_clear, btn_confirm_last, btn_confirm ;
    Button photo,photo_shelf;
    Spinner edt_note,edt_note_shelf;
    ImageView img_sign,image_type,image_type_onShelf;
    LinearLayout signaturePad, mContent;
    signature mSignature;
    Dialog dialog;
    Activity activity, activityPhoto;
    List<model_inout> data;
    List<model_photo> photos;
    List<model_photo> photosDB;
    List<model_inout> inbox;
    List<model_inout> outbox;
    RecyclerView recyclerView,recyclerView_Shelf;
    RelativeLayout layout_onbook,layout_onshelf,layout_head_onBook,layout_head_onShelf;
    RecyclerView recyclerViewPhoto,recyclerViewPhoto_Shelf;
    PhotoAdapter adapterPhoto;
    RecyclerView.LayoutManager layoutManager, layoutManagerPhoto;
    String store_id;
    TextView tv_storeName, tv_storeID, tv_storePhone;
    public static int id = 0;
    DBHelper helper;
    SQLiteDatabase db;
    int itemInOutSize = 0;

    public static ClickAdd clickAdd;
    public static ListDetailListener callDetail;
    String note = null;
    public static int count_add = 0;
    public static String scan_add_result = "";
    Bitmap savebm;

    public static onClickfunction event;

    private void view(){
        layout_onbook = (RelativeLayout)findViewById(R.id.layout_detail_on_book);
        layout_onshelf = (RelativeLayout)findViewById(R.id.layout_detail_onShelf);

        layout_head_onBook = (RelativeLayout)findViewById(R.id.layout_head_onBook);
        layout_head_onShelf = (RelativeLayout)findViewById(R.id.layout_head_onShelf);

        tv_storeID = (TextView) findViewById(th.co.gosoft.storemobile.transporttracking.R.id.storeID);
        tv_storeName = (TextView) findViewById(th.co.gosoft.storemobile.transporttracking.R.id.storeName);
        tv_storePhone = (TextView) findViewById(th.co.gosoft.storemobile.transporttracking.R.id.storePhone);

        btn_confirm_last = (Button) findViewById(th.co.gosoft.storemobile.transporttracking.R.id.btn_confirm_last);
        image_type = (ImageView) findViewById(R.id.image_type);
        image_type_onShelf = (ImageView)findViewById(R.id.image_type_onShelf);

        btn_confirm = (Button) findViewById(th.co.gosoft.storemobile.transporttracking.R.id.btn_confirm);

        photo = (Button) findViewById(th.co.gosoft.storemobile.transporttracking.R.id.btn_photo);
        photo_shelf = (Button)findViewById(R.id.btn_photo_onShelf);

        signaturePad = (LinearLayout) findViewById(th.co.gosoft.storemobile.transporttracking.R.id.signature_pad);
        img_sign = (ImageView) findViewById(th.co.gosoft.storemobile.transporttracking.R.id.img_sign);

        edt_note = (Spinner) findViewById(th.co.gosoft.storemobile.transporttracking.R.id.edt_note);
       edt_note_shelf =(Spinner)findViewById(R.id.edt_note_onShelf);

        activity = this;
        activityPhoto = this;

        recyclerView = (RecyclerView) findViewById(R.id.inout_list);
        recyclerViewPhoto = (RecyclerView) findViewById(R.id.re_photo);

        recyclerView_Shelf = (RecyclerView)findViewById(R.id.inout_list_onShelf);
        recyclerViewPhoto_Shelf = (RecyclerView)findViewById(R.id.re_photo_onShelf);

        layout_onbook.setVisibility(View.GONE);
        layout_onshelf.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(th.co.gosoft.storemobile.transporttracking.R.layout.activity_listdetail);
        view();
        setShowView();

        ArrayList list =new ArrayList<String>(Arrays.asList( getResources().getStringArray(R.array.note)));

        CustomDropdownAdapter cs_adapter = new CustomDropdownAdapter(ListDetail.this,list);
        edt_note.setAdapter(cs_adapter);

        edt_note_shelf.setAdapter(cs_adapter);

        event = new onClickfunction() {
            @Override
            public void Scan() {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
                integrator.setOrientationLocked(false);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        };
        //get store id from Intent
        store_id = getIntent().getStringExtra("STORE_ID");
        System.out.println("storeid : "+store_id);

        String qr = "SELECT * FROM " + Constants.TABLE_NAME_STORE + " WHERE " + Constants.STORE_ID + " = '" + store_id + "'";
        helper = new DBHelper(activity);
        db = helper.getWritableDatabase();
        final Cursor cursor = db.rawQuery(qr, null);
        if (cursor.moveToFirst()) {
            do {
                tv_storeID.setText(cursor.getString(2));
                tv_storeName.setText(cursor.getString(3));
//                tv_storePhone.setText(cursor.getString(4));
            } while (cursor.moveToNext());

        }

        //take a photo
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        photo_shelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);            }
        });

        clickAdd = new ClickAdd() {
            @Override
            public void click(Activity activity) {
                FragmentActivity fragmentActivity = (FragmentActivity) (activity);
                FragmentManager fm = fragmentActivity.getSupportFragmentManager();
                DialogAdd alertDialog = new DialogAdd(activity, store_id);
                alertDialog.show(fm, "fragment_alert");
                count_add++;
            }
        };

        callRecyclerView();

        callDetail = new ListDetailListener() {
            @Override
            public void onclick() {
                callRecyclerView();
            }
        };

        btn_confirm_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper = new DBHelper(activity);
                db = helper.getWritableDatabase();
                String qrAllitem = "SELECT count(*) FROM " + Constants.TABLE_NAME + " WHERE " + Constants.STORE_ID + " = '" + store_id + "'";
                Cursor mcursor = db.rawQuery(qrAllitem, null);
                mcursor.moveToFirst();
                int Allcount = mcursor.getInt(0);

                String qrInbox = "SELECT count(*) FROM " + Constants.TABLE_NAME + " WHERE " + Constants.STORE_ID + " = '" + store_id + "'" + " and "
                        + Constants.PRODUCT_STATUS + " = " + "'success'";
                Cursor cursor = db.rawQuery(qrInbox, null);
                cursor.moveToFirst();
                int count = cursor.getInt(0);

                if (count == Allcount) {
                    helper = new DBHelper(activity);
                    db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Constants.STORE_TYPE, "1");
                    db.update(Constants.TABLE_NAME_STORE, values, Constants.STORE_ID + " = '" + store_id + "'", null);
                    if (edt_note.getSelectedItem()!=null){
                        addComment(activity,store_id,edt_note.getSelectedItem().toString());
                    }
                    activity.finish();
                } else if (count > 0 && count < Allcount) {
                    note = edt_note.getSelectedItem().toString();
                    if (note.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please explain.", Toast.LENGTH_SHORT).show();
                    } else {
                        helper = new DBHelper(activity);
                        db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(Constants.STORE_TYPE, "3");
                        db.update(Constants.TABLE_NAME_STORE, values, Constants.STORE_ID + " = '" + store_id + "'", null);
                        addComment(activity,store_id,edt_note.getSelectedItem().toString());
                        activity.finish();
                    }
                } else if (count == 0) {
                    note = edt_note.getSelectedItem().toString();
                    if (note.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please explain.", Toast.LENGTH_SHORT).show();
                    } else {
                        helper = new DBHelper(activity);
                        db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(Constants.STORE_TYPE, "4");
                        db.update(Constants.TABLE_NAME_STORE, values, Constants.STORE_ID + " = '" + store_id + "'", null);
                        addComment(activity,store_id,edt_note.getSelectedItem().toString());
                        activity.finish();
                    }
                }
            }
        });

        String sql = "SELECT count(*) FROM "+ Constants.TABLE_NAME_SIGN+" WHERE "+ Constants.PIC_STOREID+" = "+"'"+store_id+"'";
        helper = new DBHelper(activity);
        db = helper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(sql,null);
        cursor1.moveToFirst();
        int countSign = cursor1.getInt(0);
        if (countSign>0){
            String sql2 = "SELECT "+ Constants.SIGN_IMAGE+" FROM "+ Constants.TABLE_NAME_SIGN+" WHERE "+ Constants.SIGN_STOREID+" = "+"'"+store_id+"'";
            Cursor cursor2 = db.rawQuery(sql2,null);
            cursor2.moveToFirst();
            String URLsign = cursor2.getString(0);

            File imgFile = new  File(URLsign);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img_sign.setImageBitmap(myBitmap);
            }
        }

        String sqlComment = "SELECT count(*) FROM "+ Constants.TABLE_NAME_COMMENT+" WHERE "+ Constants.COM_STOREID+" = "+"'"+store_id+"'";
        Cursor cursorCom = db.rawQuery(sqlComment,null);
        cursorCom.moveToFirst();
        int countCom = cursorCom.getInt(0);
        if (countCom>0){
            String sqlComment2 = "SELECT "+ Constants.COM_TEXT+" FROM "+ Constants.TABLE_NAME_COMMENT+" WHERE "+ Constants.COM_STOREID+" = "+"'"+store_id+"'";
            Cursor cursorCom2 = db.rawQuery(sqlComment2,null);
            cursorCom2.moveToFirst();
            String comment = cursorCom2.getString(0);
//            edt_note.setText(comment);
        }
    }

    public void callRecyclerView() {
        data = new ArrayList<>();
        inbox = new ArrayList<>();
        outbox = new ArrayList<>();
        // prepare data for recyclerview
        prepareDataDB(activity);
        //display list
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager_onShelf = new LinearLayoutManager(activity);
        recyclerView_Shelf.setLayoutManager(layoutManager_onShelf);


        photos = new ArrayList<>();
        photosDB = new ArrayList<>();
        // prepare photo for recyclerview
        preparePhotosDB(activityPhoto);
        layoutManagerPhoto = new LinearLayoutManager(activityPhoto, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager_Photo_onShelf = new LinearLayoutManager(activityPhoto,LinearLayoutManager.HORIZONTAL,false);
        adapterPhoto = new PhotoAdapter(photosDB, activityPhoto);

        recyclerViewPhoto.setLayoutManager(layoutManagerPhoto);
        recyclerViewPhoto.setAdapter(adapterPhoto);

        recyclerViewPhoto_Shelf.setLayoutManager(layoutManager_Photo_onShelf);
        recyclerViewPhoto_Shelf.setAdapter(adapterPhoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //sacn barcode result
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        System.out.println("Request Code : " + requestCode);
        if (result != null) {
            System.out.println("result != null");
            if (count_add == 1) {
                count_add=0;
                scan_add_result = result.getContents();
                System.out.println("scan_add_result : "+scan_add_result);
            } else {
                if (result.getContents() != null) {
                    String barcode = result.getContents();
                    System.out.println("List SCAN : " + result.getContents());

                    String qr = "SELECT count(" + Constants.PRODUCT_CODE + ") FROM " + Constants.TABLE_NAME + " WHERE " + Constants.STORE_ID + " = '" + store_id + "'" + " and " + Constants.PRODUCT_CODE + " = '" + barcode + "'";
                    helper = new DBHelper(activity);
                    db = helper.getWritableDatabase();
                    Cursor cursor1 = db.rawQuery(qr, null);
                    cursor1.moveToFirst();
                    itemInOutSize = cursor1.getInt(0);
                    if (itemInOutSize != 0) {
                        ContentValues values = new ContentValues();
                        values.put(Constants.PRODUCT_STATUS, "success");
                        db.update(Constants.TABLE_NAME, values, Constants.STORE_ID + " = '" + store_id + "'" + " and " + Constants.PRODUCT_CODE + " = '" + barcode + "'", null);
                        callRecyclerView();
                    } else {
                        Toast.makeText(getApplicationContext(), "Don't have item", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        //take picture
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                String filename = "images_"+currentDateFormat() + ".png";
                createDirectoryAndSaveFile(photo, filename);
                callRecyclerView();
            } else {
                System.out.println("can't use camera");
            }
        }
        //speech to text
        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> resultText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                String edt_text = edt_note.getText().toString();
//                edt_note.setText(edt_text+resultText.get(0));
//                edt_note.setSelection(edt_note.length());
            }else {
                System.out.println("Did you say something?");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Dialog Function Signature
        dialog = new Dialog(ListDetail.this);
        // Removing the features of Normal Dialogs
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(th.co.gosoft.storemobile.transporttracking.R.layout.dialog_signature);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        mContent = (LinearLayout) dialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.dialog_signature_pad);
        mContent.setBackgroundColor(Color.WHITE);
        mSignature = new signature(getApplicationContext(), null);
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog_btn_save = (Button) dialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.btn_save);
        dialog_btn_clear = (Button) dialog.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.btn_clear);
        signaturePad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        save(mContent);
                        dialog.dismiss();
                    }
                });
                dialog_btn_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSignature.clear();
                    }
                });
                dialog.show();
            }
        });
    }

    //Signature Method
    public class signature extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private String currentYear() {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String currentYear = yearFormat.format(new Date());
        return currentYear;
    }

    private String currentMonth() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String currentMonth = monthFormat.format(new Date());
        return currentMonth;
    }

    private String currentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/24sh_images/" + currentYear() + "/" + currentMonth());
        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/24sh_images/" + currentYear() + "/" + currentMonth());
            wallpaperDirectory.mkdirs();
            Toast.makeText(this, "directory create. ", Toast.LENGTH_SHORT).show();
        }
        File file = new File(new File(Environment.getExternalStorageDirectory() + "/24sh_images/" + currentYear() + "/" + currentMonth()), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            String URLimage = Environment.getExternalStorageDirectory() + "/24sh_images/" + currentYear() + "/" + currentMonth()+ "/" + fileName;
            addImage(activityPhoto,store_id,URLimage);
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.PNG, 0, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDirectoryAndSaveSign(Bitmap imageToSave, String fileName) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/24sh_images_sign/" + currentYear() + "/" + currentMonth());
        if (!direct.exists()) {
            Toast.makeText(this, "directory create.", Toast.LENGTH_SHORT).show();
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/24sh_images_sign/" + currentYear() + "/" + currentMonth());
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File(Environment.getExternalStorageDirectory() + "/24sh_images_sign/" + currentYear() + "/" + currentMonth()), fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("SAME STORE");
        }
        try {
            String URLsign = Environment.getExternalStorageDirectory() + "/24sh_images_sign/" + currentYear() + "/" + currentMonth() + "/"+ fileName;
            addSign(activity,store_id,URLsign);
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.PNG, 0, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(View mContent) {
        mContent.setDrawingCacheEnabled(true);
        mContent.buildDrawingCache(true);
        savebm = Bitmap.createBitmap(mContent.getDrawingCache());
        String filename = "sign_"+store_id+"_"+currentDate() + ".png";
        createDirectoryAndSaveSign(savebm,filename);
        img_sign.setImageBitmap(savebm);
        mContent.setDrawingCacheEnabled(false);
    }

    public void prepareDataDB(Context context) {
        //OUTBOX DATA
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        String qr1 = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.STORE_ID + " = " + "'" + store_id + "'"
                + " and " + Constants.PRODUCT_TYPE + " = '1'";
        System.out.println(qr1);
        Cursor mcursor = db.rawQuery(qr1, null);
        if (mcursor.moveToFirst()) {
            do {
                String product_name = mcursor.getString(4);
                String product_status = mcursor.getString(6);
                String product_type = mcursor.getString(5); //in or out
                String product_barcode = mcursor.getString(3);
                String product_add = mcursor.getString(7);

                model_inout item = new model_inout();
                item.setItem_name(product_name);
                item.setType("item");
                item.setStatus(product_status);
                item.setInORout(product_type);
                item.setBarcode(product_barcode);
                item.setAdd(product_add);
                inbox.add(item);
            } while (mcursor.moveToNext());
        }
        //INBOX DATA
        String qr2 = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.STORE_ID + " = " + "'" + store_id + "'"
                + " and " + Constants.PRODUCT_TYPE + " = '2'";
        System.out.println(qr2);
        Cursor mcursor2 = db.rawQuery(qr2, null);
        if (mcursor2.moveToFirst()) {
            do {
                String product_name = mcursor2.getString(4);
                String product_status = mcursor2.getString(6);
                String product_type = mcursor2.getString(5); //in or out
                String product_barcode = mcursor2.getString(3);
                String product_add = mcursor2.getString(7);

                model_inout item = new model_inout();
                item.setItem_name(product_name);
                item.setType("item");
                item.setStatus(product_status);
                item.setInORout(product_type);
                item.setBarcode(product_barcode);
                item.setAdd(product_add);
                outbox.add(item);
            } while (mcursor2.moveToNext());
        }

        //set Header
        model_inout item0 = new model_inout();
        item0.setHead_name("ส่งของ " + outbox.size() + " ชิ้น");
        item0.setType("head");

        model_inout item1 = new model_inout();
        item1.setHead_name("รับของ " + inbox.size() + " ชิ้น");
        item1.setType("head");

        //in layout OUT FIRST
        //set OUTBOX
        data.add(item0);
        for (int i = 0; i < outbox.size(); i++) {
            if (outbox.get(i).getInORout().equals("2")) {
                model_inout out = new model_inout();
                out.setItem_name(outbox.get(i).getItem_name());
                out.setType(outbox.get(i).getType());
                out.setStatus(outbox.get(i).getStatus());
                out.setInORout(outbox.get(i).getInORout());
                out.setBarcode(outbox.get(i).getBarcode());
                out.setAdd(outbox.get(i).getAdd());
                if(i%2==0){
                    out.setType_order("onshelf");
                }else {
                    out.setType_order("onbook");
                }
                data.add(out);
            }
        }

        //set INBOX
        data.add(item1);
        for (int i = 0; i < 1; i++) {
            if (inbox.get(i).getInORout().equals("1")) {
                model_inout in = new model_inout();
                in.setItem_name(inbox.get(i).getItem_name());
                in.setType(inbox.get(i).getType());
                in.setStatus(inbox.get(i).getStatus());
                in.setInORout(inbox.get(i).getInORout());
                in.setBarcode(inbox.get(i).getBarcode());
                in.setAdd(inbox.get(i).getAdd());
                if(i%2==0){
                    in.setType_order("onshelf");
                }else {
                    in.setType_order("onbook");
                }
                data.add(in);
            }
        }
    }

    public void preparePhotosDB(Context context){
        String sql = "SELECT count(*) FROM "+ Constants.TABLE_NAME_PIC+" WHERE "+ Constants.PIC_STOREID + " = " +"'"+store_id+"'";
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        System.out.println("count photo : "+count);
        if (count>0){
            String sql1 = "SELECT * FROM "+ Constants.TABLE_NAME_PIC+" WHERE "+ Constants.PIC_STOREID + " = " +"'"+store_id+"'";
            Cursor cursor1 = db.rawQuery(sql1,null);
            if (cursor1.moveToFirst()) {
                do {
                    String url_photo = cursor1.getString(2);
                    model_photo photo = new model_photo();
                    photo.setName(url_photo);
                    photos.add(photo);
                } while (cursor1.moveToNext());
            }
        }
        for (int i = 0; i<photos.size(); i++){
            File imgFile = new  File(photos.get(i).getName());

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                model_photo photoDB = new model_photo();
                photoDB.setBitmap(myBitmap);
                photosDB.add(photoDB);
            }
        }
        System.out.println("photosDB.size() : "+photosDB.size());
    }

    private void addImage(Context context, String storeID, String image){
        Toast.makeText(getApplicationContext(),"Save Picture",Toast.LENGTH_SHORT).show();
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.PIC_STOREID,storeID);
        values.put(Constants.PIC_IMAGE,image);
        db.insertOrThrow(Constants.TABLE_NAME_PIC, null, values);
    }

    private void addSign(Context context, String storeID, String sign){
        Toast.makeText(getApplicationContext(),"Save Signature",Toast.LENGTH_SHORT).show();
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.SIGN_STOREID,storeID);
        values.put(Constants.SIGN_IMAGE,sign);
        db.insertOrThrow(Constants.TABLE_NAME_SIGN, null, values);
    }

    private void addComment(Context context, String storeID, String comment){
//        Toast.makeText(getApplicationContext(),"Save Comment",Toast.LENGTH_SHORT).show();
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COM_STOREID,storeID);
        values.put(Constants.COM_TEXT,comment);
        db.insertOrThrow(Constants.TABLE_NAME_COMMENT, null, values);
    }

    private void setShowView(){
        layout_head_onBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_onbook.getVisibility()==View.GONE){
//                    layout_head_onBook.setBackgroundColor(getResources().getColor(R.color.colorOnBook,null));
                    image_type.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    layout_onbook.setVisibility(View.VISIBLE);
                }else if(layout_onbook.getVisibility()==View.VISIBLE){
//                    layout_head_onBook.setBackgroundColor(getResources().getColor(R.color.colorTextTitle,null));
                    image_type.setImageResource(R.drawable.ic_arrow_down);
                    layout_onbook.setVisibility(View.GONE);
                }
            }
        });

        layout_head_onShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_onshelf.getVisibility()==View.GONE){
                    image_type_onShelf.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    layout_onshelf.setVisibility(View.VISIBLE);
                }else if(layout_onshelf.getVisibility()==View.VISIBLE){
                    image_type_onShelf.setImageResource(R.drawable.ic_arrow_down);
                    layout_onshelf.setVisibility(View.GONE);
                }
            }
        });
    }
}
