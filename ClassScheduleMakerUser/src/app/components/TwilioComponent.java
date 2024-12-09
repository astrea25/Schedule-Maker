package app.components;

import java.util.Base64;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

@Component
public class TwilioComponent {
	
	
	 //@Scheduled(fixedRate=1000)
	 public TwilioReply sendMessage(String number, String message) throws Exception {
	      String creds = "AC1ac8374259b05e592f476f9387b56f6d:3007ceb6aad1c1318745ea5fa6268f05";
	      String msgsid = "MG491b399e49276ddbc838035b307efff4";
	      String url = "https://api.twilio.com/2010-04-01/Accounts/AC1ac8374259b05e592f476f9387b56f6d/Messages.json";
	      Retrofit retrofit = (new Builder()).baseUrl("https://bogus").build();
	      byte[] encodedAuth = Base64.getEncoder().encode(creds.getBytes());
	      String authorization = "Basic " + new String(encodedAuth);
	      TwilioRequests req = (TwilioRequests)retrofit.create(TwilioRequests.class);
	      Call<ResponseBody> call = req.testSMS(number, 
	    		  msgsid, 
	    		  message, 
	    		  authorization, 
	    		  url);
	      
	      
	      Response<ResponseBody> resp = call.execute();
	      if (resp.code() == 201) {
	          ObjectMapper objectMapper = new ObjectMapper();
	          TwilioReply reply = objectMapper.readValue(resp.body().string(), TwilioReply.class);
	           System.out.println("Status: " + reply.getStatus());
	          return reply;
	       } else {
	    	   ObjectMapper objectMapper = new ObjectMapper();
	    	   TwilioReply reply = objectMapper.readValue(resp.errorBody().string(), TwilioReply.class);
	           System.out.println(resp.errorBody().string());
	          return reply;
	       }
	   }

}
