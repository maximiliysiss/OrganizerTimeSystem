/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebTest;

/**
 *
 * @author zimma
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HttpClientExample {

    private final String USER_AGENT = "Mozilla/5.0";
    private final int COUNTTEST = 100;
    private final String path = "http://localhost:52603/OrganizerTime/";
    private final int userID = 7;
    private final String userEmail = "maximiliysiss@gmail.com";

    /**
     * Tests for Index.jsp
     * @throws Exception 
     */
    @Test
    public void StressTestGetIndex() throws Exception {
        for (int i = 0; i < COUNTTEST; i++) {
            sendGet();
        }
    }

    /**
     * Tests for Get All LongPlans
     * @throws Exception 
     */
    @Test
    public void StressTestPostGetFileSystem() throws Exception {
        for (int i = 0; i < COUNTTEST; i++) {
            sendPost();
        }
    }

    /**
     * Test for Add new Values
     * @throws Exception 
     */
    @Test
    public void StressTestJDBAddValue() throws Exception {
        for (int i = 0; i < COUNTTEST; i++) {
            JSDBStressAdd();
        }
    }

    /**
     * Tests for Post Organizer.jsp
     * @throws Exception 
     */
    @Test
    public void StressTestJDBGetValue() throws Exception {
        for (int i = 0; i < COUNTTEST; i++) {
            JSDBStressGet();
        }
    }

    private void sendGet() throws Exception {

        String url = path;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());
        assertEquals(200, response.getStatusLine().getStatusCode());

        /*BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());*/
    }

    private void JSDBStressGet() throws UnsupportedEncodingException, IOException {
        String url = path + "FileSystem";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("id", "null"));
        urlParameters.add(new BasicNameValuePair("user", String.valueOf(userID)));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());
        assertEquals(200, response.getStatusLine().getStatusCode());

        /*BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());*/
    }

    private void JSDBStressAdd() throws UnsupportedEncodingException, IOException {
        String url = path + "LongPlanWork";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("type", "add"));
        urlParameters.add(new BasicNameValuePair("user", userEmail));
        urlParameters.add(new BasicNameValuePair("value", "TestValue"));
        urlParameters.add(new BasicNameValuePair("parent", "null"));
        urlParameters.add(new BasicNameValuePair("transform", "1"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());
        assertEquals(200, response.getStatusLine().getStatusCode());

        /*BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());*/
    }

    private void sendPost() throws Exception {

        String url = path + "organizer.jsp";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("useremail", userEmail));
        urlParameters.add(new BasicNameValuePair("userid", "118183429016575076342"));
        urlParameters.add(new BasicNameValuePair("userimageurl", "https://lh4.googleusercontent.com/-FWqDejZTbXg/AAAAAAAAAAI/AAAAAAAAAAA/AB6qoq2yKbN7Yt3nuu-n1fNVEDAC-f4deA/s96-c/photo.jpg"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

}
