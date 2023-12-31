import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;


public class AdminControlPanel {

    private static AdminControlPanel instance;
    public static int numUsers;
    public static int numGroups;
    public static int numMessages;
    public static int numPositivePercentage;

    // Singleton PAttern for AdminControlPanel GUI
    public static AdminControlPanel getInstance() {
        if (instance == null) {
            instance = new AdminControlPanel();
        }
        return instance;
    }




    public AdminControlPanel() {

        ArrayList<String> uniqueIDs = new ArrayList<String>();

        // frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(700, 500);
        frame.setLayout(null);
        frame.setTitle("Admin Control Panel");

        // frame components
        JButton addUserButton = new JButton("Add User");
        JButton addGroupButton = new JButton("Add Group");
        JButton openUserView = new JButton("Open User View");
        JButton validateUsersButton = new JButton("Validate Users");
        JButton findLastUpdatedUser = new JButton("Find Last Updated User");
        JButton showUserTotal = new JButton("Show User Total");
        JButton showGroupTotal = new JButton("Show Group Total");
        JButton showMessagesTotal = new JButton("Show Messages Total");
        JButton showPostitivePercentage = new JButton("Show Positive Percentage");

        JTextField addUserTextField = new JTextField(10);
        JTextField addGroupTextField = new JTextField(10);

        DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode(new Group("Root")));
        JTree tree = new JTree(treeModel);
        JScrollPane scrollPane = new JScrollPane(tree);


        scrollPane.setBounds(10,10,340,480);

        // Group and User GUI
        addGroupButton.setBounds(525, 70, 150, 50);
        addGroupTextField.setBounds(360,70,150,50);
        addUserButton.setBounds(525, 10, 150, 50);
        addUserTextField.setBounds(360,10,150,50);

        openUserView.setBounds(360, 130, 320, 50);
        validateUsersButton.setBounds(360, 190, 320, 50);
        findLastUpdatedUser.setBounds(360, 250, 320, 50);


        // Statistics GUI
        showPostitivePercentage.setBounds(525, 410, 150, 50);
        showMessagesTotal.setBounds(360,410,150,50);
        showGroupTotal.setBounds(525, 350, 150, 50);
        showUserTotal.setBounds(360,350,150,50);


        frame.add(addUserTextField);
        frame.add(addUserButton);
        frame.add(addGroupTextField);
        frame.add(addGroupButton);
        frame.add(openUserView);
        frame.add(validateUsersButton);
        frame.add(findLastUpdatedUser);
        frame.add(showGroupTotal);
        frame.add(showMessagesTotal);
        frame.add(showPostitivePercentage);
        frame.add(showUserTotal);
        frame.add(scrollPane);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform an action when the button is clicked
                String text = addUserTextField.getText();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (!uniqueIDs.contains(text) && selectedNode.getUserObject() instanceof Group ){
                    numUsers++;
                    uniqueIDs.add(text);
                    User newUser = new User(text);
                    DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(newUser);
                    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
                    selectedNode.add(userNode);
                    treeModel.nodeStructureChanged(rootNode);
                    treeModel.reload();
                    tree.repaint();
                }
            }
        });

        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform an action when the button is clicked
                String text = addGroupTextField.getText();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (!uniqueIDs.contains(text) && selectedNode.getUserObject() instanceof Group ){
                    numGroups++;
                    uniqueIDs.add(text);
                    Group newGroup = new Group(text);
                    DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(newGroup);
                    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
                    selectedNode.add(groupNode);
                    treeModel.nodeStructureChanged(rootNode);
                    treeModel.reload();
                    tree.repaint();
                }
            }
        });

        showUserTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, numUsers + " users");
            }
        });

        showGroupTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, numGroups + " groups");
            }
        });

        showMessagesTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform an action when the button is clicked
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode.getUserObject() instanceof User ){
                    int numMessages = ((User)selectedNode.getUserObject()).getNewsFeed().size();
                    JOptionPane.showMessageDialog(null, numMessages);
                }
                
            }
        });

        showPostitivePercentage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, numPositivePercentage);
            }
        });


        openUserView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                User user = (User) selectedNode.getUserObject();
                UserView userView = new UserView(user,tree,treeModel);
            }
        });


        validateUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList uniqueIDs = new ArrayList<String>();
                boolean haveDuplicate = loopThroughTree((DefaultMutableTreeNode) treeModel.getRoot(), uniqueIDs);
                JOptionPane.showMessageDialog(null, haveDuplicate);
            }

            private boolean loopThroughTree(DefaultMutableTreeNode node, ArrayList IDList) {
            // Check if the node is not null
                if (node != null) {
                    String userID = node.getUserObject().toString();
                    if (!IDList.contains(userID)){
                        IDList.add(userID);
                    } else {
                        return false;
                    }
                    
                    Enumeration<TreeNode> children = node.children();
                    while (children.hasMoreElements()) {
                        DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) children.nextElement();
                        loopThroughTree(childNode, IDList);
                    }
                }
                return true;
            }
        });

        findLastUpdatedUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String lastUpdatedUser = findLatestUpdatedUserRecursively((DefaultMutableTreeNode) treeModel.getRoot()).toString();
                JOptionPane.showMessageDialog(null, lastUpdatedUser);
            }

            private static User findLatestUpdatedUserRecursively(DefaultMutableTreeNode node) {
                User latestUpdatedUser = null;
        
                if (node.getUserObject() instanceof User) {
                    User currentUser = (User) node.getUserObject();
                    if (latestUpdatedUser == null || currentUser.getLastUpdateTime() > latestUpdatedUser.getLastUpdateTime()) {
                        latestUpdatedUser = currentUser;
                    }
                }
        
                for (int i = 0; i < node.getChildCount(); i++) {
                    TreeNode child = node.getChildAt(i);
                    if (child instanceof DefaultMutableTreeNode) {
                        User childLatestUpdatedUser = findLatestUpdatedUserRecursively((DefaultMutableTreeNode) child);
                        if (latestUpdatedUser == null || (childLatestUpdatedUser != null && childLatestUpdatedUser.getLastUpdateTime() > latestUpdatedUser.getLastUpdateTime())) {
                            latestUpdatedUser = childLatestUpdatedUser;
                        }
                    }
                }
        
                return latestUpdatedUser;
            }
        });





        frame.setVisible(true);
    }


    
}
