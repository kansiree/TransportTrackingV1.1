package th.co.gosoft.storemobile.transporttracking.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import th.co.gosoft.storemobile.transporttracking.List_detail_;
import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 12/27/2017.
 */

public class DialogScan extends Dialog {

    ImageButton btn_close,btn_scan;
    EditText ed_barcode;
    Button submit;
    EventClickItem clickItem;
    public DialogScan(@NonNull Context context,EventClickItem item) {
        super(context);
        clickItem = item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_scan_order);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(clickItem==null){
            clickItem = new EventClickItem() {
                @Override
                public void confirm() {

                }

                @Override
                public void cancel() {

                }

                @Override
                public void confirm(Bitmap bitmap) {

                }
            };
        }
        btn_close = findViewById(R.id.btn_close);
        btn_scan = findViewById(R.id.image_bt_barcode);
        ed_barcode = findViewById(R.id.ed_sent);
        submit = findViewById(R.id.submit);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new List_detail_().event.Scan();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.confirm();
                dismiss();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.cancel();
                dismiss();
            }
        });
    }
}
