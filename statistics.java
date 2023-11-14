package MiniTwitterApp;

// VISITOR PATTERN
public class statistics implements Visitor {

	private int totalUser = 0;
	private int totalGroup = 0;
	@Override
	public void visit(User user) {
		totalUser++;
    }

    @Override
    public void visit(UserGroup group) {
    	totalGroup++;
    }

    public int getUserCount() {
        return totalUser;
    }

    public int getGroupCount() {
        return totalGroup;
    }
}
