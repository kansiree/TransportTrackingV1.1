package th.co.gosoft.storemobile.transporttracking.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 1/29/2018.
 */

public class AddViewGroup extends RecyclerView.ViewHolder{
    public ImageView btn_add;

    public AddViewGroup(View itemView) {
        super(itemView);
        btn_add = (ImageView)itemView.findViewById(R.id.img_camera_add);
    }
}
