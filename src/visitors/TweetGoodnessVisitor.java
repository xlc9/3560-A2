package visitors;

import java.util.Arrays;

import data.data.Tweet;

/**
 * A concrete Visitor that checks if a Tweet is "good" or not.
 */
public class TweetGoodnessVisitor implements TweetVisitor {
   
    static final String[] GOOD_WORDS = {"good", "happy", "excellent", "nice"};

    @Override
    public boolean visit(Tweet tweet) {
        String content = tweet.getContent();
        String[] words = content.split(" ");

        if (words.length != 0) {
            for (String word : words) {
                if (Arrays.asList(GOOD_WORDS).contains(word.toLowerCase())) {
                    return true;
                }
            }
        }

        return false;
    }
}
