package MiniTwitterApp;

class UserGroup implements UserVisitor{
    private String id;
    private long lastUpdateTime;
    private long creationTime; 

    public UserGroup(String groupId) {
    	this.lastUpdateTime = System.currentTimeMillis();
        this.creationTime = System.currentTimeMillis();  // Set creation time when the user group is created
    
        this.id = groupId;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Group: " + id;
    }
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    public long getCreationTime() {
        return creationTime;
    }
    public long getLastUpdatedTime() {
        return lastUpdateTime;
    }
}
