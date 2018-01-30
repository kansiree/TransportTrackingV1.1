package th.co.gosoft.storemobile.transporttracking.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import th.co.gosoft.storemobile.transporttracking.LoginActivity;
import th.co.gosoft.storemobile.transporttracking.Models.model_person;

import static th.co.gosoft.storemobile.transporttracking.Models.Constants.PERSON_NAME;

/**
 * Created by naphatbur on 12/8/2017 AD.
 */

public class SessionManager {

    private static final String PREF_NAME = "SESSION_LOGIN";

    private static final String IS_LOGIN = "ISLOGGIN";

    private static final String KEY_PERSON_NAME = "PERSON_NAME";
    private static final String KEY_PERSON_ID = "PERSON_ID";
    private static final String KEY_PERSON_PHONE = "PERSON_PHONE";
    private static final String KEY_PERSON_CAR = "PERSON_CAR";
    private static final String KEY_PERSON_WORKID = "PERSON_WORKID";


    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context ctx;

    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        this.ctx = context;
        pref = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(model_person objPerson) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_PERSON_NAME, objPerson.getPersonName());
        editor.putString(KEY_PERSON_ID, objPerson.getPersonID());
        editor.putString(KEY_PERSON_PHONE, objPerson.getPersonPhone());
        editor.putString(KEY_PERSON_CAR, objPerson.getPersonCar());
        editor.putString(KEY_PERSON_WORKID, objPerson.getWorkID());

        editor.commit();
    }

    public boolean checkLogin() {
        if (!this.isLoggedIn()) {

            return false;

        }else {
            return true;
        }
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(ctx, LoginActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |

                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        ctx.startActivity(i);
    }


    public model_person getPersonFromPref() {
        model_person objPerson = new model_person();
        objPerson.setPersonID(String.valueOf(pref.getString(KEY_PERSON_ID, "")));
        objPerson.setPersonName(String.valueOf(pref.getString(KEY_PERSON_NAME, "")));
        objPerson.setPersonPhone(String.valueOf(pref.getString(KEY_PERSON_PHONE, "")));
        objPerson.setPersonCar(String.valueOf(pref.getString(KEY_PERSON_CAR, "")));
        objPerson.setWorkID(String.valueOf(pref.getString(KEY_PERSON_WORKID, "")));

        return objPerson;
    }


    private boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
