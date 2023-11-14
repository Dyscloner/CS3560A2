package MiniTwitterApp;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class UserFrame extends JFrame implements Observer {
	 private User user;
	    private List<User> followingList;
	    private DefaultListModel<String> followingListModel;
	    private DefaultListModel<String> tweetsListModel;
	    private JTextField jTextField1;
	    private JButton jButton1; // Declare JButton as an instance variable
	    private JButton jButton2; // Declare JButton as an instance variable
	    private JTextField jTextField2;
	    private JList<String> jList1;
	    private JList<String> jList2;
	    private JScrollPane jScrollPane1;
	    private JScrollPane jScrollPane2;
	    private JLabel jLabel1;

	    public UserFrame(User user) {
	        super("User View: " + user.getId());
	        this.user = user;
	        this.followingList = new ArrayList<>();
	        this.followingListModel = new DefaultListModel<>();
	        this.tweetsListModel = new DefaultListModel<>();
	        user.addObserver(this);
	        initComponents();
	    }

    private void initComponents() {
    	jButton1 = new JButton("Follow User");
        jButton2 = new JButton("jButton2");
        jLabel1 = new JLabel("User ID");
        jScrollPane1 = new JScrollPane();
        jList1 = new JList<>();
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jScrollPane2 = new JScrollPane();
        jList2 = new JList<>();

    	 jButton1.addActionListener(e -> followUser());
    	 jButton2.addActionListener(e -> postTweet());
            
    // MADE FROM NETBEANS
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jButton1.setText("Follow User");
        jButton1.setBorder(javax.swing.BorderFactory.createDashedBorder(null));
        jLabel1.setText("User ID");
        jScrollPane1.setViewportView(jList1);
        jButton2.setText("Post Tweet");
        jScrollPane2.setViewportView(jList2);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 18, Short.MAX_VALUE))))
        );
        
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
   
        pack();
  
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pack();
            setVisible(true);
        });
       
    }
    
    private void followUser() {
        String userIdToFollow = jTextField1.getText();
        User followedUser = getUserById(userIdToFollow);
        
        if (userIdToFollow.equals(user.getId())) {
            JOptionPane.showMessageDialog(this, "You cannot follow yourself.");
        }
        
        if (followedUser != null) {
            followedUser.addFollower(user);
            followingList.add(followedUser);
            followingListModel.addElement(followedUser.getId());
            jList1.setModel(followingListModel);
            List<String> newsFeed = followedUser.getNewsFeed();
            tweetsListModel.clear(); 
            for (String tweet : newsFeed) {
                tweetsListModel.addElement(tweet);
                followedUser.addObserver(this); 
            }         
            JOptionPane.showMessageDialog(this, "User Followed: " + userIdToFollow);
        } else {
            JOptionPane.showMessageDialog(this, "User not found: " + userIdToFollow);
        }
    }

    private void postTweet() {
        String tweetText = jTextField2.getText();
        user.postTweet(tweetText);

        String formattedTweet = user.getId() + ": " + tweetText;
        tweetsListModel.addElement(formattedTweet);
        jTextField2.setText("");

        for (User follower : user.getFollowers()) {
            follower.updateNewsFeed(user, tweetText);
            follower.updateTweetsList(user.getId()+": "+ tweetText);
        }
    }
    
    public void updateTweetsList(String tweet) {
        tweetsListModel.addElement(tweet);
        jList2.setModel(tweetsListModel);
    }  
    
    private User getUserById(String userIdToFollow) {
        return User.getUserById(userIdToFollow);
    }
}
    

