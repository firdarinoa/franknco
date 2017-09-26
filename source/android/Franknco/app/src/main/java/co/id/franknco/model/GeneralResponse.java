package co.id.franknco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by e_er_de on 13/09/2017.
 */

public class GeneralResponse {
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("msg")
    @Expose
    private List<General> msg = null;

    @SerializedName("msg")
    @Expose
    private transient String msgSingle;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<General> getMsg() {
        return msg;
    }

    public void setMsg(List<General> msg) {
        this.msg = msg;
    }

    public String getMsgSingle() {
        return msgSingle;
    }

    public void setMsgSingle(String msgSingle) {
        this.msgSingle = msgSingle;
    }

}
