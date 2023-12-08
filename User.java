package MiniTwitterApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

class User implements UserVisitor {
    private String id;
    private ArrayList<User> followers;
    private ArrayList<User> followings;
    private ArrayList<String> tweets;
    private static Map<String, User> allUsers = new HashMap<>(); // Static field to hold all users
    private List<UserFrame> userFrames;
    private List<Observer> observers = new ArrayList<>();
    private static int totalMessages = 0;
    private static int totalpos = 0;
    private long creationTime;
    private long lastUpdateTime;
    
    public User(String id) {
        this.id = id;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.userFrames = new ArrayList<>();
        this.creationTime = System.currentTimeMillis();
        this.lastUpdateTime = System.currentTimeMillis();

        allUsers.put(id, this);
       
    }
    public long getLastUpdatedTime() {
        return lastUpdateTime;
    }
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    
    public String getId() {
        return id;
    }
    
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    private void notifyObservers(String tweet) {
        for (Observer observer : observers) {
            observer.updateTweetsList(tweet);
        }
    }
    
    public static User getUserById(String userId) {
        return allUsers.get(userId);
    }
    
    public ArrayList<User> getFollowers() {
        return followers;
    }

    public ArrayList<User> getFollowings() {
        return followings;
    }

    public List<String> getTweets() {
        return tweets;
    }

    public void addFollower(User follower) {
        followers.add(follower);
        follower.followings.add(this); // Update the followed user's followings list
    }

    public void postTweet(String tweet) {
        lastUpdateTime = System.currentTimeMillis();
    	String tweeted = this.id + ": " + tweet;
        // Update the followers' news feed
        for (User follower : followers) {
            follower.updateNewsFeed(this, tweeted);
            follower.notifyObservers(tweeted); // Notify followers with the latest tweet
        }
        tweets.add(tweeted);
        totalMessages++;
        if(tweet.toLowerCase().contains("like")){
        	totalpos++;
        }

    }

    public int getTweetCount() {
        return tweets.size();
    }

    
    public void addUserFrame(UserFrame userFrame) {
        userFrames.add(userFrame);
    }
    
    public void updateNewsFeed(User user, String tweet) {
        tweets.add(user + ": " + tweet);
    }
    public void updateTweetsList(String tweetText) {
        for (UserFrame userFrame : userFrames) {
            userFrame.updateTweetsList(tweetText);
        }
    }

    public List<String> getNewsFeed() {
        return tweets;
    }
    @Override
    public String toString() {
        return id;
    }
    public static int getTotalMessages() {
        return totalMessages;
    }
    public static int getTotalpos() {
        return totalpos;
    }
    public long getCreationTime() {
        return creationTime;
    }
    private User findLastUpdatedUser(DefaultMutableTreeNode node) {
        long maxUpdateTime = Long.MIN_VALUE;
        User lastUpdatedUser = null;

        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
            if (childNode.getUserObject() instanceof UserGroup) {
                User foundInGroup = findLastUpdatedUser(childNode);
                if (foundInGroup != null && foundInGroup.getLastUpdateTime() > maxUpdateTime) {
                    maxUpdateTime = foundInGroup.getLastUpdateTime();
                    lastUpdatedUser = foundInGroup;
                }
            } else if (childNode.getUserObject() instanceof User) {
                User user = (User) childNode.getUserObject();
                if (user.getLastUpdateTime() > maxUpdateTime) {
                    maxUpdateTime = user.getLastUpdateTime();
                    lastUpdatedUser = user;
                }
            }
        }

        return lastUpdatedUser;
    }
    
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }
}
