package th.co.gosoft.storemobile.transporttracking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;

import th.co.gosoft.storemobile.transporttracking.Adapters.CustomDropdownAdapter;
import th.co.gosoft.storemobile.transporttracking.Adapters.KeepOnBookAdapter;
import th.co.gosoft.storemobile.transporttracking.Adapters.PhotoAdapter;
import th.co.gosoft.storemobile.transporttracking.Adapters.SentOnBookAdapter;
import th.co.gosoft.storemobile.transporttracking.Adapters.SentOnShelfAdapter;
import th.co.gosoft.storemobile.transporttracking.Dialogs.DialogScan;
import th.co.gosoft.storemobile.transporttracking.Dialogs.DialogSignature;
import th.co.gosoft.storemobile.transporttracking.Dialogs.EventClickItem;
import th.co.gosoft.storemobile.transporttracking.Dialogs.onClickfunction;
import th.co.gosoft.storemobile.transporttracking.Models.model_keep_on_book;
import th.co.gosoft.storemobile.transporttracking.Models.model_keep_on_shelf;
import th.co.gosoft.storemobile.transporttracking.Models.model_photo;
import th.co.gosoft.storemobile.transporttracking.Models.model_send_on_shelf;
import th.co.gosoft.storemobile.transporttracking.Models.model_sent_on_book;

