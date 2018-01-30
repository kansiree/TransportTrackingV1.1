package th.co.gosoft.storemobile.transporttracking.Dialogs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import th.co.gosoft.storemobile.transporttracking.Controller.ListDetail;
import th.co.gosoft.storemobile.transporttracking.Controller.DB.DBHelper;
import th.co.gosoft.storemobile.transporttracking.R;
import com.google.zxing.integration.android.IntentIntegrator;

import static th.co.gosoft.storemobile.transporttracking.Controller.ListDetail.count_add;
import static th.co.gosoft.storemobile.transporttracking.Controller.ListDetail.scan_add_result;
import static th.co.gosoft.storemobile.transporttracking.LoginActivity.person;
import static th.co.gosoft.storemobile.transporttracking.Models.Constants.PRODUCT_ADD;
import static th.co.gosoft.storemobile.transporttracking.Models.Constants.PRODUCT_CODE;
import static th.co.gosoft.storemobile.transporttracking.Models.Constants.PRODUCT_NAME;
import static th.co.gosoft.storemobile.transporttracking.Models.Constants.PRODUCT_STATUS;
import static th.co.gosoft.storemobile.transporttracking.Models.Constants.PRODUCT_TYPE;
import static th.co.gosoft.storemobile.transporttracking.Models.Constants.STORE_ID;
import static th.co.gosoft.storemobile.transporttracking.Models.Constants.TABLE_NAME;
import static th.co.gosoft.storemobile.transporttracking.Models.Constants.WORK_ID;

/**
 * Created by Jubjang on 9/28/2017.
 */

public class DialogAdd extends DialogFragment {
    Button btn_barcode,btn_confirm,btn_cancel;
    EditText edt_barcode,edt_name;
    String barcode,product_name;
    Activity activity;
    DBHelper helper;
    SQLiteDatabase db;
    String storeID;

    public DialogAdd(Activity activity,String storeID) {
        this.activity = activity;
        this.storeID = storeID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add,container,false);
        btn_barcode = v.findViewById(R.id.btn_barcode_add);
        btn_confirm = v.findViewById(R.id.btn_confirm);
        edt_barcode = v.findViewById(R.id.edt_barcode_add);
        edt_name = v.findViewById(R.id.edt_product_add_name);
        btn_cancel = v.findViewById(R.id.btn_cancel);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        edt_barcode.setText(scan_add_result);
        edt_barcode.setSelection(edt_barcode.getText().length());
        System.out.println("ADD RESU<E");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        btn_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
                integrator.setOrientationLocked(false);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcode = edt_barcode.getText().toString();
                product_name = edt_name.getText().toString();
                if (barcode.equals("")||product_name.equals("")){
                    Toast.makeText(activity,"Please input barcode and product name.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity,"Add item success.",Toast.LENGTH_SHORT).show();
                    addItem(activity,person.getWorkID(),
                            storeID,
                            barcode,
                            product_name,
                            "1",
                            "success",
                            "1");
                    dismiss();
                    ListDetail.callDetail.onclick();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count_add = 0;
                dismiss();
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("Request Code Add : "+requestCode);
//        //scan barcode result
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
//        if(result!=null){
//            if(result.getContents()!=null){
//                Toast.makeText(activity,result.getContents(),Toast.LENGTH_SHORT).show();
//                System.out.println("Add list Result : "+result.getContents());
//                edt_name.setText(result.getContents());
//            }else {
//                Toast.makeText(activity,"You cancelled the scanning",Toast.LENGTH_SHORT).show();
//            }
//        }else {
//            super.onActivityResult(requestCode,resultCode,data);
//        }
//    }

    private void addItem(Context context, String workID, String product_store_id, String product_code,
                         String product_name, String product_type, String product_status, String product_add){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORK_ID,workID);
        values.put(STORE_ID,product_store_id);
        values.put(PRODUCT_CODE,product_code);
        values.put(PRODUCT_NAME,product_name);
        values.put(PRODUCT_TYPE,product_type);
        values.put(PRODUCT_STATUS,product_status);
        values.put(PRODUCT_ADD, product_add);
        db.insertOrThrow(TABLE_NAME, null, values);
    }
}
