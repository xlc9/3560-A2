package gui.control;

import visitors.TweetGoodnessVisitor;

import javax.swing.*;

import data.data.DataManager;
import data.data.Tweet;
import data.data.addables.User;
import data.data.addables.UserGroup;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * panel holding all buttons
 */
public class ManagePanel extends JPanel {
    private final DataManager dataManager = DataManager.getInstance();
    private Runnable onRefreshListener;
    private Runnable onOpenUserViewListener;
    private User lastUpdated;

    public ManagePanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 45));
        setPreferredSize(new Dimension(475, 500));
        setBorder(BorderFactory.createTitledBorder("Manage"));

        //add user button
        JButton addUserButton = new JButton("Add user");
        addUserButton.setPreferredSize(new Dimension(225,50));
        addUserButton.addActionListener(actionEvent -> {
            JTextField userIdField = new JTextField();
            JTextField groupIdField = new JTextField();
            Object[] request = {
                    "User ID", userIdField,
                    "Group ID", groupIdField,
            };

            JOptionPane.showMessageDialog(null, request, "Add User", JOptionPane.QUESTION_MESSAGE);

            String userId = userIdField.getText();
            String groupId = groupIdField.getText();

            addUser(userId, groupId);
            
            if (onRefreshListener != null)
                onRefreshListener.run();
        });
        add(addUserButton);


        // Add group Button
        JButton addUserGroupButton = new JButton("Add usergroup");
        addUserGroupButton.setPreferredSize(new Dimension(225,50));
        addUserGroupButton.addActionListener(actionEvent -> {
            JTextField groupIdField = new JTextField();
            JTextField parentGroupIdField = new JTextField();
            Object[] request = {
                    "UserGroup ID", groupIdField,
                    "Parent UserGroup ID", parentGroupIdField,
            };

            JOptionPane.showMessageDialog(null, request, "Add UserGroup", JOptionPane.QUESTION_MESSAGE);

            String groupId = groupIdField.getText();
            String parentGroupId = parentGroupIdField.getText();

            addGroup(groupId, parentGroupId);

            if (onRefreshListener != null)
                onRefreshListener.run();
        });
        add(addUserGroupButton);


        //open user view button
        JButton openUserViewButton = new JButton("Open user view");
        openUserViewButton.setPreferredSize(new Dimension(455,50));
        openUserViewButton.addActionListener(actionEvent -> {
            if (onOpenUserViewListener != null)
                onOpenUserViewListener.run();
        });
        add(openUserViewButton);


        //checks percentage of good tweets button
        JButton checkGoodnessButton = new JButton("Check goodness");
        checkGoodnessButton.setPreferredSize(new Dimension(150,50));
        checkGoodnessButton.addActionListener(actionEvent -> {
            double totalGoodness = 0;
            int tweetCount = 0;

            TweetGoodnessVisitor tweetGoodnessVisitor = new TweetGoodnessVisitor();

            Tweet[] allTweets = dataManager.getAllTweets();
            for (Tweet tweet : allTweets) {
                boolean good = tweet.accept(tweetGoodnessVisitor);
                if (good)
                    totalGoodness++;

                tweetCount += 1;
            }

            if (tweetCount != 0)
                totalGoodness /= tweetCount;

            JOptionPane.showMessageDialog(null, "Total goodness: " + totalGoodness * 100 + "%");
        });
        add(checkGoodnessButton);

        JButton  validateUsers= new JButton("Validate Users");
        validateUsers.setPreferredSize(new Dimension(150,50));
        validateUsers.addActionListener(actionEvent -> {
            User[] allUsers = dataManager.getRootGroup().getUsers();
            UserGroup root = dataManager.getRootGroup();
            boolean running = true;
            for(int i = 0;i<allUsers.length && running == true;i++){
                User currUser = allUsers[i];
                if(root.validateUser(currUser) == false){
                    JOptionPane.showMessageDialog(null, "User ID: " + allUsers[i].getId()+ " is invalid");
                    running = false;
                }
            }
            if(running){
                JOptionPane.showMessageDialog(null, "All User IDs are valid");
            }

            
        });
        add(validateUsers);

         JButton  lastUpdatedUser= new JButton("Show Last User");
        lastUpdatedUser.setPreferredSize(new Dimension(150,50));
        lastUpdatedUser.addActionListener(actionEvent -> {
            int mostRecent = 0;
            User[] allUsers = dataManager.getRootGroup().getUsers();
           for(int i = 0; i < allUsers.length;i++){
                if(allUsers[mostRecent].getLastUpdateTime() < allUsers[i].getLastUpdateTime()){
                    mostRecent = i;
                }
           }
           lastUpdated = allUsers[mostRecent];

            JOptionPane.showMessageDialog(null, "Last Updated User is: " + lastUpdated.getId());
        });
        add(lastUpdatedUser);



        
        JButton userTotalButton = new JButton("Show user total");
        userTotalButton.setPreferredSize(new Dimension(150,50));
        userTotalButton.addActionListener(actionEvent -> {
            User[] allUsers = dataManager.getAllUsers();
            JOptionPane.showMessageDialog(null, "Total users: " + allUsers.length);
        });
        add(userTotalButton);

        JButton userGroupTotalButton = new JButton("Show group total");
        userGroupTotalButton.setPreferredSize(new Dimension(150,50));
        userGroupTotalButton.addActionListener(actionEvent -> {
            UserGroup[] allUserGroups = dataManager.getAllUserGroups();
            JOptionPane.showMessageDialog(null, "Total user groups: " + allUserGroups.length);
        });
        add(userGroupTotalButton);

        JButton tweetTotalButton = new JButton("Show tweet total");
        tweetTotalButton.setPreferredSize(new Dimension(150,50));
        tweetTotalButton.addActionListener(actionEvent -> {
            Tweet[] allTweets = dataManager.getAllTweets();
            JOptionPane.showMessageDialog(null, "Total tweets: " + allTweets.length);
        });
        add(tweetTotalButton);

       
    }

    private void addUser(String userId, String groupId) {
        if (userId.isEmpty())
            return;
        User user = new User(userId);
        UserGroup group;

        if (groupId.isEmpty()) {
            group = dataManager.getRootGroup();
        } else {
            group = dataManager.findGroupById(groupId);
        }

        if (group != null) {
            group.addUser(user);
            if(lastUpdated == null)
            {   
                lastUpdated = user;
            }
        }
    }

    private void addGroup(String groupId, String parentGroupId) {
        if (groupId.isEmpty())
            return;

        UserGroup group = new UserGroup(groupId);
        UserGroup parentGroup;

        if (parentGroupId.isEmpty()) {
            parentGroup = dataManager.getRootGroup();
        } else {
            parentGroup = dataManager.findGroupById(parentGroupId);
        }

        if (parentGroup != null) {
            parentGroup.addSubgroup(group);
        }
    }

    public void setOnRefreshListener(Runnable onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setOnOpenUserViewListener(Runnable onOpenUserViewListener) {
        this.onOpenUserViewListener = onOpenUserViewListener;
    }
}
