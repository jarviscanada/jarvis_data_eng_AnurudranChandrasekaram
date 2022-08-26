package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetUtil;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)

public class TwitterControllerUnitTest {

    @Mock
    TwitterService service;

    @InjectMocks
    TwitterController controller;

    @Test
    public void postTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        when(service.postTweet(any())).thenReturn(new Tweet());
        String[] args = {"post", "test", "0:0"};
        Tweet tweet = controller.postTweet(args);
        assertNotNull(tweet);
    }

    @Test
    public void showTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        when(service.showTweet(any(), any())).thenReturn(new Tweet());
        String[] args = {"show", "132465798123456789"};
        Tweet tweet = controller.showTweet(args);
        assertNotNull(tweet);
    }

    @Test
    public void deleteTweet() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        List<Tweet> tweets = null;
        when(service.deleteTweets(any())).thenReturn(tweets);
        String[] args = {"delete", "132465798123456789"};
        List<Tweet> tweet = controller.deleteTweet(args);
    }
}
