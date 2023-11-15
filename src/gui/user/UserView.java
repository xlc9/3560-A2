package gui.user;

import javax.swing.*;

import data.data.addables.User;

import java.awt.*;

/**
 * The user details page.
 */
public class UserView extends JFrame {
    private final User user;

    public UserView(User user) {
        this.user = user;

        setResizable(false);
        setSize(400, 500);
        setTitle(user.getId());

        FeedPanel feedPanel = new FeedPanel(user);
        FollowingPanel followingPanel = new FollowingPanel(user);
        followingPanel.setOnRefreshListener(feedPanel::refresh);

        add(followingPanel, BorderLayout.NORTH);
        add(feedPanel);

    }

    public void present() {
        setVisible(true);
    }

    public User getUser() {
        return user;
    }
}
