package th.co.gosoft.storemobile.transporttracking.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 1/25/2018.
 */

public class SentOnBookViewGroup extends RecyclerView.ViewHolder {
    public TextView txt_boxID_sent;
    public TextView txt_num_sent;
    public EditText num;
    public ImageView image_edit_num;
    public SentOnBookViewGroup(View itemView) {
        super(itemView);
        txt_boxID_sent = (TextView)itemView.findViewById(R.id.box_id_sent_on_book);
        txt_num_sent = (TextView)itemView.findViewById(R.id.num_sent_on_book);
        num = (EditText)itemView.findViewById(R.id.edit_sent_on_book);
        image_edit_num = (ImageView)itemView.findViewById(R.id.image_sent_on_book);

    }
}
