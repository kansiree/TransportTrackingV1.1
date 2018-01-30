package th.co.gosoft.storemobile.transporttracking.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 12/25/2017.
 */

public class INOUT_View_GroupItem extends RecyclerView.ViewHolder {
    public TextView Item;
    public LinearLayout cardItem;
    public LinearLayout cardItemBG;
    public ImageView ic_right;
    public ImageView sent_image;
    public LinearLayout bt_sent;

    public INOUT_View_GroupItem(View itemView) {
        super(itemView);
        Item = (TextView) itemView.findViewById(R.id.name);
        cardItem = (LinearLayout) itemView.findViewById(R.id.card_item);
        cardItemBG = (LinearLayout) itemView.findViewById(R.id.card_item_bg);
//        ic_right = (ImageView) itemView.findViewById(R.id.ic_right);
        sent_image = (ImageView) itemView.findViewById(R.id.btn_sent);
        bt_sent = (LinearLayout) itemView.findViewById(R.id.layout_bt_Scan);
    }
}

