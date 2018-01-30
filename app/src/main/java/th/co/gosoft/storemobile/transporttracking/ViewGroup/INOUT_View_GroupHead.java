package th.co.gosoft.storemobile.transporttracking.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 12/25/2017.
 */

public class INOUT_View_GroupHead extends RecyclerView.ViewHolder {
    public TextView  Header;
    public LinearLayout cardHead;
    public Button add_order;

    public INOUT_View_GroupHead(View itemView) {
        super(itemView);
        Header = (TextView) itemView.findViewById(R.id.name);
        cardHead = (LinearLayout) itemView.findViewById(R.id.card_head);
        add_order = (Button) itemView.findViewById(R.id.add_list);
    }
}
