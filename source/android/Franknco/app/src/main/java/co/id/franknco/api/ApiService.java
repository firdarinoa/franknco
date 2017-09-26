package co.id.franknco.api;


import co.id.franknco.model.GeneralResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiService {

    //LOGIN - 0100
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> login(@Field("code") String code,
                                @Field("msg") String msg);

    //SIGN UP - 0200
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> signUp(@Field("code") String code,
                                 @Field("msg") String msg);

    //FORGOT PASSWORD - 0300
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> forgotPassword(@Field("code") String code,
                                         @Field("msg") String msg);

    //CHANGE PASSWORD - 0400
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> changePassword(@Field("code") String code,
                                         @Field("msg") String msg,
                                         @Field("token")String token);

    //GET CARD KEY FOR DECRYPT CARD CREDIT - 0500
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> getCardKeyForDecryptCard(@Field("code") String code,
                                                   @Field("msg") String msg,
                                                   @Field("token")String token);

    //GET CARD CREDIT - 0600
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> getCardCredit(@Field("code") String code,
                                        @Field("msg") String msg,
                                        @Field("token")String token);
    //GET USER CARD LIST - 0700
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> getUserCardList(@Field("code") String code,
                                          @Field("msg") String msg,
                                          @Field("token")String token);

    //GET CARD TRANSACTION LIST - 0800
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> getCardTransactoinList(@Field("code") String code,
                                                 @Field("msg") String msg,
                                                 @Field("token")String token);

    //REGISTER CARD - 0900
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> registerCard(@Field("code") String code,
                                       @Field("msg") String msg,
                                       @Field("token")String token);

    //ENCRYPT 3 DES - 1600
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> encrypt3Des(@Field("code") String code,
                                      @Field("msg") String msg);

    //DECRYPT 3 DES - 1700
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> decrypt3Des(@Field("code") String code,
                                      @Field("msg") String msg);

    //ENCRYPT 3 DES CARD CREDIT - 1800
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> encrypt3DesCardCredit(@Field("code") String code,
                                                @Field("msg") String msg);

    //DECRYPT 3 DES CARD CREDIT - 1900
    @FormUrlEncoded
    @POST("/hitaps")
    Call<GeneralResponse> decrypt3DesCardCredit(@Field("code") String code,
                                                @Field("msg") String msg);

}
