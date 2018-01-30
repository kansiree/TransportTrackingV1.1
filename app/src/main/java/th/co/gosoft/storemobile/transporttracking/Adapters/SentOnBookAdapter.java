package th.co.gosoft.storemobile.transporttracking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import th.co.gosoft.storemobile.transporttracking.Models.model_sent_on_book;
import th.co.gosoft.storemobile.transporttracking.R;
import th.co.gosoft.storemobile.transporttracking.ViewGroup.SentOnBookViewGroup;

/**
 * Created by print on 1/25/2018.
 */

public class SentOnBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<model_sent_on_book> inouts;
    OnClickItem item;

    public SentOnBookAdapter(Context context, ArrayList<model_sent_on_book> inouts) {
        this.context = context;
        this.inouts = inouts;
    }

    public interface OnClickItem{
        void onItemClick(View view,int position,String ID);
    }

    public OnClickItem getItem() {
        return item;
    }

    public void setItem(OnClickItem item) {
        this.item = item;
    }

    public void setClickItem(SentOnBookAdapter.OnClickItem item){setItem(item);}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewGroup = LayoutInflater.from(context).inflate(R.layout.card_item_sent_on_book,parent,false);
        SentOnBookViewGroup group = new SentOnBookViewGroup(viewGroup);
        return group;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SentOnBookViewGroup viewGroup = (SentOnBookViewGroup) holder;
        final model_sent_on_book model = inouts.get(position);
        viewGroup.txt_boxID_sent.setText(model.getBoxID());
        viewGroup.txt_num_sent.setText(model.getNum());
        viewGroup.num.setText(model.getNum_sent()+"");
        viewGroup.image_edit_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem().onItemClick(view,position,model.getBoxID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return inouts.size();
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }
}
