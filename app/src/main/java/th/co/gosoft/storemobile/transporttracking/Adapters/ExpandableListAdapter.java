package th.co.gosoft.storemobile.transporttracking.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import th.co.gosoft.storemobile.transporttracking.Dialogs.DialogMap;
import th.co.gosoft.storemobile.transporttracking.R;

/**
 * Created by Jubjang on 9/7/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, List<String>> _listDataChild_phone;
    private HashMap<String, List<String>> _listDataChild_in;
    private HashMap<String, List<String>> _listDataChild_out;
    onClickItem item;
    DialogMap alertDialog;
    ImageView status;
    ImageButton smallmap;

    public ExpandableListAdapter(Context context,
                                 List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData,
                                 HashMap<String, List<String>> listChildData_phone,
                                 HashMap<String, List<String>> listChildData_in,
                                 HashMap<String, List<String>> listChildData_out) {

        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._listDataChild_phone = listChildData_phone;
        this._listDataChild_in = listChildData_in;
        this._listDataChild_out = listChildData_out;
    }

    public interface onClickItem{
        void onItemClick(View view, int position, String id);
    }
    public void setItem(ExpandableListAdapter.onClickItem item){this.item = item;}
    public ExpandableListAdapter.onClickItem getItem(){return item;}
    public void  setClickItem(ExpandableListAdapter.onClickItem item){setItem(item);}

    //get Store Title
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    //get Storephone
    public Object getStorePhone(int groupPosition, int childPosititon) {
        return this._listDataChild_phone.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    //get StoreIn
    public Object getStoreIn(int groupPosition, int childPosititon) {
        return this._listDataChild_in.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    //get StoreOut
    public Object getStoreOut(int groupPosition, int childPosititon) {
        return this._listDataChild_out.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //send store position to dialog add
        final String childText = (String) getChild(groupPosition, childPosition); //storeID
        final String storePhone = (String) getStorePhone(groupPosition,childPosition);
        final String storeIn = (String) getStoreIn(groupPosition,childPosition);
        final String storeOut = (String) getStoreOut(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(th.co.gosoft.storemobile.transporttracking.R.layout.list_item, null);
        }
        LinearLayout background = (LinearLayout) convertView.findViewById(R.id.list_bg);
//        status = (ImageView) convertView.findViewById(R.id.oval_status);
        smallmap = (ImageButton) convertView.findViewById(R.id.smallmap);

        if(isLastChild==true){
            background.setBackgroundResource(th.co.gosoft.storemobile.transporttracking.R.drawable.bg_list_b);
        }else {
            background.setBackgroundResource(R.drawable.bg_list_a);
        }
        switch (groupPosition){
            case 0:
//                setColorChild(R.drawable.grey_button_bg,R.drawable.grey_button_bg,R.drawable.ic_mark_grey,R.drawable.shape_oval_grey);
                break;
        }
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem().onItemClick(view,childPosition,childText);
            }
        });

        smallmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity fragmentActivity = (FragmentActivity)(_context);
                FragmentManager fm = fragmentActivity.getSupportFragmentManager();
                alertDialog = new DialogMap(String.valueOf(childText));
                alertDialog.show(fm, "fragment_alert");
            }
        });

        TextView txtListChild = (TextView) convertView.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.subtitle);
        txtListChild.setText(childText);

        TextView txtListStorePhone = (TextView) convertView.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.phone);
        txtListStorePhone.setText(storePhone);

        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(th.co.gosoft.storemobile.transporttracking.R.layout.list_group, null);
        }
        LinearLayout background = (LinearLayout) convertView.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.header_bg);
        TextView lblListHeader = (TextView) convertView.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.header);
        if(isExpanded==true){
            background.setBackgroundResource(th.co.gosoft.storemobile.transporttracking.R.drawable.bg_header_b);
        }else {
            background.setBackgroundResource(th.co.gosoft.storemobile.transporttracking.R.drawable.bg_header_a);
        }

       if(groupPosition==0){
            lblListHeader.setTextColor(_context.getResources().getColor(th.co.gosoft.storemobile.transporttracking.R.color.colorGreyTitleList));
        }
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void setColorChild(int colorIN,int colorOUT,int colorMap,int colorstatus ){
        smallmap.setBackgroundResource(colorMap);
//        status.setBackgroundResource(colorstatus);
    }
}
