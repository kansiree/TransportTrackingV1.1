package th.co.gosoft.storemobile.transporttracking.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import th.co.gosoft.storemobile.transporttracking.MainActivity;
import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 1/23/2018.
 */

public class DialogLoadData extends Dialog{
    public Button confirm;
    public ImageView btn_Scan;
    public DialogLoadData(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load_data);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btn_Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainActivity().event.Scan();
            }
        });
    }

    private void view(){
        confirm = (Button)findViewById(R.id.btn_load_data_confirm);
        btn_Scan = (ImageView)findViewById(R.id.btn_scan);
    }
}
