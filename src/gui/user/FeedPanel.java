package gui.user;

import javax.swing.*;

import data.data.Feed;
import data.data.Tweet;
import data.data.addables.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FeedPanel extends JPanel {
    private final User user;
    private final Set<User> followingWatching = new HashSet<>();

    public FeedPanel(User user) {
        this.user = user;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Current Feed"));

        buildUI();
    }

   
    /**
     * Register observers on all following user feeds so that we can refresh if they are changed.
     */
    private void updateObservers() {
        

        for (User following : user.getFollowing()) {
            Feed feed = following.getFeed();
            feed.addObserver(this::refresh);
            followingWatching.add(following);
        }

        // Watch this user's feed as well.
        Feed feed = user.getFeed();
        feed.addObserver(this::refresh);
        followingWatching.add(user);
    }

    private String[] getUserFeedContent() {
        Feed feed = new Feed();

        // Add our own tweets.
        feed.addTweets(user.getFeed().getTweets());

        // Add tweets from our followings.
        for (User following : user.getFollowing()) {
            feed.addTweets(following.getFeed().getTweets());
        }

        return Arrays.stream(feed.getTweets()).map(Tweet::getFormattedContent).toArray(String[]::new);
    }

    private void buildUI() {
        removeAll();

        String[] feed = getUserFeedContent();
        updateObservers();

        JList<String> list = new JList<>(feed);
        JScrollPane listScroller = new JScrollPane(list);
        add(listScroller);

        JLabel lastUpdate = new JLabel();
        lastUpdate.setText("Last Update (in milliseconds): "+ user.getLastUpdateTime());
        add(lastUpdate);

        JButton addTweetButton = new JButton("Add Tweet");
        addTweetButton.addActionListener(actionEvent -> {
            String content = JOptionPane.showInputDialog("Content");
            Tweet tweet = new Tweet(content, user);
            user.getFeed().addTweet(tweet);
        });
        add(addTweetButton);

    }

    public void refresh() {
        buildUI();
        validate();
        repaint();
    }

    public User getUser() {
        return user;
    }
}
