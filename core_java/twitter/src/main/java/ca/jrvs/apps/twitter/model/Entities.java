package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)


public class Entities {
    @JsonProperty("hashtags")
    private Hashtag[] hashtags;

    @JsonProperty("userMentions")
    private UserMention[] userMentions;

    @JsonProperty("hashtags")
    public Hashtag[] getHashtags() {
        return hashtags;
    }

    @JsonProperty("hashtags")
    public void setHashtags(Hashtag[] hashtags) {
        this.hashtags = hashtags;
    }

    @JsonProperty("userMentions")
    public UserMention[] getUserMentions() {
        return userMentions;
    }

    @JsonProperty("userMentions")
    public void setUserMentions(UserMention[] userMentions) {
        this.userMentions = userMentions;
    }

}
