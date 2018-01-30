package th.co.gosoft.storemobile.transporttracking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 1/23/2018.
 */

public class HamburgerAdapter extends RecyclerView.Adapter<HamburgerAdapter.View_Hamburger> {
    ArrayList<String> list;
    Context context;

    public HamburgerAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Hamburger onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_idcard,parent,false);
        View_Hamburger view_hamburger = new View_Hamburger(view);
        return view_hamburger;
    }

    @Override
    public void onBindViewHolder(View_Hamburger holder, int position) {
        holder.text_idCard.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class View_Hamburger extends RecyclerView.ViewHolder {
        public TextView text_idCard;
        public View_Hamburger(View itemView) {
            super(itemView);
            text_idCard = itemView.findViewById(R.id.ham_item_card);
        }
    }
}
