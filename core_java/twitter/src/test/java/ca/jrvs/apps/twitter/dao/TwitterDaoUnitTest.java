package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    @Test
    public void showTweet() throws Exception {
        //test failed request
        String id = "";
        //exception is expected here
        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.findById(id);
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test happy path
        //however, we dont want to call parseResponseBody
        // we will make a spyDao which can fake parseResponseBody return value
        String tweetJsonStr = "{\n"
                +"  \"created_at\":\"Mon Feb 18 21:24:39 + 0000 2019\", \n"
                +"  \"id\":1097607853932564480,\n"
                +"  \"id_str\":\"1097607853932564480\",\n"
                +"  \"text\":\"test with loc223\",\n"
                +"  \"entities\":{\n"
                +"      \"hastags\":[],"
                +"      \"user_mentions\":[]"
                +"  },\n"
                +"  \"coordinates\":null,"
                +"  \"retweet_count\":0,\n"
                +"  \"favorite_count\":0,\n"
                +"  \"favorited\":false,\n"
                +"  \"retweeted\":false\n"
                +"}";

        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponse(any(), anyInt());
        Tweet tweet = spyDao.findById("1097607853932564480");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
        System.out.println(tweet.getId());
        assertEquals(expectedTweet.getId(), tweet.getId());
    }

    @Test
    public void postTweet() throws Exception {
        //test failed request
        String hashtag = "#abc";
        String text = "Hi";
        Double lat = 1d;
        Double lon = -1d;
        //exception is expected here
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.create(TweetUtil.buildTweet(text, lon, lat));
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test happy path
        //however, we dont want to call parseResponseBody
        // we will make a spyDao which can fake parseResponseBody return value
        String tweetJsonStr = "{\n"
                +"  \"created_at\":\"Mon Feb 18 21:24:39 + 0000 2019\", \n"
                +"  \"id\":1097607853932564480,\n"
                +"  \"id_str\":\"1097607853932564480\",\n"
                +"  \"text\":\"test with loc223\",\n"
                +"  \"entities\":{\n"
                +"      \"hastags\":[],"
                +"      \"user_mentions\":[]"
                +"  },\n"
                +"  \"coordinates\":null,"
                +"  \"retweet_count\":0,\n"
                +"  \"favorite_count\":0,\n"
                +"  \"favorited\":false,\n"
                +"  \"retweeted\":false\n"
                +"}";

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponse(any(), anyInt());
        Tweet tweet = spyDao.create(TweetUtil.buildTweet(text, lon, lat));
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void deleteTweet() throws Exception {
        //test failed request
        String id = "23";
        //exception is expected here
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.deleteById(id);
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test happy path
        //however, we dont want to call parseResponseBody
        // we will make a spyDao which can fake parseResponseBody return value
        String tweetJsonStr = "{\n"
                +"  \"created_at\":\"Mon Feb 18 21:24:39 + 0000 2019\", \n"
                +"  \"id\":1097607853932564480,\n"
                +"  \"id_str\":\"1097607853932564480\",\n"
                +"  \"text\":\"test with loc223\",\n"
                +"  \"entities\":{\n"
                +"      \"hastags\":[],"
                +"      \"user_mentions\":[]"
                +"  },\n"
                +"  \"coordinates\":null,"
                +"  \"retweet_count\":0,\n"
                +"  \"favorite_count\":0,\n"
                +"  \"favorited\":false,\n"
                +"  \"retweeted\":false\n"
                +"}";

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        //mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponse(any(), anyInt());
        Tweet tweet = spyDao.deleteById("1097607853932564480");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }
}
