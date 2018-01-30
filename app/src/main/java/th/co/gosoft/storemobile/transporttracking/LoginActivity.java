package th.co.gosoft.storemobile.transporttracking;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import th.co.gosoft.storemobile.transporttracking.Controller.DB.DataManager;
import th.co.gosoft.storemobile.transporttracking.Dialogs.DialogDownload;
import th.co.gosoft.storemobile.transporttracking.Models.HQ.RequestPersonLogin;
import th.co.gosoft.storemobile.transporttracking.Models.model_person;
import th.co.gosoft.storemobile.transporttracking.Utility.SessionManager;

/**
 * Created by Jubjang on 8/22/2017.
 */

public class LoginActivity extends AppCompatActivity {
    EditText user,pass;
    Button btn;
    String username,password;
    Activity activity;

    Context ctx;

    public static model_person person = new model_person();

    String[] permissionCheck = {    Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        askForPermission();

        activity = this;
        ctx = this;

        user = (EditText) findViewById(R.id.editUsername);
        pass = (EditText) findViewById(R.id.editPassword);
        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get username and password when click
                if(checkTextInput(user)&&checkTextInput(pass)){
                    username = user.getText().toString();
                    password = pass.getText().toString();
                }
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg){
                        super.handleMessage(msg);
                        switch (msg.what){
                            case 0:
                                RequestPersonLogin res = (RequestPersonLogin) msg.obj;
                                if (res!=null){
                                    System.out.println("Return : "+res.getReturnDescription());
                                    person.setWorkID(res.getReturnCode());
                                    person.setPersonID(res.getPersonID());
                                    person.setPersonName(res.getPersonName());
                                    person.setPersonPhone(res.getPersonPhone());
                                    person.setPersonCar(res.getPersonCar());

                                    SessionManager sessionManager = new SessionManager(ctx);
                                    sessionManager.createLoginSession(person);

                                    FragmentActivity fragmentActivity = (FragmentActivity)(activity);
                                    FragmentManager fm = fragmentActivity.getSupportFragmentManager();
                                    DialogDownload alertDialog = new DialogDownload(activity);
//                                    alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                    alertDialog.setCancelable(false);
//                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show(fm,"fragment_alert");

                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Username or Password isn't correct, please try again.",Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(),"Connection Fail,please try again.",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
                Login(handler,username,password);

            }
        });
    }

    //send request to hq use username and password to check
    public void Login(final Handler handler, final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestPersonLogin res = DataManager.getInstance().authen(username,password);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = res;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    //request fail
                    Message message = new Message();
                    message.what = 1;
                    message.obj = e.toString();
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean checkTextInput(EditText editText){
        if(editText.getText()!=null){
            String text = editText.getText().toString().trim();
            if(!(text.equals(""))){
                return true;
            }
        }
        return false;
    }
    private void askForPermission() {
        for (int i = 0; i < permissionCheck.length ; i++) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissionCheck[i]) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,permissionCheck,1);
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                recreate();
                return ;
            }
        }
    }

}
