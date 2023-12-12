package MiniTwitterApp;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
// COMPOSITE
public class AdminPanel {
	private static AdminPanel instance;
    private Map<String, User> users = new HashMap<>();
    private Set<String> usedUserNames = new HashSet<>();
    private Set<String> usedGroupNames = new HashSet<>();
    private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
    private JTree userTree = new JTree(root);
    private DefaultTreeModel treeModel;
    private JTextArea userTextArea = new JTextArea(2, 10);
    private JTextArea groupTextArea = new JTextArea(2, 10);

    private AdminPanel() {
        JFrame adminFrame = new JFrame("Admin Control Panel");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        treeModel = (DefaultTreeModel) userTree.getModel();
        userTree.setPreferredSize(new Dimension(200, 300));     
        JButton addUserButton = new JButton("Add User");
        JButton addGroupButton = new JButton("Add Group");
        JButton userInterface = new JButton("Open User View");
        JButton showUserTotal = new JButton("Show User Total");
        JButton showGroupTotal = new JButton("Show Group Total");
        JButton showMessageTotal = new JButton("Show Message Total");
        JButton showPositive = new JButton("Show Positive Percentage");
        JButton validateIDsButton = new JButton("Validate IDs");
        JButton findLastUpdatedUserButton = new JButton("Find Last Updated User");
        JButton showUserCreationTime = new JButton("Show User Creation Time");
        JButton showGroupCreationTime = new JButton("Show Group Creation Time");
        JButton showLast = new JButton("Show Last Updated Time");
      
        showLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();
                if (selectedNode != null && selectedNode.getUserObject() instanceof User) {
                    User selectedUser = (User) selectedNode.getUserObject();
                    showLastUpdatedTime(selectedUser.getLastUpdateTime());
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a User from the tree.");
                }
            }
        });

        showUserCreationTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();
                if (selectedNode != null && selectedNode.getUserObject() instanceof User) {
                    User selectedUser = (User) selectedNode.getUserObject();
                    showCreationTime(selectedUser.getCreationTime());
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a User from the tree.");
                }
            }
        });

        showGroupCreationTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();
                if (selectedNode != null && selectedNode.getUserObject() instanceof UserGroup) {
                    UserGroup selectedGroup = (UserGroup) selectedNode.getUserObject();
                    showCreationTime(selectedGroup.getCreationTime());
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a UserGroup from the tree.");
                }
            }
        });
        findLastUpdatedUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User lastUpdatedUser = findLastUpdatedUser(root);
                if (lastUpdatedUser != null) {
                    JOptionPane.showMessageDialog(null, "Last Updated User: " + lastUpdatedUser.getId());
                } else {
                    JOptionPane.showMessageDialog(null, "No updates found.");
                }
            }
        });
        validateIDsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean allIDsValid = validateAllIDs(root);
                if (allIDsValid) {
                    JOptionPane.showMessageDialog(null, "All IDs are valid.");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid IDs found.");
                }
            }
        });
        /// ADD USER
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userTextArea.getText();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();

                if (userName != null && !userName.isEmpty() && usedUserNames.add(userName)) {
                  
                    User user = new User(userName);
                    users.put(userName, user);
                    DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);

                    if (selectedNode == null) {
                        treeModel.insertNodeInto(userNode, root, root.getChildCount());
                    } else {
                        treeModel.insertNodeInto(userNode, selectedNode, selectedNode.getChildCount());
                    }
                    userTree.scrollPathToVisible(new TreePath(userNode.getPath()));
                    userTextArea.setText(""); 
                } else {
                    JOptionPane.showMessageDialog(null, "User name must be unique or not empty.");
                }
            }
        });
        
        // SHOW USER TOTAL
        showUserTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statistics stats = new statistics();
                for (User user: users.values()) {
                	user.accept(stats);
;                }
                int usertot = stats.getUserCount();
                JOptionPane.showMessageDialog(null, "Total Users: " + usertot);
            }
        });
        // ADD GROUP
        
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = groupTextArea.getText();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();

                if (groupName == null || groupName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Group name cannot be empty.");
                    return;
                }

                if (selectedNode == null || selectedNode.getUserObject() instanceof UserGroup) {
                    if (!usedGroupNames.contains(groupName)) {
                        UserGroup group = new UserGroup(groupName);
                        DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);

                        if (selectedNode != null) {
                            treeModel.insertNodeInto(groupNode, selectedNode, selectedNode.getChildCount());
                        } else {
                            treeModel.insertNodeInto(groupNode, root, root.getChildCount());
                        }
                        userTree.scrollPathToVisible(new TreePath(groupNode.getPath()));
                        groupTextArea.setText("");
                        usedGroupNames.add(groupName);
                    } else {
                        JOptionPane.showMessageDialog(null, "Group name must be unique among UserGroups.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "UserGroup can only be added to another UserGroup or Root.");
                }
            }
        });


        userInterface.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();

                if (selectedNode != null && selectedNode.getUserObject() instanceof User) {
                    User selectedUser = (User) selectedNode.getUserObject();
                    new UserFrame(selectedUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a User from the tree.");
                }
            }
        });
        
     // SHOW GROUP TOTAL
        showGroupTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	statistics stats = new statistics();
            	countGroups(root, stats);
                int groupTotal = stats.getGroupCount();
                JOptionPane.showMessageDialog(null, "Total Groups: " + groupTotal);
            }
        });
        
        // OPEN USER WINDOW
        userInterface.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selected = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();

                if (selected != null && selected.getUserObject() instanceof User) {
                    User selectedUser = (User) selected.getUserObject();
                    new UserFrame(selectedUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Select a User from the tree.");
                }
            }
        });
        
        // MSG TOTAL
        showMessageTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int messageTotal = countTotalTweets();
           JOptionPane.showMessageDialog(null, "Total MSG: " + messageTotal);
            }
        });
        
        // POSITIVE PERCENTAGE
        showPositive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double positivePercentage = calculatePositiveTweetPercentage();
                JOptionPane.showMessageDialog(null, "Likes Percentage: " + positivePercentage + "%");
            }
        });

        JPanel adminPanel = new JPanel(new BorderLayout());
        // Left panel for tree
        JPanel treePanel = new JPanel(new BorderLayout());
        treePanel.add(new JScrollPane(userTree), BorderLayout.CENTER);
        adminPanel.add(treePanel, BorderLayout.WEST);
        // Right panel for the rest
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 10, 5, 10); // Adjusted insets for more spacing

        // User row
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("User:"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(userTextArea, constraints);

        constraints.gridx = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(addUserButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(5, 10, 5, 10); // Adjusted insets for labels
        inputPanel.add(new JLabel("Group:"), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(groupTextArea, constraints);

        constraints.gridx = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(5, 5, 5, 5); // Adjusted insets for buttons
        inputPanel.add(addGroupButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 30;
        constraints.fill = GridBagConstraints.NONE;
        inputPanel.add(userInterface, constraints);

        constraints.gridwidth = 1; 
        constraints.gridx = 0;
        constraints.gridy = 3;
        inputPanel.add(showUserTotal, constraints);

        constraints.gridx = 1;
        inputPanel.add(showGroupTotal, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        inputPanel.add(showMessageTotal, constraints);

        constraints.gridx = 1;
        inputPanel.add(showPositive, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        inputPanel.add(new JLabel(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        inputPanel.add(new JLabel(), constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 7;
        inputPanel.add(validateIDsButton, constraints);
        
        constraints.gridx = 3;
        constraints.gridy = 7;
        inputPanel.add(findLastUpdatedUserButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 8;
        inputPanel.add(showUserCreationTime, constraints);

        constraints.gridx = 3;
        constraints.gridy = 8;
        inputPanel.add(showGroupCreationTime, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 9;
        inputPanel.add(showLast, constraints);
        
        adminPanel.add(inputPanel, BorderLayout.CENTER);
        adminFrame.add(adminPanel);
        
        adminFrame.pack();
        adminFrame.setVisible(true);
    }
    
    // SINGLETON
    public static AdminPanel getInstance() {
        if (instance == null) {
            instance = new AdminPanel();
        }
        return instance;
    }
    
    // VISITOR TO COUNT GROUP
    private void countGroups(DefaultMutableTreeNode node, Visitor visitor) {
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
            if (childNode.getUserObject() instanceof UserGroup) {
                UserGroup group = (UserGroup) childNode.getUserObject();
                group.accept(visitor);
                countGroups(childNode, visitor);
            }
        }
    }
    private void showCreationTime(long creationTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(creationTime);
        JOptionPane.showMessageDialog(null, "Creation Time: " + formattedDate);
    }
    private int countTotalTweets() {
      return User.getTotalMessages();
        
    }
    
    private int countTotalPos() {
    	return User.getTotalpos();
    }
    
    private double calculatePositiveTweetPercentage() {
        int totalPositiveTweets = countTotalPos();
        int totalTweets = countTotalTweets();
       
        double percent = (double) totalPositiveTweets / totalTweets * 100;
        return percent;
    }
    private boolean validateAllIDs(DefaultMutableTreeNode node) {
        Set<String> usedIDs = new HashSet<>();
        return validateIDsRecursive(node, usedIDs);
    }

    private void showLastUpdatedTime(long lastUpdateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(lastUpdateTime);
        JOptionPane.showMessageDialog(null, "Last Updated Time: " + formattedDate);
    }
    private boolean validateIDsRecursive(DefaultMutableTreeNode node, Set<String> usedIDs) {
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
            if (childNode.getUserObject() instanceof UserGroup) {
                String groupID = ((UserGroup) childNode.getUserObject()).getId();
                if (!isValidID(groupID, usedIDs)) {
                    return false;
                }
                usedIDs.add(groupID);
                if (!validateIDsRecursive(childNode, usedIDs)) {
                    return false;
                }
            } else if (childNode.getUserObject() instanceof User) {
                String userID = ((User) childNode.getUserObject()).getId();
                if (!isValidID(userID, usedIDs)) {
                    return false;
                }
                usedIDs.add(userID);
            }
        }
        return true;
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

    private boolean isValidID(String id, Set<String> usedIDs) {
        return id != null && !id.isEmpty() && !usedIDs.contains(id) && !id.contains(" ");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> AdminPanel.getInstance());
    }
}

