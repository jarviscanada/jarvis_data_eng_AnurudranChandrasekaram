package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TwitterDaoIntTest {

    private TwitterDao dao;

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        this.dao = new TwitterDao(httpHelper);

    }

    @Test
    public void create() throws Exception {
        String text = "hello";
        Tweet postTweet = TweetUtil.buildTweet(text, -1d, 1d );
        postTweet.setText(text);
        Tweet tweet = dao.create(postTweet);

        assertEquals(text, tweet.getText());
    }

    @Test
    public void findById () throws Exception {
        String text = "hi";
        Tweet postTweet = TweetUtil.buildTweet(text, -1d, 1d );
        postTweet.setText(text);
        Tweet tweet = dao.create(postTweet);
        String id = tweet.getId_str();
        Tweet tweet1 = dao.findById(id);
        assertEquals(id, tweet1.getId_str());
    }
}
