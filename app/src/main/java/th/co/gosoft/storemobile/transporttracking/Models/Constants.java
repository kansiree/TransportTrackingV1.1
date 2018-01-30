package th.co.gosoft.storemobile.transporttracking.Models;

import android.provider.BaseColumns;

/**
 * Created by Jubjang on 10/2/2017.
 */

public interface Constants extends BaseColumns {
    public static final String TABLE_NAME = "item"; // ชื่อเทเบิล
    public static final String WORK_ID = "workid";        // ชื่อคอลัมน์
    public static final String STORE_ID = "storeid";
    public static final String PRODUCT_CODE = "code";  //barcode
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_TYPE = "type"; // 1 = in or 2 = out
    public static final String PRODUCT_STATUS = "status"; //inprogress or success
    public static final String PRODUCT_ADD = "additem"; //0 or 1 ; 1 = add

    public static final String TABLE_NAME_STORE = "store";
    public static final String WORK_ID_STORE = "workid";
    public static final String STORE_ID_STORE = "storeid";
    public static final String STORE_NAME = "name";
    public static final String STORE_PHONE = "phone";
    public static final String STORE_LAT = "latitude";
    public static final String STORE_LNG = "longitude";
    public static final String STORE_TYPE = "type"; // 1,2,3,4 == success,notprogress,inprogress,fail
    public static final String STORE_STATUS = "status"; // 0,1 == pass the store or not

    public static final String TABLE_NAME_PERSON = "person";
    public static final String PERSON_ID = "id";
    public static final String PERSON_WORKID = "workid";
    public static final String PERSON_TIME = "time";
    public static final String PERSON_DAY = "day";
    public static final String PERSON_NAME = "name";

    public static final String TABLE_NAME_PIC = "pictures";
    public static final String PIC_STOREID = "storeid";
    public static final String PIC_IMAGE = "image";

    public static final String TABLE_NAME_SIGN = "signatures";
    public static final String SIGN_STOREID = "storeid";
    public static final String SIGN_IMAGE = "sign";

    public static final String TABLE_NAME_COMMENT = "comments";
    public static final String COM_STOREID = "storeid";
    public static final String COM_TEXT = "text";

    public static final int SUCESS = 1;
    public static final int INPROCESS = 2;
    public static final int STILL = 3;
    public static final int FAIL = 4;
}
