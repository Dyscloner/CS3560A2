package MiniTwitterApp;

class UserGroup implements UserVisitor{
    private String id;

    public UserGroup(String groupId) {
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
}
