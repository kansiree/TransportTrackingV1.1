package th.co.gosoft.storemobile.transporttracking.Models.HQ;

/**
 * Created by Jubjang on 9/25/2017.
 */

public class Config {

    //local
//    private static final String PROTOCAL = "http";
//    private static final String PORT = "9080";
//    private static final String HOST = "10.186.206.161";

    //qa
    private static final String PROTOCAL = "http";
    private static final String HOST = "sescswedv04.cpall.co.th";

    private static final String SUB_API = "TransportTrackingHQ";

    //local
    //private static final String PREFIX = PROTOCAL.concat("://").concat(HOST).concat(":").concat(PORT).concat("/").concat(SUB_API);

    //qa
    private static final String PREFIX = PROTOCAL.concat("://").concat(HOST).concat("/").concat(SUB_API);


    public static final String URL_LOGIN =PREFIX.concat("/RequestPersonLogin");
    public static final String URL_STORELIST =PREFIX.concat("/RequestStoreList");

    //Google map
    public static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?mode=driving&";
    public static final String GOOGLE_API_KEY = "AIzaSyBtMq7PNVp6P89Vn7PVIaEAXZWCSzpHSws";
}
