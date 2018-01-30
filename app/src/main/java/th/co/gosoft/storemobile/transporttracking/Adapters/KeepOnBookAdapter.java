package th.co.gosoft.storemobile.transporttracking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import th.co.gosoft.storemobile.transporttracking.Models.model_keep_on_book;
import th.co.gosoft.storemobile.transporttracking.R;
import th.co.gosoft.storemobile.transporttracking.ViewGroup.KeepOnBookViewGroup;

/**
 * Created by print on 1/29/2018.
 */

public class KeepOnBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<model_keep_on_book> model;
    Context context;

    public KeepOnBookAdapter(ArrayList<model_keep_on_book> model, Context context) {
        this.model = model;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_keep_onbook,parent,false);
        KeepOnBookViewGroup viewGroup = new KeepOnBookViewGroup(view);

        return viewGroup;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        KeepOnBookViewGroup view = (KeepOnBookViewGroup)holder;
        model_keep_on_book model_ = (model_keep_on_book)model.get(position);
        view.box_id.setText(model_.getBoxid());
        view.num_box_id.setText(model_.getNum());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
