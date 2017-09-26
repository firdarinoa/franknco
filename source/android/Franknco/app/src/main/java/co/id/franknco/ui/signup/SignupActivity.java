package co.id.franknco.ui.signup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.api.ApiService;
import co.id.franknco.api.RestApi;
import co.id.franknco.controller.Base64Utils;
import co.id.franknco.controller.Encrypter;
import co.id.franknco.controller.HashPassword;
import co.id.franknco.controller.HexConverter;
import co.id.franknco.controller.Test3DES;
import co.id.franknco.controller.TripleDES;
import co.id.franknco.model.GeneralResponse;
import co.id.franknco.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_address) EditText _addressText;
    @BindView(R.id.input_dateofbirth) EditText etDateOfBirth;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_nocard)EditText etNoCard;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    String currentDateTimeString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signup();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() throws UnsupportedEncodingException {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString().trim();
        String email = _emailText.getText().toString().trim();
        String mobile = _mobileText.getText().toString().trim();
        String password = _passwordText.getText().toString().trim();
        String reEnterPassword = _reEnterPasswordText.getText().toString().trim();

        // TODO: Implement your own signup logic here.
        String key3DES = "12345678uaop";

        Test3DES ted = new Test3DES(key3DES);
        Encrypter tripleDES = new Encrypter();
        TripleDES tDes = new TripleDES(key3DES);

        String pass256 = HashPassword.sha256(password);

        String name3DES = null;
        String addr3DES = null;
        String email3DES = null;
        String mobile3DES = null;
        String numberCard3DES = null;
        String password3DES = null;
        String name33des = null;
        byte[] name333des = null;
        byte[] pass3DES = null;
        try {
            name33des = tDes.harden(name);
            name333des = ted.encrypt(name.getBytes());
            name3DES = tripleDES._encrypt(name, key3DES);
            addr3DES = tripleDES._encrypt(address, key3DES);
            email3DES = tripleDES._encrypt(email, key3DES);
            mobile3DES = tripleDES._encrypt(mobile, key3DES);
            numberCard3DES = tripleDES._encrypt(etNoCard.getText().toString().trim(), key3DES);
            password3DES = tripleDES._encrypt(HashPassword.sha256(password), key3DES);
            pass3DES = ted.encrypt(pass256.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

        String auah = Base64Utils.encodedStr(name333des);
        HexConverter hc = HexConverter.getHexStringConverterInstance();
        String nameHex33 = hc.stringToHex(name33des);
        String nameHex3333 = hc.stringToHex(auah);

        String nameHex = hc.stringToHex(name3DES);
        String addrHex = hc.stringToHex(addr3DES);
        String mailHex = hc.stringToHex(email3DES);
        String mobileHex = hc.stringToHex(mobile3DES);
        String noCardHex = hc.stringToHex(numberCard3DES);
        String passHex = hc.stringToHex(password3DES);

        String buildMsg = noCardHex + "#" + nameHex + "#" + etDateOfBirth.getText() + "#" + addrHex + "#" +
                 mailHex + "#" + mobileHex + "#" + passHex ;

        Log.e("TAG", "wowew: " + buildMsg.toUpperCase() );
        Log.e("TAG", "wowew 2: " + new String(Hex.encodeHex(nameHex33.getBytes())) + "#" + name33des.toUpperCase() + "#" + name );
        Log.e("TAG", "wowew 3: " + new String(Hex.encodeHex(name333des)) + "#" + auah.toUpperCase() + "#" + name );
        Log.e("TAG", "password = " + password + "#pass256 = " + pass256 + "#pass256+3DSE = " + new String(Hex.encodeHex(pass3DES)));

        signUpProcess(buildMsg.toUpperCase());

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }


    public void onSignupSuccess() {
//        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
//        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

//        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=12) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    private void signUpProcess(String msg){
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Creating Account...");

        ApiService apiService = RestApi.getClient().create(ApiService.class);

        Call<GeneralResponse> call = apiService.signUp("0200", msg);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse>call, Response<GeneralResponse> response) {
                dialog.dismiss();

                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Start Job received: " + new Gson().toJson(response.body()));

                if (response.code() == 200 && response.body().getCode().equalsIgnoreCase("0210") ) {
                    currentDateTimeString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

//                    Bundle params = new Bundle();
//                    params.putString("staff_code", staff_code);
//                    params.putString("SN", barcode);
//                    params.putString("stock_in_time", currentDateTimeString);
//                    mFirebaseAnalytics.logEvent("stock_in", params);

                    AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Sign Up Success");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
                    alertDialog.show();
                } else if (response.code() == 200 && response.body().getCode().equalsIgnoreCase("0220")){
                    AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                    alertDialog.setTitle("Failed");
                    alertDialog.setMessage("Error When Inserting to Database");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                } else if (response.code() == 200 && response.body().getCode().equalsIgnoreCase("0230")){
                    AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                    alertDialog.setTitle("Failed");
                    alertDialog.setMessage("User Already Exist");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                    alertDialog.setTitle("Failed");
                    alertDialog.setMessage("Card not Registered to Database");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse>call, Throwable t) {
                dialog.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
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

}