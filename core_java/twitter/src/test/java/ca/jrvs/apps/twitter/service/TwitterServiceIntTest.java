package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TwitterServiceIntTest {

    private TwitterService service;

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        TwitterDao dao = new TwitterDao(httpHelper);
        this.service = new TwitterService(dao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void postFailTweet() throws Exception {
        String text = "helfdskjldskfjdslkfjdslkjsdflkjdsflksdfjlkdsjfldskfjlkdsfjldskfjldskfjlsdkfjsdlkjsdlkfjsdflkjsdflkjsdflkjsflkslkdjlsfsjfdjldjlkfjlkjldskjflksdjflksdjfldskjsdlkjslkfjdlkfjlsdkjflsdkjflskdjflo";
        Tweet postTweet = TweetUtil.buildTweet(text, -1d, 1d );
        postTweet.setText(text);
        Tweet tweet = service.postTweet(postTweet);

        assertEquals(text, tweet.getText());
    }

    @Test(expected = IllegalArgumentException.class)
    public void postFailCoordinateTweet() throws Exception {
        String text = "hello";
        Tweet postTweet = TweetUtil.buildTweet(text, -100d, 200d );
        postTweet.setText(text);
        Tweet tweet = service.postTweet(postTweet);

        assertEquals(text, tweet.getText());
    }

    @Test
    public void postValidTweet() throws Exception {
        String text = "hello";
        Tweet postTweet = TweetUtil.buildTweet(text, -1d, 2d );
        postTweet.setText(text);
        Tweet tweet = service.postTweet(postTweet);

        assertEquals(text, tweet.getText());
    }
}
