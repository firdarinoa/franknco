package co.id.franknco.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import co.id.franknco.ui.login.LoginActivity;
import co.id.franknco.ui.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static co.id.franknco.controller.AppController.TAG;

/**
 * Created by GSS-NB-2016-0012 on 8/26/2017.
 */

public class Function {

    private Context context;
    private Activity activity;
    private SessionManager sessionManager;
    private ProgressDialog pDialog;


    public Function(Activity mContext) {
        context = mContext;
        activity = mContext;
        sessionManager = new SessionManager(context);
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
    }

    public void Logout(final String token, final String auth) {
        String tag_string_req = "req_hit_logout";
        final Map<String, String> postParam = new HashMap<String, String>();
        // postParam.put("pos_id", "02700309116123000100");

        Log.e("VERIFIKASI logout", token + " " + auth);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL_Logout, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //TRACE.w(String.valueOf(response));
                        try {

                            String status = response.getString("status");
                            String timestamp = response.getString("midware_timestamp");
                            if (status.equals("LOGOUT SUCCESS.")) {
                                Intent myintent = new Intent(activity, LoginActivity.class);
                                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.finish();
                                activity.startActivity(myintent);
                                Log.e("LOGOUT", "CIE BERHASIL LOGOUT");
                            }

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN


                } else { //DATA GIVEN

                    // hideDialog();
                }
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Posting header json to url
                HashMap<String, String> headers = new HashMap<String, String>();
                // headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("token", token);
                headers.put("authorization", auth);
                return headers;

            }

        };
        //for timeout and make data do not send twice
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                GetUrlName.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    public void VerifikasiToken(final String token, final String auth) {
        String tag_string_req = "req_hit_verifikasitoken";
        Map<String, String> postParam = new HashMap<String, String>();
        // postParam.put("pos_id", "02700309116123000100");

        Log.e("VERIFIKASI", token + " " + auth);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL_verif_token, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //TRACE.w(String.valueOf(response));
                        try {

                            String status = response.getString("status");
                            String timestamp = response.getString("midware_timestamp");
                            if (status.equals("TOKEN IS GOOD.")) {
                                sessionManager.setStatusToken(status);
                                Log.e("VERIFIKASI", "CIE BERHASIL VERIFIKASI");
                            } else if (status.equals("VALIDATION FAILED.")) {
                                Log.e("VERIFIKASI", "CIE di kick");
                                Intent myintent = new Intent(activity, LoginActivity.class);
                                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.finish();
                                activity.startActivity(myintent);

                                Log.e("LOGOUT", "CIE BERHASIL LOGOUT");
                            } else if (status.equals("TOKEN EXPIRED.")) {
                                Log.e("VERIFIKASI", "CIE di kick");
                                sessionManager.setStatusToken(status);
                                ActivityLogin(sessionManager.getUsername(), sessionManager.getPassword(), "1");

                                Log.e("LOGOUT", "CIE BERHASIL LOGOUT");
                            }

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN


                } else { //DATA GIVEN

                    // hideDialog();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Posting header json to url
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("token", token);
                headers.put("authorization", auth);
                return headers;

            }

        };
        //for timeout and make data do not send twice
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                GetUrlName.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    public void ActivityLogin(final String username, final String password, final String Flag_Login) {

        // Tag used to cancel the request
        String tag_string_req = "req_ActivityLogin";
        String dataMSG = "";
        pDialog.setMessage("Logging in ...");
        //showDialog();
        dataMSG = username + "#" + Configurasi.md5(password);
        Log.e("ISI PASSWORD SHA-2 ", Configurasi.md5(password));
        Log.e("dataMSG", dataMSG);


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("username", username);
        postParam.put("password", Configurasi.md5(password));
        // postParam.put("version", GetUrlName.versioning);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL_Login, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hideDialog();
                            String midwarestamp = response.getString("midware_timestamp");
                            String code = response.getString("status");
                            String TOKEN_ID = response.getString("token");
                            if (code.equals("LOGIN SUCCESS.") && Flag_Login.equals("0")) {
                                Log.e("ERROR MESAGE", String.valueOf(response));

                              /*  // Getting JSON Array node
                                JSONObject apps = response.getJSONObject("apps");
                                Log.e("ERROR MESAGE APPS", String.valueOf(apps));
*/
                                sessionManager.createLoginSession(username, Configurasi.md5(password), TOKEN_ID);
                                Log.e("LOGIN", TOKEN_ID);
                                VerifikasiToken(TOKEN_ID, GetUrlName.authorization + TOKEN_ID);
                                Intent intent = new Intent(activity,
                                        MainActivity.class);
                                activity.finish();
                                activity.startActivity(intent);
                            } else if (code.equals("LOGIN SUCCESS.") && Flag_Login.equals("1")) {
                                sessionManager.setTokenId(TOKEN_ID);

                            } else {
                                // Error in LoginActivity. Get the error message
                                //String errorMsg = jObj.getString("user");
                                Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "LoginActivity Error: " + error.getMessage());
                if (error != null) {
                    Bundle ActivityLoginError = new Bundle();
                    ActivityLoginError.putString("ERROR_ActivityLogin", "Server Is Offline!");

                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show();

                } else {
                    hideDialog();
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to LoginActivity url
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        //for timeout and make data do not send twice
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                GetUrlName.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }





    public void Update_Child_TerminalIDDetail(final String tid_child_list, final String terminal_child_active_stat, final String token, final String auth) {
        String tag_string_req = "req_UPDATE_CHILD";
        final Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("terminal_child_id", tid_child_list);
        postParam.put("terminal_child_active_stat", terminal_child_active_stat);

        Log.e("VERIFIKASI detail tid", token + " " + auth);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GetUrlName.URL_Update_Child_Terminal_Id_details, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //TRACE.w(String.valueOf(response));
                        try {

                            String status = response.getString("status");
                            String terminal_child_active_stat = response.getString("terminal_child_active_stat");
                            String terminal_child_type = response.getString("terminal_child_type");
                            String terminal_child_list = response.getString("terminal_child_list");
                            String tid = response.getString("tid");
                            String timestamp = response.getString("midware_timestamp");
                            if (status.equals("SUCCESS TO GET TERMINAL DETAILS.")) {
                                Intent myintent = new Intent(activity, LoginActivity.class);
                                myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.finish();
                                activity.startActivity(myintent);
                            }

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN


                } else { //DATA GIVEN

                    // hideDialog();
                }
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Posting header json to url
                HashMap<String, String> headers = new HashMap<String, String>();
                // headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("token", token);
                headers.put("authorization", auth);
                return headers;

            }

        };
        //for timeout and make data do not send twice
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                GetUrlName.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
