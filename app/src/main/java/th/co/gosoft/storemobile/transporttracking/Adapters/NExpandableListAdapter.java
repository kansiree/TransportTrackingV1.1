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
 * Created by print on 12/22/2017.
 */

public class NExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, List<String>> _listDataChild_phone;
    private HashMap<String, List<String>> _listDataChild_in;
    private HashMap<String, List<String>> _listDataChild_out;
    NExpandableListAdapter.onClickItem item;
    DialogMap alertDialog;
    ImageView status;
    ImageButton smallmap;

    public NExpandableListAdapter(Context _context, List<String> _listDataHeader, HashMap<String, List<String>> _listDataChild, HashMap<String, List<String>> _listDataChild_phone, HashMap<String, List<String>> _listDataChild_in, HashMap<String, List<String>> _listDataChild_out) {
        this._context = _context;
        this._listDataHeader = _listDataHeader;
        this._listDataChild = _listDataChild;
        this._listDataChild_phone = _listDataChild_phone;
        this._listDataChild_in = _listDataChild_in;
        this._listDataChild_out = _listDataChild_out;
    }

    public interface onClickItem{
        void onItemClick(View view,int position,String id);
    }

    public void setItem(NExpandableListAdapter.onClickItem item){this.item=item;}
    public  NExpandableListAdapter.onClickItem getItem(){return item;}
    public void setClickItem(NExpandableListAdapter.onClickItem item){setItem(item);}

    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this._listDataChild.get(this._listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return _listDataHeader.get(i);
    }

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
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(th.co.gosoft.storemobile.transporttracking.R.layout.list_group, null);
        }
        LinearLayout background = (LinearLayout) view.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.header_bg);
        TextView lblListHeader = (TextView) view.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.header);
        if(isExpanded==true){
            background.setBackgroundResource(th.co.gosoft.storemobile.transporttracking.R.drawable.bg_header_b);
        }else {
            background.setBackgroundResource(th.co.gosoft.storemobile.transporttracking.R.drawable.bg_header_a);
        }

        if(groupPosition==0){
//            lblListHeader.setTextColor(_context.getResources().getColor(th.co.gosoft.storemobile.transporttracking.R.color.colorPrimary));
        }
        else if (groupPosition==1){
//            lblListHeader.setTextColor(_context.getResources().getColor(th.co.gosoft.storemobile.transporttracking.R.color.colorRed));
        }

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {

        //send store position to dialog add
        final String childText = (String) getChild(groupPosition, childPosition); //storeID
        final String storePhone = (String) getStorePhone(groupPosition,childPosition);
        final String storeIn = (String) getStoreIn(groupPosition,childPosition);
        final String storeOut = (String) getStoreOut(groupPosition,childPosition);

        if(view==null){
            LayoutInflater layoutInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item,null);
        }
        LinearLayout background = (LinearLayout) view.findViewById(R.id.list_bg);
//        status = (ImageView)view.findViewById(R.id.oval_status);
        smallmap = (ImageButton) view.findViewById(R.id.smallmap);

        if(isLastChild==true){
            background.setBackgroundResource(R.drawable.bg_list_b);
        }else {
            background.setBackgroundResource(R.drawable.bg_list_a);

        }

        switch (groupPosition){
            case 0:
                setColorChild(R.drawable.blue_button_bg,R.drawable.blue_button_bg,R.drawable.ic_mark_green,R.drawable.shape_oval);
                break;
//            case 0:
//                setColorChild(R.drawable.grey_button_bg,R.drawable.grey_button_bg,R.drawable.ic_mark_grey,R.drawable.shape_oval_grey);
//                break;
//            case 1:
//                setColorChild(R.drawable.yellow_button_bg,R.drawable.yellow_button_bg,R.drawable.ic_mark_yellow,R.drawable.shape_oval_yellow);
//                break;
            case 1:
                setColorChild(R.drawable.red_button_bg,R.drawable.red_button_bg,R.drawable.ic_mark_red,R.drawable.shape_oval_red);
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

        TextView txtListChild = (TextView) view.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.subtitle);
        txtListChild.setText(childText);

        TextView txtListStorePhone = (TextView) view.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.phone);
        txtListStorePhone.setText(storePhone);

        return view;
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
