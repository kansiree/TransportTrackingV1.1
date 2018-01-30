package th.co.gosoft.storemobile.transporttracking.Models;

/**
 * Created by Jubjang on 10/2/2017.
 */

public class model_item {
    String WORK_ID ;
    String STORE_ID ;
    String PRODUCT_CODE ;  //barcode
    String PRODUCT_NAME ;
    String PRODUCT_TYPE ; // 1 = in or 2 = out
    String PRODUCT_STATUS ; //inprogress or success
    String PRODUCT_ADD ;


    public model_item() {

    }

    public model_item(String WORK_ID, String STORE_ID, String PRODUCT_CODE, String PRODUCT_NAME, String PRODUCT_TYPE, String PRODUCT_STATUS){
        this.WORK_ID = WORK_ID;
        this.STORE_ID = STORE_ID;
        this.PRODUCT_CODE = PRODUCT_CODE;
        this.PRODUCT_NAME = PRODUCT_NAME;
        this.PRODUCT_TYPE = PRODUCT_TYPE;
        this.PRODUCT_STATUS = PRODUCT_STATUS;
    }

    public model_item(String WORK_ID, String STORE_ID, String PRODUCT_CODE, String PRODUCT_NAME, String PRODUCT_TYPE, String PRODUCT_STATUS, String PRODUCT_ADD){
        this.WORK_ID = WORK_ID;
        this.STORE_ID = STORE_ID;
        this.PRODUCT_CODE = PRODUCT_CODE;
        this.PRODUCT_NAME = PRODUCT_NAME;
        this.PRODUCT_TYPE = PRODUCT_TYPE;
        this.PRODUCT_STATUS = PRODUCT_STATUS;
        this.PRODUCT_ADD = PRODUCT_ADD;
    }


    public String getWORK_ID() {
        return WORK_ID;
    }

    public void setWORK_ID(String WORK_ID) {
        this.WORK_ID = WORK_ID;
    }

    public String getSTORE_ID() {
        return STORE_ID;
    }

    public void setSTORE_ID(String STORE_ID) {
        this.STORE_ID = STORE_ID;
    }

    public String getPRODUCT_CODE() {
        return PRODUCT_CODE;
    }

    public void setPRODUCT_CODE(String PRODUCT_CODE) {
        this.PRODUCT_CODE = PRODUCT_CODE;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME = PRODUCT_NAME;
    }

    public String getPRODUCT_TYPE() {
        return PRODUCT_TYPE;
    }

    public void setPRODUCT_TYPE(String PRODUCT_TYPE) {
        this.PRODUCT_TYPE = PRODUCT_TYPE;
    }

    public String getPRODUCT_STATUS() {
        return PRODUCT_STATUS;
    }

    public void setPRODUCT_STATUS(String PRODUCT_STATUS) {
        this.PRODUCT_STATUS = PRODUCT_STATUS;
    }

    public String getPRODUCT_ADD() {
        return PRODUCT_ADD;
    }

    public void setPRODUCT_ADD(String PRODUCT_ADD) {
        this.PRODUCT_ADD = PRODUCT_ADD;
    }
}
