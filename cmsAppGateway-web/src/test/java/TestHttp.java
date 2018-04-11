import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class TestHttp {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://my.creditweb:8080/creditPreAdvice-web/preRequest");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("arg0", "111"));
		nvps.add(new BasicNameValuePair("arg1", "222"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		httpPost.addHeader("version", "11");
		httpPost.addHeader("userAgent","xx");
		HttpResponse response = httpclient.execute(httpPost);
		InputStream is = response.getEntity().getContent();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(
				is));
        String line;
        String result = "";
        while ((line = in.readLine()) != null) {
            result += line;
        }
        System.out.println(result);
		
//		httpclient.getConnectionManager().shutdown();
	}

}
