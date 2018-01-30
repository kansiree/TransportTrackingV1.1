package th.co.gosoft.storemobile.transporttracking.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 1/5/2018.
 */

public class CustomDropdownAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context context ;
    private ArrayList<String> list;

    public CustomDropdownAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(context);
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(18);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setText(list.get(i));
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setBackgroundResource(R.drawable.bg_crop_gray);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(2,2,2,2);
        textView.setLayoutParams(params);
        return textView;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(16);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
        textView.setText(list.get(i));
        textView.setTextColor(Color.parseColor("#000000"));

        return textView;
    }

}
