import java.awt.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;


public class UserView implements UserObserver{
    DefaultListModel<String> followListModel = new DefaultListModel<>();
    DefaultListModel<String> newsFeedModel;

    JFrame frame = new JFrame();
    JTextField followUserText = new JTextField(10);
    JButton followUserButton = new JButton("Follow User");
    JTextField postTweetText = new JTextField(10);
    JButton postTweetButton = new JButton("Post Tweet");
    JList<String> followListJList;
    JScrollPane followListScrollPane;
    JList<String> newsFeedJList;
    JScrollPane newsFeedPanel;
    

    public UserView(User user, JTree tree, DefaultTreeModel treeModel){
        frame.setTitle(user.toString());
        frame.setResizable(false);
        frame.setSize(500, 500);
        frame.setLayout(null);
        user.addObserver(this);

        followUserButton.setBounds(260, 10, 240, 50);
        followUserText.setBounds(10,10,240,50);

        postTweetButton.setBounds(260, 230, 240, 50);
        postTweetText.setBounds(10,230,240,50);

        for(String s:user.getFollowing()){
            followListModel.addElement(s);
        }
        followListJList = new JList<>(followListModel);
        followListScrollPane = new JScrollPane(followListJList);

        newsFeedModel = new DefaultListModel<>();
        for(String s:user.getNewsFeed()){
            newsFeedModel.addElement(s); 
        }
        newsFeedJList = new JList<>(newsFeedModel);
        newsFeedPanel = new JScrollPane(newsFeedJList);

        followListScrollPane.setBounds(10,70,480,150);
        newsFeedPanel.setBounds(10,290,480, 170);

        frame.add(followUserButton);
        frame.add(followUserText);
        frame.add(postTweetButton);
        frame.add(postTweetText);
        frame.add(followListScrollPane);
        frame.add(newsFeedPanel);
        frame.setVisible(true);



        followUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform an action when the button is clicked
                String text = followUserText.getText();
                DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
                loopThroughTree(rootNode,text,user, followListModel);
                followListJList.setModel(followListModel);;
            }
        });


        postTweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform an action when the button is clicked
                String tweet = postTweetText.getText();
                user.addTweet(tweet);
            }
        });

    }

    @Override
    public void notifyTweet(User user){
        newsFeedModel.addElement(user.getNewsFeed().get(user.getNewsFeed().size()-1)); 
        newsFeedJList.repaint();
    }
    

    private void loopThroughTree(DefaultMutableTreeNode node, String ID, User user, DefaultListModel followListModel) {
        // Check if the node is not null
        if (node != null) {
            if (node.getUserObject() instanceof User){
                String userID = node.getUserObject().toString();
                if (userID.equals(ID)){
                    if (!(user.getFollowing().contains(ID)) && !(ID.equals(user.getUserID()))){
                        user.addFollowing(ID, (User)node.getUserObject(), this); 
                        followListModel.addElement(userID);
                    }
                }
            } 
            Enumeration<TreeNode> children = node.children();
            while (children.hasMoreElements()) {
                DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) children.nextElement();
                loopThroughTree(childNode, ID, user, followListModel);
            }
        }
    }

}