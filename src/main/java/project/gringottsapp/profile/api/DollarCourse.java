package project.gringottsapp.profile.api;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class DollarCourse {
	 public static double getDollarCourse(){
		  double dollar = 0;
		  CloseableHttpClient httpClient = HttpClients.createDefault();
		  HttpGet get = new HttpGet(Const.BASE_URL + Const.ENDPOINT + "?access_key=" + Const.ACCESS_KEY);
		  try {
				CloseableHttpResponse response =  httpClient.execute(get);
				HttpEntity entity = response.getEntity();
				JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
				dollar = exchangeRates.getJSONObject("quotes").getDouble("USDRUB");
				response.close();
		  } catch (JSONException | ParseException | IOException e) {
				e.printStackTrace();
		  }
		  System.out.println(dollar);
		  System.out.println((double) Math.round(dollar * 100)/100);
		  return((double) Math.round(dollar * 100)/100); // округляем доллар до сотых
	 }
}
