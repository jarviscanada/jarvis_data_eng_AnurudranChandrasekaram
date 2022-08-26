package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

public class TwitterController implements Controller{

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service) { this.service = service;}

    /**
     * @param args
     * @return
     */
    @Override
    public Tweet postTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        String tweet_text = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        if (coordArray.length != 2 || StringUtils.isEmpty(tweet_text)) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        Double lat;
        Double lon;
        try {
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"", e);
        }
        Tweet postTweet = TweetUtil.buildTweet(tweet_text, lon, lat);
        return service.postTweet(postTweet);
    }

    /**
     * @param args
     * @return
     */
    @Override
    public Tweet showTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        if (args.length != 3 && args.length != 2) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp show \"tweet_id\" \"[field1,fields2]\"");
        }
        String[] fields = null;
        if (args.length == 3) {
            fields = args[2].split(COMMA);
        }
        String tweet_id = args[1];
        return service.showTweet(tweet_id, fields);
    }

    /**
     * @param args
     * @return
     */
    @Override
    public List<Tweet> deleteTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        if (args.length != 2) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"[id1,id2,...]\"");
        }
        String[] tweet_ids = args[1].split(COMMA);
        return service.deleteTweets(tweet_ids);
    }
}
