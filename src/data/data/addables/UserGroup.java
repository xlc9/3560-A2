package data.data.addables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds the group information.
 */
public class UserGroup extends UUG{
    private final Set<User> users = new HashSet<>();
    private final Set<UserGroup> subgroups = new HashSet<>();

    public UserGroup(String id) {
       super(id);
    }

    public UserGroup(String id, User[] users) {
        super(id);
        setUsers(users);
    }

    public UserGroup(String id, User[] users, UserGroup[] subgroups) {
        super(id);
        setUsers(users);
        setSubgroups(subgroups);
    }

    public boolean validateUser(User user){
        if(users.contains(user) || user.getId().contains(" ")){
            return false;
        }
        else{
            return true;
        }
    }

    public User[] getUsers() {
        return users.toArray(User[]::new);
    }

    public void setUsers(User[] users) {
        this.users.clear();
        Collections.addAll(this.users, users);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public UserGroup[] getSubgroups() {
        return subgroups.toArray(UserGroup[]::new);
    }

    public void setSubgroups(UserGroup[] subgroups) {
        this.subgroups.clear();
        Collections.addAll(this.subgroups, subgroups);
    }

    public void addSubgroup(UserGroup subgroup) {
        this.subgroups.add(subgroup);
    }
}
