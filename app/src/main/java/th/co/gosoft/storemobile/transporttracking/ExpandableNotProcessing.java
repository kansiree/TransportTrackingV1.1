package th.co.gosoft.storemobile.transporttracking;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.co.gosoft.storemobile.transporttracking.Adapters.NExpandableListAdapter;
import th.co.gosoft.storemobile.transporttracking.Controller.ListDetail;
import th.co.gosoft.storemobile.transporttracking.Models.Constants;
import th.co.gosoft.storemobile.transporttracking.Utility.DataExpanManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpandableNotProcessing extends Fragment {

    ExpandableListView expandableList;
    NExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataStorePhone;
    HashMap<String, List<String>> listDataStoreIn;
    HashMap<String, List<String>> listDataStoreOut;
    public ExpandableNotProcessing() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_expandable_not_processing, container, false);
        setview(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callExpandableList();

    }

    private void setview(View view){
        expandableList = (ExpandableListView)view.findViewById(R.id.Expan_submit);
    }

    private void callExpandableList(){
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataStorePhone = new HashMap<>();
        listDataStoreIn = new HashMap<>();
        listDataStoreOut = new HashMap<>();
        ExpanData(Constants.SUCESS,"สำเร็จ ",0);
        ExpanData(Constants.FAIL,"ไม่สำเร็จ ",1);
        listAdapter = new NExpandableListAdapter(getContext(),listDataHeader,listDataChild,listDataStorePhone,listDataStoreIn,listDataStoreOut);
        expandableList.setAdapter(listAdapter);

        listAdapter.setClickItem(new NExpandableListAdapter.onClickItem() {
            @Override
            public void onItemClick(View view, int position, String id) {
                Intent intent = new Intent(getContext(),ListDetail.class);
                intent.putExtra("STORE_ID", String.valueOf(id));
                startActivity(intent);
            }
        });
    }
    DataExpanManager dataExpanManager;

    private void ExpanData(int StoreType,String text,int index){
        dataExpanManager = new DataExpanManager(getContext());
        // Set Head List
        listDataHeader.add(text + dataExpanManager.getCountColumn(StoreType)+ " ร้าน");
        System.out.println("print: "+listDataHeader.size()+" "+listDataChild.size());
        // Adding child data
        listDataChild.put(listDataHeader.get(index), dataExpanManager.getListStatus(StoreType)); // Header, Child data
        listDataStorePhone.put(listDataHeader.get(index), dataExpanManager.getListPhon(StoreType));
        listDataStoreIn.put(listDataHeader.get(index), dataExpanManager.getListItemIN(StoreType));
        listDataStoreOut.put(listDataHeader.get(index), dataExpanManager.getListItemOut(StoreType));
    }
}
