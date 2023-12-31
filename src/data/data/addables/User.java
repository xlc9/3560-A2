package data.data.addables;

import java.util.HashSet;
import java.util.Set;

import data.data.Feed;

/**
 * user info and is child of UUG
 */
public class User extends UUG{
    private final Feed feed = new Feed();
    private Set<User> followers = new HashSet<>();
    private Set<User> following = new HashSet<>();
    private long lastUpdateTime;

    public User(String id) {
       super(id);
       lastUpdateTime = feed.getLastUpdateTime();
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public void addFollower(User user) {
        this.followers.add(user);
    }

    public void removeFollower(User user) {
        this.followers.remove(user);
    }

    public User[] getFollowing() {
        return following.toArray(User[]::new);
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public void addFollowing(User user) {
        this.following.add(user);

        // Add ourselves as a follower to the target user.
        user.addFollower(this);
        if(user.getFeed().getLastUpdateTime() > this.lastUpdateTime){
            feed.setLastUpdateTime(user.getFeed().getLastUpdateTime());
        }
    }

    public long getLastUpdateTime(){
        lastUpdateTime = feed.getLastUpdateTime();
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Feed getFeed() {
        return feed;
    }
}
