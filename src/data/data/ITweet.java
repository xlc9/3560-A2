package data.data;

import java.util.Date;

import data.data.addables.User;

/**
 * A Tweet that holds text, a timestamp, and the posting user.
 */
public abstract class ITweet {
    
    private String content;//what it says

    private Date date;//when it was said

    private User user;//who said it

    public ITweet(String content, User user) {
        this.content = content;
        this.user = user;
        this.date = new Date();
    }

    public ITweet(String content, User user, Date date) {
        this.content = content;
        this.user = user;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return A formatted version of this Tweet with relavent metadata.
     */
    public abstract String getFormattedContent();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
