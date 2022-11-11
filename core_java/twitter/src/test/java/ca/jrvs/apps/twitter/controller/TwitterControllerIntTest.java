package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TwitterControllerIntTest {
    private TwitterController controller;

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        TwitterDao dao = new TwitterDao(httpHelper);
        TwitterService service = new TwitterService(dao);
        this.controller = new TwitterController(service);
    }

    @Test
    public void postTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String[] args = new String[]{"post", "hello", "0:0"};
        Tweet tweet = controller.postTweet(args);
        assertEquals("hello", tweet.getText());
    }

    @Test
    public void showTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String[] args = new String[]{"post", "hi", "0:0"};
        Tweet tweet = controller.postTweet(args);
        String id = tweet.getId_str();
        String[] fields = new String[]{};
        String[] args1 = new String[]{"show", id};
        Tweet tweet1 = controller.showTweet(args1);
        assertEquals("hi", tweet1.getText());
    }

    @Test
    public void deleteTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        String[] args = new String[]{"post", "hi", "0:0"};
        Tweet tweet = controller.postTweet(args);
        String id = tweet.getId_str();
        String[] args1 = new String[]{"delete", id};
        List<Tweet> tweets = controller.deleteTweet(args1);
    }
}
