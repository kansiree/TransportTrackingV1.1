package th.co.gosoft.storemobile.transporttracking.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by print on 12/26/2017.
 */

public class View_in_onShelf extends RecyclerView.ViewHolder {
    public TextView txt_ToteID;
    public EditText ed_onShelf;
    public Spinner spinner;
    public View_in_onShelf(View itemView) {
        super(itemView);
        txt_ToteID = (TextView)itemView.findViewById(R.id.txt_in_onshelf);
        ed_onShelf = (EditText)itemView.findViewById(R.id.ed_in_onshelf);
//        spinner = (Spinner)itemView.findViewById(R.id.sp_in_onShelf);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(itemView.getContext(),R.layout.support_simple_spinner_dropdown_item,itemView.getContext().getResources().getStringArray(R.array.unit));
//        spinner.setAdapter(adapter);
    }
}
