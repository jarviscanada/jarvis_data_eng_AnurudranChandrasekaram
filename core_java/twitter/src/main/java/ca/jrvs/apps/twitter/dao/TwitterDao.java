package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.transform.sax.SAXResult;
import java.io.IOException;
import java.net.URI;

public class TwitterDao implements CrdDao<Tweet, String> {

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {this.httpHelper = httpHelper; }


    @Override
    public Tweet create(Tweet entity) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        // Create a uri request
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        URI uri = URI.create(API_BASE_URI + POST_PATH + QUERY_SYM + "status=" + percentEscaper.escape(entity.getText()));
        HttpResponse httpResponse = httpHelper.httpPost(uri);
        return parseResponse(httpResponse, HTTP_OK);
    }

    protected Tweet parseResponse(HttpResponse response, int expectedResponse) {
        Tweet tweet = null;
        int status = response.getStatusLine().getStatusCode();
        // Check if the status code is 200
        if (expectedResponse != status) {
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no entity");
            } throw new RuntimeException("Unexpected HTTP Status:" + status);
        }
        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create tweet obj",e);
        }
        return tweet;
    }

    @Override
    public Tweet findById(String s) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        // Create a uri request
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        URI uri = URI.create(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + percentEscaper.escape(s));
        HttpResponse httpResponse = httpHelper.httpGet(uri);
        return parseResponse(httpResponse, HTTP_OK);
    }

    @Override
    public Tweet deleteById(String s) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        // Create a uri request
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        URI uri = URI.create(API_BASE_URI + DELETE_PATH + "/" + percentEscaper.escape(s) + ".json");
        HttpResponse httpResponse = httpHelper.httpPost(uri);
        return parseResponse(httpResponse, HTTP_OK);
    }
}
