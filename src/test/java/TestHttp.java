//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//public class TestHttp {
//
//    private static HttpClient httpClient = new DefaultHttpClient();
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//
//        TestHttp fixture = new TestHttp();
//
//        try {
//            fixture.doGet();
//            fixture.doPost();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void doGet() throws ClientProtocolException, IOException {
//
//		/* create the HTTP client and GET request */
//        HttpGet httpGet = new HttpGet("http://hc.apache.org/");
//
//		/* execute request */
//        HttpResponse httpResponse = httpClient.execute(httpGet);
//        HttpEntity httpEntity = httpResponse.getEntity();
//
//		/* process response */
//        if (httpResponse.getStatusLine().getStatusCode() == 200) {
//
//            String responseText = EntityUtils.toString(httpEntity);
//
//            System.out.println(responseText);
//
//        } else {
//            System.err.println("Invalid HTTP response: "
//                    + httpResponse.getStatusLine().getStatusCode());
//        }
//
//    }
//
//    private void doPost() throws IOException, UnsupportedEncodingException,
//            ClientProtocolException {
//
//		/* create the HTTP client and POST request */
//        HttpPost httpPost = new HttpPost("http://someUrl/bla");
//
//        String parm1 = "0123456789abcdef0123456789abcdef";
//
//		/* add some request parameters for a form request */
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("parm1", parm1));
//        nvps.add(new BasicNameValuePair("submit", "Submit Query"));
//        nvps.add(new BasicNameValuePair("Content-Type",
//                "application/x-www-form-urlencoded"));
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
//
//		/* execute request */
//        HttpResponse httpResponse = httpClient.execute(httpPost);
//        HttpEntity httpEntity = httpResponse.getEntity();
//
//		/* process response */
//        if (httpResponse.getStatusLine().getStatusCode() == 200) {
//            String responseText = EntityUtils.toString(httpEntity);
//            System.out.println(responseText);
//
//        } else {
//            System.err.println("Invalid HTTP response: "
//                    + httpResponse.getStatusLine().getStatusCode());
//        }
//
//    }
//}