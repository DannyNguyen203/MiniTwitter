import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;


public class User implements TreeNode{
    private String userID;
    private ArrayList<String> following;
    private ArrayList<String> followers;
    private ArrayList<String> newsFeed;
    private ArrayList<UserObserver> observers = new ArrayList<UserObserver>();
    private DefaultMutableTreeNode node;
    private long creationTime;
    private long lastUpdateTime;


    User (String ID){
        this.userID = ID;
        this.followers = new ArrayList<String>();
        this.following = new ArrayList<String>();
        this.newsFeed = new ArrayList<String>();
        this.node = new DefaultMutableTreeNode(ID);
        this.creationTime = System.currentTimeMillis();
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public void notify(String tweet){
        newsFeed.add(tweet);
    }


    @Override
    public String toString() {
        return this.userID; // Display the username in the JTree
    }

    public String getUserID(){
        return this.userID;
    }

    public long getCreationTime(){
        return this.creationTime;
    }

    public long getLastUpdateTime(){
        return this.lastUpdateTime;
    }


    public ArrayList<String> getFollowing (){
        return this.following;
    }

    public ArrayList<String> getNewsFeed (){
        return this.newsFeed;
    }

    public void addFollowing (String ID, User otherUser, UserObserver user){
        if (!(this.following.contains(ID)) && !(ID.equals(this.getUserID()))){
            this.following.add(ID);
            otherUser.addObserver(user);
        }
    }

     public void addTweet(String tweet){
        this.newsFeed.add(this.getUserID() + " : " + tweet);
        notifyObservers(); 
    }


    public void addObserver(UserObserver observer) {
        if (!observers.contains(observer)){
                observers.add(observer);
        }
    }

    public void notifyObservers(){
        for (UserObserver userObserver : observers){
            userObserver.notifyTweet(this);
        }
    }

    @Override
    public void addChild(TreeNode child) {
        node.add((DefaultMutableTreeNode) child);
    }

    @Override
    public void removeChild(TreeNode child) {
        // Leaves do not have children
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }
    
}