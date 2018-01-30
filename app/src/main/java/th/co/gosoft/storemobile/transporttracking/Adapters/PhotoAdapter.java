package th.co.gosoft.storemobile.transporttracking.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.storemobile.transporttracking.Models.model_photo;
import th.co.gosoft.storemobile.transporttracking.R;
import th.co.gosoft.storemobile.transporttracking.ViewGroup.AddViewGroup;

/**
 * Created by Jubjang on 9/14/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<model_photo> photos = new ArrayList<>();
    Context context;
    ImageView imageView;
    int add = 1;

    public PhotoAdapter (List<model_photo> data,Context context){
        this.photos = data;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_photo,parent,false);

        if(add==0){
            view_Group view_group = new view_Group(view);
            return view_group;
        }
        else {
            AddViewGroup viewGroup = new AddViewGroup(view);
            return viewGroup;
        }
    }
    public class view_Group extends RecyclerView.ViewHolder {
        public view_Group(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_camera);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        System.out.println("photo: "+photos.size());
        if(photos.size()!=1){
            imageView.setImageBitmap(photos.get(position).getBitmap());
        }

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==1){
            add=0;
        }
        else {
            add=1;
        }
        return add;
    }
}
