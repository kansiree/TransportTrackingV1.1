package th.co.gosoft.storemobile.transporttracking.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 1/29/2018.
 */


public class KeepOnBookViewGroup extends RecyclerView.ViewHolder {
    public TextView box_id;
    public TextView num_box_id;

    public KeepOnBookViewGroup(View itemView) {
        super(itemView);
        box_id = itemView.findViewById(R.id.txt_keep_box_id);
        num_box_id = itemView.findViewById(R.id.txt_keep_num_box_id);
    }
}
