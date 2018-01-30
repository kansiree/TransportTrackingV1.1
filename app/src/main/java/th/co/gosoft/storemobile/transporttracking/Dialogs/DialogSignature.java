package th.co.gosoft.storemobile.transporttracking.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import th.co.gosoft.storemobile.transporttracking.R;
import th.co.gosoft.storemobile.transporttracking.Signature;

/**
 * Created by print on 1/26/2018.
 */

public class DialogSignature extends Dialog {
    Context context;
    Button clear,save;
    LinearLayout dialog_signature;
    Signature signature;
    Bitmap bitmap;
    ImageView imageView;
    EventClickItem clickItem;
    public DialogSignature(@NonNull Context context,EventClickItem item) {
        super(context);
        this.context = context;
        clickItem = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_signature);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view();
        signature = new Signature(context,null);
        dialog_signature.addView(signature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(signature);
                clickItem.confirm(bitmap);
                dismiss();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signature.clear();
                clickItem.cancel();
            }
        });

    }

    private void view(){
        clear = (Button) findViewById(R.id.btn_clear);
        save = (Button) findViewById(R.id.btn_save);
        dialog_signature = (LinearLayout) findViewById(R.id.dialog_signature_pad);
    }

    private void save(View mContent) {
        mContent.setDrawingCacheEnabled(true);
        mContent.buildDrawingCache(true);
        bitmap = Bitmap.createBitmap(mContent.getDrawingCache());
        String filename = "sign_storeID"+"_Date"+".jpg";
//        createDirectoryAndSaveSign(bitmap,filename);
        mContent.setDrawingCacheEnabled(false);
    }


}
