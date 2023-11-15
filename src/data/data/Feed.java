package data.data;

import observe.Subject; 

import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * An observable feed of Tweets
 */
public class Feed extends Subject {
    private final TreeSet<Tweet> tweets = new TreeSet<>(Comparator.comparing(Tweet::getDate));

    public Tweet[] getTweets() {
        return tweets.toArray(Tweet[]::new);
    }

    public void setTweets(Tweet[] tweets) {
        this.tweets.clear();
        addTweets(tweets);
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
        notifyObservers();
    }

    public void addTweets(Tweet[] tweets) {
        Collections.addAll(this.tweets, tweets);
        notifyObservers();
    }

}
