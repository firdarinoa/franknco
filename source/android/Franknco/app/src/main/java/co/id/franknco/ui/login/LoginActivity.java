package co.id.franknco.ui.login;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.franknco.R;
import co.id.franknco.api.ApiService;
import co.id.franknco.api.RestApi;
import co.id.franknco.controller.Function;
import co.id.franknco.model.GeneralResponse;
import co.id.franknco.ui.forgotpass.ForgotPasswordActivity;
import co.id.franknco.ui.main.MainActivity;
import co.id.franknco.ui.signup.SignupActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GSS-NB-2016-0012 on 8/25/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)EditText _emailText;
    @BindView(R.id.input_password)EditText _passwordText;
    @BindView(R.id.btn_login)Button _loginButton;
    @BindView(R.id.link_signup)TextView _signupLink;

    Function function;

    private String currentDateTimeString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        function = new Function(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
       /* progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
       // progressDialog.show();*/

        String username = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        Intent intent = new Intent(this,
                MainActivity.class);
        finish();
        startActivity(intent);

//        String key3DES = "12345678uaop";
//        Test3DES ted = new Test3DES(key3DES);
//
//        byte[] a = null;
//        try{
//            a = TripleDES.encrypt3DES(encodeUTF8("ersa"), encodeUTF8(key3DES));
//        }catch (Exception e){
//            System.out.println(e.getStackTrace());
//        }
//        Log.e("TAG", "TEST" + new String(Hex.encodeHex(a)));
//
//        String pass256 = HashPassword.sha256(password);
//
//        byte[] username33 = null;
//        byte[] pass33 = null;
//        try {
//            username33 = ted.encrypt(username.getBytes());
//            pass33 = ted.encrypt(pass256.getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String usernameB64 = Base64Utils.encodedStr(username33);
//        String passB64 = Base64Utils.encodedStr(pass33);
//
//        String buildMsg = new String(Hex.encodeHex(username33)) + "#" + new String(Hex.encodeHex(pass33));
//
//        Log.e("TAG", "isi msg : " + buildMsg.toUpperCase() );
//        Log.e("TAG", "user : " + new String(Hex.encodeHex(username33)) + "#" + usernameB64 + "#" + username33 + "#" + username );
//        Log.e("TAG", "pass : " + new String(Hex.encodeHex(pass33)) + "#" + passB64 + "#" + pass33 + "#" + pass256 + "#" + password );
//
//        loginProcess(buildMsg.toUpperCase());

        _loginButton.setEnabled(true);
        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed

                        //onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();

                    }
                }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /** FOR KILL APPS */
        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = getPackageManager();
        //get a list of installed apps.
        packages = pm.getInstalledApplications(0);
        ActivityManager mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        String myPackage = getApplicationContext().getPackageName();
        for (ApplicationInfo packageInfo : packages) {
            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) continue;
            if (packageInfo.packageName.equals(myPackage)) continue;
            mActivityManager.killBackgroundProcesses(packageInfo.packageName);
        }
        /** --------------------------------------  */
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        super.finish();

    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        /*Intent intent = new Intent(getApplicationContext(), TIDActivity.class);
        finish();
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();*/
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ) {
            _emailText.setError("enter USERNAME");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() ) {
            _passwordText.setError("enter PASSWORD");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void loginProcess(String msg){
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Authenticating...");

        ApiService apiService = RestApi.getClient().create(ApiService.class);

        Call<GeneralResponse> call = apiService.login("0100", msg);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse>call, Response<GeneralResponse> response) {
                dialog.dismiss();

                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Start Job received: " + new Gson().toJson(response.body()));

                if (response.code() == 200 && response.body().getCode().equalsIgnoreCase("0110") ) {
                    currentDateTimeString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

//                    Bundle params = new Bundle();
//                    params.putString("staff_code", staff_code);
//                    params.putString("SN", barcode);
//                    params.putString("stock_in_time", currentDateTimeString);
//                    mFirebaseAnalytics.logEvent("stock_in", params);

                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Login Success");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                } else if (response.code() == 200 && response.body().getCode().equalsIgnoreCase("0120")){
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Failed");
                    alertDialog.setMessage("User and Password not Match");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Failed");
                    alertDialog.setMessage("Please Try Again in 10 minutes");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                dialog.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Tidak terhubung ke Server \n periksa koneksi internet !" + t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
    }

    @OnClick(R.id.txt_forgot_pass)
    public void toForgotPass(View v){
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

}
