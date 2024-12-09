    package app.components;

import java.util.List;

import app.entities.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface TwilioRequests {
   @POST
   @FormUrlEncoded
   Call<ResponseBody> testSMS(@Field("To") String var1, @Field("MessagingServiceSid") String var2, @Field("Body") String var3, @Header("Authorization") String var4, @Url String var5);
   
   //getQuote (Quote Remote)
  // @GET("http://localhost:9997/message/getquote")
  // Call<Quote> getQuote(@Query("c") String var1);
   
   //getUser (User Remote)
   //@GET("http://localhost:9998/user/finduser")
   //Call<User> getUser(@Query("p") Long var1);
   
   //getUserAll (User Remote)
   //@GET("http://localhost:9998/user/finduserall")
   //Call<List<User>> getUserAll();

 

   
}
   
