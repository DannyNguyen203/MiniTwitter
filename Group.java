import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class Group implements TreeNode{
    private String groupID;
    private ArrayList<String> members;
    private DefaultMutableTreeNode node;
    private java.util.List<TreeNode> children;
    private long creationTime;

    Group (String ID){
        this.groupID = ID;
        this.node = new DefaultMutableTreeNode(ID);
        this.children = new java.util.ArrayList<>();
        this.creationTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return groupID; // Display the username in the JTree
    }

    public void addMember(String groupMember){
        members.add(groupMember);
    }

    public long getCreationTime(){
        return this.creationTime;
    }

    public ArrayList<String> getMembers(){
        ArrayList<String> membersCopy = new ArrayList<>();
        return new ArrayList<>(membersCopy);
    }

    public String getGroupName(){
        return groupID;
    }


    @Override
    public void addChild(TreeNode child) {
        children.add(child);
        node.add(((Group) child).getNode());
    }

    @Override
    public void removeChild(TreeNode child) {
        children.remove(child);
        node.remove(((Group) child).getNode());
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }

}
