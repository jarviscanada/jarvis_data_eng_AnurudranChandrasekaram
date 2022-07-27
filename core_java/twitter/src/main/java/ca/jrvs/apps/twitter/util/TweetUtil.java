package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetUtil {
    public static Tweet buildTweet(String text, Double lon, Double lat) {
        Tweet tweet = new Tweet();
        List<Double> c = new ArrayList<>();
        c.add(lon);
        c.add(lat);
        tweet.setText(text);
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(c);
        tweet.setCoordinates(coordinates);
        return tweet;
    }
}
