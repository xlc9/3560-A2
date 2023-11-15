package data.data;

import java.util.*;

import data.data.addables.User;
import data.data.addables.UserGroup;

/**
 * A concrete singleton implementation of the DataManager class
 */
public class DataManager {
    private static DataManager instance;

    private final UserGroup rootGroup = new UserGroup("Root");

    private DataManager() {
    }

    /**
     * Gets the singleton instance.
     *
     * @return the instance if it exists. Otherwise, create a new instance.
     */
    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    
    public UserGroup getRootGroup() {
        return rootGroup;
    }

    private UserGroup recursivelyFindGroupById(String id, UserGroup root) {
        if (root.getId().equals(id)) {
            return root;
        }

        for (UserGroup subgroup : root.getSubgroups()) {
            UserGroup result = recursivelyFindGroupById(id, subgroup);
            if (result != null)
                return result;
        }

        return null;
    }

    
    public UserGroup findGroupById(String id) {
        UserGroup rootGroup = getRootGroup();
        return recursivelyFindGroupById(id, rootGroup);
    }

    private User recursivelyFindUserById(String id, UserGroup root) {
        for (User user : root.getUsers()) {
            if (user.getId().equals(id))
                return user;
        }

        for (UserGroup subgroup : root.getSubgroups()) {
            User result = recursivelyFindUserById(id, subgroup);
            if (result != null)
                return result;
        }

        return null;
    }

    public User findUserById(String id) {
        UserGroup rootGroup = getRootGroup();
        return recursivelyFindUserById(id, rootGroup);
    }

    private User[] recursivelyGetAllUsers(UserGroup group) {
        List<User> users = new ArrayList<>();

        User[] groupUsers = group.getUsers();

        if (groupUsers.length > 0)
            Collections.addAll(users, groupUsers);

        for (UserGroup subgroup : group.getSubgroups()) {
            Collections.addAll(users, recursivelyGetAllUsers(subgroup));
        }

        return users.toArray(User[]::new);
    }

    public User[] getAllUsers() {
        return recursivelyGetAllUsers(rootGroup);
    }

    private UserGroup[] recursivelyGetAllUserGroups(UserGroup group) {
        List<UserGroup> groups = new ArrayList<>();
        groups.add(group);

        UserGroup[] subgroups = group.getSubgroups();

        for (UserGroup subgroup : subgroups) {
            Collections.addAll(groups, recursivelyGetAllUserGroups(subgroup));
        }

        return groups.toArray(UserGroup[]::new);
    }

    public UserGroup[] getAllUserGroups() {
        return recursivelyGetAllUserGroups(rootGroup);
    }

    public Tweet[] getAllTweets() {
        List<Tweet> tweets = new ArrayList<>();

        User[] allUsers = getAllUsers();
        for (User user : allUsers) {
            Collections.addAll(tweets, user.getFeed().getTweets());
        }

        return tweets.toArray(Tweet[]::new);
    }
}
