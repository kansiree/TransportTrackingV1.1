package th.co.gosoft.storemobile.transporttracking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import th.co.gosoft.storemobile.transporttracking.Models.model_send_on_shelf;
import th.co.gosoft.storemobile.transporttracking.R;
import th.co.gosoft.storemobile.transporttracking.ViewGroup.SentOnShelfViewGroup;

/**
 * Created by print on 1/29/2018.
 */

public class SentOnShelfAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<model_send_on_shelf> model;

    public SentOnShelfAdapter(Context context, ArrayList<model_send_on_shelf> model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_sent_on_shelf,parent,false);
        SentOnShelfViewGroup viewGroup = new SentOnShelfViewGroup(view);
        return viewGroup;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SentOnShelfViewGroup viewGroup = (SentOnShelfViewGroup) holder;
        model_send_on_shelf model_ = model.get(position);
        viewGroup.tote_id.setText(model_.getTote_id());
        viewGroup.num_tote.setText(model_.getNum_sent()+"");
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
