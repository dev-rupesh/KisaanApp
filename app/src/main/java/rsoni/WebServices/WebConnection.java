package rsoni.WebServices;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import rsoni.Utils.DataResult;


public class WebConnection {
	
	String error_msg = "";

	

	public DataResult invalidResponse() {
		DataResult dataResult = new DataResult();
		dataResult.Status = false;
		dataResult.msg = error_msg;
		return dataResult;
	}

	public String getJsonFromUrl2(String url, ArrayList<NameValuePair> param) {
		System.out.println("getResponce() post...");
		System.out.println("url : "+url);
		String json = null;
		System.out.println("param : "+param.toString());

		try {
			HttpClient httpclient = new DefaultHttpClient();
			System.out.println("URL : " + url);
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(param));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			System.out.println("1eee");
			json = EntityUtils.toString(httpEntity);

		} catch (ClientProtocolException e) {
			error_msg = "1. Unable to connect to server. Please try again or contect to Support.";
			System.out
					.println(" Error From getXMLFromUrl... ClientProtocolException ");
			e.printStackTrace();
		} catch (IOException e) {
			error_msg = "2. Unable to connect to server. Please try again or contect to Support.";
			System.out.println(" Error From getXMLFromUrl... IOException ");
			e.printStackTrace();
		}

		if (json != null) {

			// json = json.substring(json.indexOf("{"));

			System.out.println("Json :  " + json);

		} else {
			System.out.println("Json :  " + "some error to read it.");
		}
		return json;
	}
	
	public String getJsonFromUrlGet(String url, ArrayList<NameValuePair> param) {
		System.out.println("getResponce() get...");
		System.out.println("url : "+url);
		String json = null;
		System.out.println("param : "+param.toString());
		
		if(param.size()>0){
			String paramString = URLEncodedUtils.format(param, "utf-8");
			url += "?"+paramString;
			System.out.println("url : "+url);
		}

		try {
			HttpClient httpclient = new DefaultHttpClient();
			System.out.println("URL : " + url);
			HttpGet httpGet = new HttpGet(url);			

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			System.out.println("1eee");
			json = EntityUtils.toString(httpEntity);

		} catch (ClientProtocolException e) {
			error_msg = "1. Unable to connect to server. Please try again or contect to Support.";
			System.out
					.println(" Error From getXMLFromUrl... ClientProtocolException ");
			e.printStackTrace();
		} catch (IOException e) {
			error_msg = "2. Unable to connect to server. Please try again or contect to Support.";
			System.out.println(" Error From getXMLFromUrl... IOException ");
			e.printStackTrace();
		}

		if (json != null) {

			// json = json.substring(json.indexOf("{"));

			System.out.println("Json :  " + json);

		} else {
			System.out.println("Json :  " + "some error to read it.");
		}
		return json;
	}

	

	

}
