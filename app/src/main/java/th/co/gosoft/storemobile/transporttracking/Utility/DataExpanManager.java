package th.co.gosoft.storemobile.transporttracking.Utility;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import th.co.gosoft.storemobile.transporttracking.Models.Constants;
import th.co.gosoft.storemobile.transporttracking.Models.model_Expanlist;

/**
 * Created by print on 12/22/2017.
 */

public class DataExpanManager {

    Context context;
    SQLliteManager sqLliteManager;
    ArrayList<model_Expanlist.model_item> list_item ;
    model_Expanlist.model_item model_item ;
    model_Expanlist expanlist;

    public DataExpanManager(Context context) {
        this.context = context;
        sqLliteManager = new SQLliteManager(context);
    }

    public ArrayList<String> getListStatus(int Type){
        ArrayList<String> listStatus = new ArrayList<>();
        model_Expanlist expanlist = getDataBase(Type);
        for (model_Expanlist.model_item item:expanlist.getItems()){
            listStatus.add(item.getStatus());
        }
       return listStatus;
    }

    public ArrayList<String> getListPhon(int Type){
        ArrayList<String> listStatus = new ArrayList<>();
        model_Expanlist expanlist = getDataBase(Type);
        for (model_Expanlist.model_item item:expanlist.getItems()){
            listStatus.add(item.getPhon());
        }
        return listStatus;
    }

    public ArrayList<String> getListItemIN(int Type){
        ArrayList<String> listStatus = new ArrayList<>();
        model_Expanlist expanlist = getDataBase(Type);
        for (model_Expanlist.model_item item:expanlist.getItems()){
            listStatus.add(item.getItemIN());
        }
        return listStatus;
    }
    public ArrayList<String> getListItemOut(int Type){
        ArrayList<String> listStatus = new ArrayList<>();
        model_Expanlist expanlist = getDataBase(Type);
        for (model_Expanlist.model_item item:expanlist.getItems()){
            listStatus.add(item.getItemOut());
        }
        return listStatus;
    }

    public int getCountColumn(int TypeStore){
        return sqLliteManager.getCount(Constants.STORE_TYPE,Constants.TABLE_NAME_STORE, TypeStore);
    }
    private model_Expanlist getDataBase(int TypeStore){
        expanlist = new model_Expanlist();
        list_item = new ArrayList<>();
        Cursor cursor =sqLliteManager.getTypeStore(Constants.TABLE_NAME_STORE,Constants.STORE_TYPE,TypeStore);
        if (cursor.moveToFirst()){
            do {
                model_item = new model_Expanlist.model_item();

                //add store_name and phone
                model_item.setStatus(cursor.getString(2));
                model_item.setPhon(cursor.getString(4));
                //get item In/Out for each store
                int in_item = sqLliteManager.getTypeProduct(Constants.TABLE_NAME,Constants.STORE_ID,cursor.getString(2),Constants.PRODUCT_TYPE,1);
                model_item.setItemIN(String.valueOf(in_item));
                int out_item = sqLliteManager.getTypeProduct(Constants.TABLE_NAME,Constants.STORE_ID,cursor.getString(2),Constants.PRODUCT_TYPE,2);
                model_item.setItemOut(String.valueOf(out_item));
                list_item.add(model_item);
            }while (cursor.moveToNext());
        }
        expanlist.setItems(list_item);
        return expanlist;
    }
}

