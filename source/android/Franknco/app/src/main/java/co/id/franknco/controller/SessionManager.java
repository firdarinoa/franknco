package co.id.franknco.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import co.id.franknco.ui.login.LoginActivity;

import java.util.HashMap;

/**
 * Created by GSS-NB-2016-0012 on 7/19/2017.
 */

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "MPos Login";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    //ID (make variable public to access from outside)
    public static final String KEY_ID = "id";

    //Password (make variable public to access from outside)
    public static final String KEY_PASSWORD = "pass";

    //merchant Name (make variable public to access from outside)
    public static final String MERCHANT_NAME = "MerchantName";

    //merchant Address (make variable public to access from outside)
    public static final String MERCHANT_ADDRESS = "MerchantAddress";

    //merchant Id (make variable public to access from outside)
    public static final String TOKEN_ID = "token";

    //merchant Id (make variable public to access from outside)
    public static final String PARENT_ID = "Parent_ID";

    //sn_terminal (make variable public to access from outside)
    public static final String SERIAL_NUMBER_TERMINAL = "sn_terminal";

    //tipe_transaction (make variable public to access from outside)
    public static final String TIPE_TRANSACTION = "tipe_transaction";

    //merchant Id (make variable public to access from outside)
    public static final String TIME_OUT_TRANSACTION = "timeout_trans";

    //merchant Id (make variable public to access from outside)
    public static final String TIME_OUT_SETTLEMENT = "timeout_settle";

    //merchant Id (make variable public to access from outside)
    public static final String STATUS_TOKEN = "status_token";

    /**
     * CC BRI
     */
    //BRANCH Id (make variable public to access from outside)
    public static final String BRANCH_ID_CC_BRI = "branch_id";

    //MERCHANT Id (make variable public to access from outside)
    public static final String MERCHANT_ID_CC_BRI = "merchant_ID";

    //TERMINAL Id (make variable public to access from outside)
    public static final String TERMINAL_ID_CC_BRI = "terminal_id";

    /**
     * DEBIT  BRI
     */
    //BRANCH Id (make variable public to access from outside)
    public static final String BRANCH_ID_DB_BRI = "branch_id";

    //MERCHANT Id (make variable public to access from outside)
    public static final String MERCHANT_ID_DB_BRI = "merchant_ID";

    //TERMINAL Id (make variable public to access from outside)
    public static final String TERMINAL_ID_DB_BRI = "terminal_id";

    /**
     * BRIZZI
     */
    //BRANCH Id (make variable public to access from outside)
    public static final String BRANCH_ID_BRIZZI_BRI = "branch_id";

    //MERCHANT Id (make variable public to access from outside)
    public static final String MERCHANT_ID_BRIZZI_BRI = "merchant_ID";

    //TERMINAL Id (make variable public to access from outside)
    public static final String TERMINAL_ID_BRIZZI_BRI = "terminal_id";


    //merchant Id (make variable public to access from outside)
    public static final String BATCH = "batch";


    //merchant Id (make variable public to access from outside)
    public static final String DESCRIPTION = "description";

    public SessionManager(Context context) {
        this._context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }

    //For Session Login
    public void createLoginSession(String id, String pass, String token_id) {
        Log.e("HIT", id+pass+token_id);
        // Storing name in pref
        editor.putString(KEY_ID, id);

        // Storing email in pref
        editor.putString(KEY_PASSWORD, pass);

        // Storing token in pref
        editor.putString(TOKEN_ID, token_id);

        // commit changes
        editor.commit();
    }

    public void setStatusToken(String statusToken) {

        // Storing char pass in pref
        editor.putString(STATUS_TOKEN, statusToken);
        // commit changes
        editor.commit();

    }

    public void setTokenId(String tokenId) {

        // Storing char pass in pref
        editor.putString(TOKEN_ID, tokenId);
        // commit changes
        editor.commit();

    }

    public void setTipeTransaction(String tipeTransaction) {

        // Storing char pass in pref
        editor.putString(TIPE_TRANSACTION, tipeTransaction);
        // commit changes
        editor.commit();

    }

    public void setDescription(String description) {

        // Storing char pass in pref
        editor.putString(DESCRIPTION, description);
        // commit changes
        editor.commit();

    }

    public void clearDescription(String description) {

        // Storing char pass in pref
        editor.putString(DESCRIPTION, "");
        // commit changes
        editor.commit();

    }

    //Session for merchant Name, merchant Address, Privillage, merchant ID
    public void createDataSessionCREDITBRI(String bid, String mid, String tid)

    {
        Log.e("SET credit bri", bid+mid+tid);
        editor.putString(BRANCH_ID_CC_BRI, bid);
        editor.putString(MERCHANT_ID_CC_BRI, mid);
        editor.putString(TERMINAL_ID_CC_BRI, tid);

        // commit changes
        editor.commit();
    }

    public void createDataSessionDEBITBRI(String bid, String mid, String tid)

    {
        Log.e("SET debit bri", bid+mid+tid);
        editor.putString(BRANCH_ID_DB_BRI, bid);
        editor.putString(MERCHANT_ID_DB_BRI, mid);
        editor.putString(TERMINAL_ID_DB_BRI, tid);

        // commit changes
        editor.commit();
    }

    public void createDataSessionBRIZZI(String bid, String mid, String tid)

    {
        Log.e("SET brizzi", bid+mid+tid);
        editor.putString(BRANCH_ID_BRIZZI_BRI, bid);
        editor.putString(MERCHANT_ID_BRIZZI_BRI, mid);
        editor.putString(TERMINAL_ID_BRIZZI_BRI, tid);

        // commit changes
        editor.commit();
    }


    //FOR BATCH
    public void set_batch(String batch) {
        // Check login status
        editor.putString(BATCH, batch);

        // commit changes
        editor.commit();
    }




    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.e(TAG, "User login session modified!");
    }

    //Get stored session data
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // user pass
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // user merchant name
        user.put(MERCHANT_NAME, pref.getString(MERCHANT_NAME, null));

       // user privillage
        user.put(TOKEN_ID, pref.getString(TOKEN_ID, null));


        // user merchant addresss
        user.put(MERCHANT_ADDRESS, pref.getString(MERCHANT_ADDRESS, null));

        // user parent id
        user.put(PARENT_ID, pref.getString(PARENT_ID, null));


        // user sn terminal
        user.put(SERIAL_NUMBER_TERMINAL, pref.getString(SERIAL_NUMBER_TERMINAL, null));


        // user sn terminal
        user.put(TIME_OUT_TRANSACTION, pref.getString(TIME_OUT_TRANSACTION, null));

        // user sn terminal
        user.put(TIME_OUT_SETTLEMENT, pref.getString(TIME_OUT_SETTLEMENT, null));

       // user sn terminal
        user.put(BATCH, pref.getString(BATCH, null));


        // return user
        return user;
    }

    //FOR LOGOUT USER AND REMOVE SESSION
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Log.e(TAG, "User logout!");
        // After logout redirect user to Loing activity
        Intent i;
        i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login activity
        _context.startActivity(i);

    }

    public void logoutUser_L() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Log.e(TAG, "User logout TEST!");
        // After logout redirect user to Loing activity


    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getUsername() {
        return pref.getString(KEY_ID, "null");
    }

    public String getPassword() {
        return pref.getString(KEY_PASSWORD, "null");
    }

    public String getSerialNumberTerminal() {
        return pref.getString(SERIAL_NUMBER_TERMINAL, "null");
    }

    public String getTokenId() {
        return pref.getString(TOKEN_ID, "null");

    }

    public String getStatusToken() {
        return pref.getString(STATUS_TOKEN, "null");

    }

    public String getTimeoutTransaction() {
        return pref.getString(TIME_OUT_TRANSACTION, "0");
    }

    public String getDescription() {
        return pref.getString(DESCRIPTION, "");
    }

    public String getTipeTransaction() {
        return pref.getString(TIPE_TRANSACTION, "");
    }

    /** get branch id */
    public String getBranchid(String apps) {

        String result="";
        if(apps.equals("CREDIT_BRI_PLAIN")){
            result = pref.getString(BRANCH_ID_CC_BRI, "");
        }
        else if(apps.equals("DEBIT_BRI_PLAIN")){
            result = pref.getString(BRANCH_ID_DB_BRI, "");
        }
        else if(apps.equals("BRIZZI_PLAIN")){
            result = pref.getString(BRANCH_ID_BRIZZI_BRI, "");
        }
        Log.e("GET BID", result);
        return result;
    }

    /** get merchant id */
    public String getMerchantid(String apps) {

        String result="";
        if(apps.equals("CREDIT_BRI_PLAIN")){
            result = pref.getString(MERCHANT_ID_CC_BRI, "");
        }
        else if(apps.equals("DEBIT_BRI_PLAIN")){
            result = pref.getString(MERCHANT_ID_DB_BRI, "");
        }
        else if(apps.equals("BRIZZI_PLAIN")){
            result = pref.getString(MERCHANT_ID_BRIZZI_BRI, "");
        }
        Log.e("GET MID", result);
        return result;
    }

    /** get terminal id */
    public String getTerminalid(String apps) {

        String result="";
        if(apps.equals("CREDIT_BRI_PLAIN")){
            result = pref.getString(TERMINAL_ID_CC_BRI, "");
        }
        else if(apps.equals("DEBIT_BRI_PLAIN")){
            result = pref.getString(TERMINAL_ID_DB_BRI, "");
        }
        else if(apps.equals("BRIZZI_PLAIN")){
            result = pref.getString(TERMINAL_ID_BRIZZI_BRI, "");
        }
        Log.e("GET TID", result);
        return result;
    }
}