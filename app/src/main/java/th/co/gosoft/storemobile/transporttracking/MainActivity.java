package th.co.gosoft.storemobile.transporttracking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import th.co.gosoft.storemobile.transporttracking.Adapters.HamburgerAdapter;
import th.co.gosoft.storemobile.transporttracking.Adapters.ViewPageAdapter;
import th.co.gosoft.storemobile.transporttracking.Controller.ExpandableList;
import th.co.gosoft.storemobile.transporttracking.Controller.MapListener;
import th.co.gosoft.storemobile.transporttracking.Dialogs.DialogLoadData;
import th.co.gosoft.storemobile.transporttracking.Dialogs.onClickfunction;
import th.co.gosoft.storemobile.transporttracking.Models.model_person;
import th.co.gosoft.storemobile.transporttracking.Utility.SQLliteManager;
import th.co.gosoft.storemobile.transporttracking.Utility.SessionManager;

import static th.co.gosoft.storemobile.transporttracking.Models.Constants.PERSON_WORKID;
import static th.co.gosoft.storemobile.transporttracking.Models.Constants.TABLE_NAME_PERSON;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Activity activity;
    TabLayout tabLayout;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int CAMERA_REQUEST = 1888;
    public static onClickfunction event;
    Button load_data;
    SQLliteManager sqlManager;
    ViewPager viewPager;
    Context ctx;
    LinearLayout layout_map;

    private void setView(){
        layout_map = (LinearLayout)findViewById(R.id.ham_layout_map);
    }

    private View setTabLayout(String text,int count1,int color){
        View view = LayoutInflater.from(this).inflate(R.layout.custom_tab,null);
        TextView textView = (TextView)view.findViewById(R.id.process);
        TextView count = (TextView)view.findViewById(R.id.count_process);
        textView.setText(text);
        textView.setTextColor(color);
        count.setText("("+count1+")");
        count.setTextColor(color);
        return view;
    }

    private void ChangTab(TabLayout.Tab tab,int color){
        View view = tab.getCustomView();
        TextView textView = (TextView)view.findViewById(R.id.process);
        TextView count = (TextView)view.findViewById(R.id.count_process);
        textView.setTextColor(color);
        count.setTextColor(color);
    }

    private void manageTab(final int ColorRed, final int ColorBlack){
        viewPager = (ViewPager)findViewById(R.id.ViewPage);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setCustomView(setTabLayout("รอดำเนินการ",7,ColorRed));
        tabLayout.getTabAt(1).setCustomView(setTabLayout("ดำเนินการเแล้ว",0,ColorBlack));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        ChangTab(tab,ColorRed);
                        break;
                    case 1:
                        ChangTab(tab,ColorRed);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        ChangTab(tab,ColorBlack);
                        break;
                    case 1:
                        ChangTab(tab,ColorBlack);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        ctx = this;
        final int ColorRed = getResources().getColor(R.color.colorDarkRed,null);
        final int ColorBlack = getResources().getColor(R.color.colorBlack,null);
        sqlManager = new SQLliteManager(ctx);
        Callfuntion();
        String workID = sqlManager.getSingleRowLocalDB(PERSON_WORKID, TABLE_NAME_PERSON);
        setView();
        layout_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.layout_page,new MapListener());
                transaction.addToBackStack("map");
                transaction.commit();
            }
        });
        //set name in toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(getString(R.string.prefix_title)+" "+workID);
//        setSupportActionBar(toolbar);
        manageTab(ColorRed,ColorBlack);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SessionManager sessionManager = new SessionManager(ctx);
        model_person objPerson = new model_person();
        objPerson = sessionManager.getPersonFromPref();

        //set IDCard
        RecyclerView recyclerView = findViewById(R.id.list_IDCard);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<String> list = new ArrayList<>();
        list.add("085");
        list.add("546");
        HamburgerAdapter hamburgerAdapter = new HamburgerAdapter(list,getApplicationContext());
        recyclerView.setAdapter(hamburgerAdapter);

        load_data = findViewById(R.id.ham_btn_load_data);
        load_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLoadData dialogLoadData = new DialogLoadData(MainActivity.this);
                dialogLoadData.show();
            }
        });
        //set Menu Text in Nav Bar
//        Menu menu = navigationView.getMenu();
//        MenuItem nav_phone = menu.findItem(R.id.nav_phone);
//        nav_phone.setTitle(objPerson.getPersonPhone());
//        MenuItem nav_car = menu.findItem(R.id.nav_car);
//        nav_car.setTitle(objPerson.getPersonCar());
//
//        //get Time, First Login
//        String clockIn = sqlManager.getSingleRowLocalDB(PERSON_TIME, TABLE_NAME_PERSON);
//        MenuItem nav_time = menu.findItem(R.id.nav_time);
//        nav_time.setTitle(getString(R.string.prefix_clockin)+clockIn);

        //set head Hamburger menu
        TextView tv_personName = (TextView)findViewById(R.id.nav_txt_name);
        TextView tv_personID = (TextView)findViewById(R.id.nav_txt_idName);
        tv_personName.setText(objPerson.getPersonName());
        tv_personID.setText(    objPerson.getPersonID());
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_phone) {
//            // Handle the camera action
//        } else if (id == R.id.nav_car) {
//
//        } else if (id == R.id.nav_time) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public onClickfunction Callfuntion(){
         event = new onClickfunction() {
            @Override
            public void Scan() {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
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

    private void setupViewPager(ViewPager viewPager){
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExpandableList(),"รอดำเนินการ");
        adapter.addFragment(new ExpandableNotProcessing(),"ดำเนินการแล้ว");
        viewPager.setAdapter(adapter);
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
//                    String barcode = result.getContents();
//                    System.out.println("List SCAN : " + result.getContents());
//
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
        //take picture
        if (requestCode == CAMERA_REQUEST) {
//            if (resultCode == Activity.RESULT_OK) {
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                String filename = "images_"+currentDateFormat() + ".png";
//                createDirectoryAndSaveFile(photo, filename);
//                callRecyclerView();
//            } else {
//                System.out.println("can't use camera");
//            }
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
}