public class List_detail_ extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView list_sent_on_book,list_keep_on_book,list_photo_on_book,list_photo_ons_shelf,list_sent_on_shelf,list_keep_on_shelf;
    private ArrayList<model_sent_on_book> list_inouts;
    private ArrayList<model_keep_on_book> list_keep;
    private ArrayList<model_send_on_shelf> list_send_shelf;
    private ArrayList<model_keep_on_shelf> list_keep_shelf;

    private ArrayList<model_photo> model_photo_on_book,model_photo_on_shelf;
    Spinner spinner_onBook,spinner_onShelf;

    LinearLayout signature_pad;
    ImageView img_signature;
    public static onClickfunction event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail_);
        view();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("รหัสร้าน");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack,null));
        setSupportActionBar(toolbar);
        Callfuntion();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onBook(getApplicationContext());
        onShelf(getApplicationContext());

        signature_pad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSignature dialogSignature = new DialogSignature(List_detail_.this, new EventClickItem() {
                    @Override
                    public void confirm() {


                    }

                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void confirm(Bitmap bitmap) {
                        img_signature.setImageBitmap(bitmap);
                    }
                });
                dialogSignature.show();
            }
        });

    }

    private void onShelf(Context context){
        ArrayList list =new ArrayList<String>(Arrays.asList( getResources().getStringArray(R.array.note)));
        CustomDropdownAdapter cs_adapter = new CustomDropdownAdapter(List_detail_.this,list);
        spinner_onShelf.setAdapter(cs_adapter);

        model_photo_on_shelf = new ArrayList<>();
        model_photo photo = new model_photo();
        model_photo_on_shelf.add(photo);
        RecyclerView.LayoutManager manager = new GridLayoutManager(context,4);
        list_photo_ons_shelf.setLayoutManager(manager);
        PhotoAdapter photoAdapter = new PhotoAdapter(model_photo_on_shelf,context);
        list_photo_ons_shelf.setAdapter(photoAdapter);

        list_keep_shelf = new ArrayList<>();
        list_send_shelf = new ArrayList<>();

        RecyclerView.LayoutManager send_shelf_Manager = new LinearLayoutManager(context);
        list_sent_on_shelf.setLayoutManager(send_shelf_Manager);
        model_send_on_shelf model = new model_send_on_shelf();
        model.setTote_id("534");
        model.setNum_sent(5);
        list_send_shelf.add(model);
        list_send_shelf.add(model);
        SentOnShelfAdapter adapter = new SentOnShelfAdapter(context,list_send_shelf);
        list_sent_on_shelf.setAdapter(adapter);

    }

    private void onBook(Context context){
        list_inouts = new ArrayList<>();
        model_sent_on_book model = new model_sent_on_book();
        model.setBoxID("123456789");
        model.setNum("6");
        model.setNum_sent(0);
        model_sent_on_book model1 = new model_sent_on_book();
        model.setBoxID("123456789");
        model.setNum("6");
        model.setNum_sent(0);
        list_inouts.add(model);
        list_inouts.add(model);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        list_sent_on_book.setLayoutManager(manager);
        SentOnBookAdapter adapter = new SentOnBookAdapter(context,list_inouts);
        adapter.setItem(new SentOnBookAdapter.OnClickItem() {
            @Override
            public void onItemClick(View view, int position, String ID) {
                DialogScan dialogScan = new DialogScan(List_detail_.this,null);
                dialogScan.show();
            }
        });
        list_sent_on_book.setAdapter(adapter);
        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(context);
        list_keep = new ArrayList<>();
        model_keep_on_book keepOnBook = new model_keep_on_book();
        keepOnBook.setBoxid("7978");
        keepOnBook.setNum("6");
        list_keep.add(keepOnBook);
        list_keep.add(keepOnBook);
        KeepOnBookAdapter bookAdapter = new KeepOnBookAdapter(list_keep,context);
        list_keep_on_book.setLayoutManager(manager1);
        list_keep_on_book.setAdapter(bookAdapter);

        ArrayList list =new ArrayList<String>(Arrays.asList( getResources().getStringArray(R.array.note)));
        CustomDropdownAdapter cs_adapter = new CustomDropdownAdapter(List_detail_.this,list);
        spinner_onBook.setAdapter(cs_adapter);

        RecyclerView.LayoutManager photomanager = new GridLayoutManager(context,4);
        list_photo_on_book.setLayoutManager(photomanager);
        model_photo_on_book = new ArrayList<>();
        model_photo photo = new model_photo();
        model_photo_on_book.add(photo);
        PhotoAdapter photoAdapter = new PhotoAdapter(model_photo_on_book,context);
        list_photo_ons_shelf.setAdapter(photoAdapter);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_detail_, menu);
        return true;
    }

    private void view(){
        list_sent_on_book = (RecyclerView)findViewById(R.id.list_sent_on_book);
        signature_pad = (LinearLayout)findViewById(R.id.layout_signature_pad);
        img_signature = (ImageView)findViewById(R.id.img_signature);
        list_keep_on_book = (RecyclerView)findViewById(R.id.list_keep_on_book);
        spinner_onBook = (Spinner)findViewById(R.id.dropdown_on_book);
        spinner_onShelf = (Spinner)findViewById(R.id.dropdown_on_shelf);
        list_photo_on_book = (RecyclerView)findViewById(R.id.list_photo_on_book);
        list_photo_ons_shelf = (RecyclerView)findViewById(R.id.list_photo_on_shelf);
        list_sent_on_shelf = (RecyclerView)findViewById(R.id.list_sent_on_shelf);
//        list_keep_on_shelf = (RecyclerView)findViewById(R.id.list_keep_on_shelf);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public onClickfunction Callfuntion(){
        event = new onClickfunction() {
            @Override
            public void Scan() {
                IntentIntegrator integrator = new IntentIntegrator(List_detail_.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
                integrator.setOrientationLocked(false);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        };
        return event;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //sacn barcode result
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        System.out.println("Request Code : " + requestCode);
        if (result != null) {
            System.out.println("result != null");
//            if (count_add == 1) {
//                count_add=0;
//                scan_add_result = result.getContents();
//                System.out.println("scan_add_result : "+scan_add_result);
//            } else {
            if (result.getContents() != null) {
                    String barcode = result.getContents();
                    System.out.println("List SCAN : " + result.getContents());

//                    String qr = "SELECT count(" + Constants.PRODUCT_CODE + ") FROM " + Constants.TABLE_NAME + " WHERE " + Constants.STORE_ID + " = '" + store_id + "'" + " and " + Constants.PRODUCT_CODE + " = '" + barcode + "'";
//                    helper = new DBHelper(activity);
//                    db = helper.getWritableDatabase();
//                    Cursor cursor1 = db.rawQuery(qr, null);
//                    cursor1.moveToFirst();
//                    itemInOutSize = cursor1.getInt(0);
//                    if (itemInOutSize != 0) {
//                        ContentValues values = new ContentValues();
//                        values.put(Constants.PRODUCT_STATUS, "success");
//                        db.update(Constants.TABLE_NAME, values, Constants.STORE_ID + " = '" + store_id + "'" + " and " + Constants.PRODUCT_CODE + " = '" + barcode + "'", null);
//                        callRecyclerView();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Don't have item", Toast.LENGTH_SHORT).show();
//                    }
            }
        }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }
}
