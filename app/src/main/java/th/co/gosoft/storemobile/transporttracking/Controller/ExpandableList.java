package th.co.gosoft.storemobile.transporttracking.Controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import th.co.gosoft.storemobile.transporttracking.Adapters.ExpandableListAdapter;
import th.co.gosoft.storemobile.transporttracking.List_detail_;
import th.co.gosoft.storemobile.transporttracking.Models.Constants;
import th.co.gosoft.storemobile.transporttracking.Utility.DataExpanManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpandableList extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataStorePhone;
    HashMap<String, List<String>> listDataStoreIn;
    HashMap<String, List<String>> listDataStoreOut;

    String person_work_id;
    String person_id;
    public ExpandableList() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        callExpandableList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(th.co.gosoft.storemobile.transporttracking.R.layout.fragment_expandable, container, false);
        expListView = (ExpandableListView) v.findViewById(th.co.gosoft.storemobile.transporttracking.R.id.lvExp);
        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Cursor cursor = sqLliteManager.getDataAll(Constants.TABLE_NAME_PERSON);
//        if (cursor.moveToFirst()) {
//            do {
//                person_id = cursor.getString(0);
//                person_work_id = cursor.getString(1);
//
//            } while (cursor.moveToNext());
//        }
        callExpandableList();
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                return false;
            }
        });

    }
    public void callExpandableList(){
        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild , listDataStorePhone, listDataStoreIn, listDataStoreOut);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0);

                listAdapter.setClickItem(new ExpandableListAdapter.onClickItem() {
                    @Override
                    //id = position in expandablelist ----- it has to be storeID
                    public void onItemClick(View view, int position, String id) {

//                Intent intent = new Intent(getContext(),ListDetail.class);
                        Intent intent = new Intent(getContext(),List_detail_.class);
                intent.putExtra("STORE_ID", String.valueOf(id));
                startActivity(intent);
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataStorePhone = new HashMap<>();
        listDataStoreIn = new HashMap<>();
        listDataStoreOut = new HashMap<>();
        int TypeStore = Constants.INPROCESS;
        ExpanData(Constants.INPROCESS,"ยังไม่ดำเนินการ ",0);
    }

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }
    DataExpanManager dataExpanManager;
    private void ExpanData(int StoreType,String text,int index){
        dataExpanManager = new DataExpanManager(getContext());
        // Set Head List
//        listDataHeader.add(text + dataExpanManager.getCountColumn(StoreType)+ " ร้าน");
        listDataHeader.add("เลขที่ใบงาน 564");
        // Adding child data
        listDataChild.put(listDataHeader.get(index), dataExpanManager.getListStatus(StoreType)); // Header, Child data
        listDataStorePhone.put(listDataHeader.get(index), dataExpanManager.getListPhon(StoreType));
        listDataStoreIn.put(listDataHeader.get(index), dataExpanManager.getListItemIN(StoreType));
        listDataStoreOut.put(listDataHeader.get(index), dataExpanManager.getListItemOut(StoreType));

    }
}
