package th.co.gosoft.storemobile.transporttracking.Controller.DB;

import th.co.gosoft.storemobile.transporttracking.Models.HQ.Config;
import th.co.gosoft.storemobile.transporttracking.Models.HQ.RequestPersonLogin;
import th.co.gosoft.storemobile.transporttracking.Models.HQ.RequestStoreList;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by Jubjang on 9/25/2017.
 */

public class DataManager {
    final int READ_TIMEOUT = 2 * 60 * 1000;//2 min
    final Gson mGson;
    private  static DataManager mDataManager = null;

    public DataManager() {
        mGson = new Gson();
    }

    public static DataManager getInstance() {
        if (mDataManager == null) mDataManager = new DataManager();
        return mDataManager;
    }

    public RequestPersonLogin authen(String username, String password) throws Exception{

        AFSimpleHTTPReader simple = new AFSimpleHTTPReader();
        HashMap posData = new HashMap<>();
        posData.put("username", username);
        posData.put("password", password);

        //change obj to json platform
        JSONObject object = new JSONObject(posData);
        System.out.println("json : "+object.toString());

        simple.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simple.setReadTimeOut(READ_TIMEOUT);

        //read data from web server to String obj in json platform
        String res = simple.readHTML(new URL(Config.URL_LOGIN), object.toString()).toString();
        System.out.println("json return : "+res);

        //change data from json to java obj by Gson class and return to class
        return mGson.fromJson(res, RequestPersonLogin.class);
    }

    public RequestStoreList downloadWork(String workID)throws Exception{

        AFSimpleHTTPReader simple = new AFSimpleHTTPReader();
        HashMap posData = new HashMap<>();
        posData.put("workID", workID);

        //change obj to json platform
        JSONObject object = new JSONObject(posData);
        System.out.println("json : "+object.toString());

        simple.setMethod(AFSimpleHTTPReader.METHOD_POST);
        simple.setReadTimeOut(READ_TIMEOUT);

        //read data from web server to String obj in json platform
        String res = simple.readHTML(new URL(Config.URL_STORELIST), object.toString()).toString();
        System.out.println("json return : "+res);

        //change data from json to java obj by Gson class and return to class
        return mGson.fromJson(res, RequestStoreList.class);
    }
}
