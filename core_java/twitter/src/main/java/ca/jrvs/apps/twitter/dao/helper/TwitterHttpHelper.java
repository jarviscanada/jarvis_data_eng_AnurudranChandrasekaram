package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterHttpHelper implements HttpHelper{

    /**
     * Dependencies are specified as private member variables
     */
    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        /**
         * Default = single connection. Discuss source code if time permit
         */
        httpClient = new DefaultHttpClient();
    }

    public TwitterHttpHelper() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        /**
         * Default = single connection. Discuss source code if time permit
         */
        httpClient = new DefaultHttpClient();
    }
    @Override
    public HttpResponse httpPost(URI uri) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
        HttpPost request = new HttpPost(uri);
        consumer.sign(request);
        HttpResponse httpResponse = httpClient.execute(request);
        return httpResponse;
    }

    @Override
    public HttpResponse httpGet(URI uri) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
        HttpGet request = new HttpGet(uri);
        consumer.sign(request);
        HttpResponse httpResponse = httpClient.execute(request);
        return httpResponse;
    }

}
