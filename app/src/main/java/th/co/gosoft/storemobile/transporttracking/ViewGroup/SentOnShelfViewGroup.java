package th.co.gosoft.storemobile.transporttracking.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 1/29/2018.
 */

public class SentOnShelfViewGroup extends RecyclerView.ViewHolder {

    public TextView tote_id,num_tote;
    public EditText edit_tote;
    public SentOnShelfViewGroup(View itemView) {
        super(itemView);
        tote_id = itemView.findViewById(R.id.tote_id_sent_on_shelf);
        num_tote = itemView.findViewById(R.id.num_sent_on_shelf);
        edit_tote = itemView.findViewById(R.id.edit_sent_on_shelf);
    }
}
