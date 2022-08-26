package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TwitterService implements Service{

    private CrdDao dao;

    @Autowired
    public TwitterService(CrdDao dao) {this.dao = dao;}
    /**
     * @param tweet tweet to be created
     * @return
     */
    @Override
    public Tweet postTweet(Tweet tweet) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        //Business logic:
        //e.g. text length, lat/lon range, id format
        validatePostTweet(tweet);

        //create tweet via dao
        return (Tweet) dao.create(tweet);
    }

    private void validatePostTweet(Tweet tweet) {
        Double lon = tweet.getCoordinates().getCoordinates().get(0);
        Double lan = tweet.getCoordinates().getCoordinates().get(1);
        if (tweet.getText().length() > 140) {
            throw new IllegalArgumentException("text exceeds more than 140 characters");
        } if ( lon > 180 || lon < -180 || lan > 90 || lan < -90 ) {
            throw new IllegalArgumentException("Coordinates out of range");
        }
    }

    /**
     * @param id     tweet id
     * @param fields set fields not in the list to null
     * @return
     */
    @Override
    public Tweet showTweet(String id, String[] fields) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        //Business logic:
        //e.g.  id format
        if (!validateId(id)) {
            throw new IllegalArgumentException("Id has non-numeric values");
        }
        //create tweet via dao
        Tweet tweet =  (Tweet) dao.findById(id);
        validateField(tweet, fields);
        return tweet;
    }

    private void validateField(Tweet tweet, String[] fields) {
        if (fields != null) {
            ArrayList<String> s = new ArrayList<String>();
            s.add("created_at");
            s.add("id");
            s.add("id_str");
            s.add("text");
            s.add("entities");
            s.add("coordinates");
            s.add("retweet_count");
            s.add("favorite_count");
            s.add("favorited");
            s.add("retweeted");
            for (String field : fields) {
                if (!s.contains(field)) {
                    switch (field) {
                        case "created_at":
                            tweet.setCreated_at(null);
                            break;
                        case "id":
                            tweet.setId(null);
                            break;
                        case "id_str":
                            tweet.setId_str(null);
                            break;
                        case "text":
                            tweet.setText(null);
                            break;
                        case "entities":
                            tweet.setEntities(null);
                            break;
                        case "coordinates":
                            tweet.setCoordinates(null);
                            break;
                        case "retweet_count":
                            tweet.setRetweet_count(null);
                            break;
                        case "favorite_count":
                            tweet.setFavorite_count(null);
                            break;
                        case "favorited":
                            tweet.setFavorited(null);
                            break;
                        case "retweeted":
                            tweet.setRetweeted(null);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid field");
                    }
                }
            }
        }
    }

    private boolean validateId(String id) {
        return Pattern.matches("^[0-9]+$", id);
    }

    /**
     * @param ids tweet IDs which will be deleted
     * @return
     */
    @Override
    public List<Tweet> deleteTweets(String[] ids) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, OAuthCommunicationException {
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (String id : ids) {
            if (!validateId(id)) {
                throw new IllegalArgumentException("Invalid id");
            }
            Tweet tweet = (Tweet) dao.deleteById(id);
            tweets.add(tweet);
        }
        return tweets;
    }
}
