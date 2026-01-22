package SplitwisePackage;

import java.util.List;

public class GroupService {
    public Group createGroup(String id, List<User> members){
        return new Group(id, members);
    }

    public void addMember( Group group, User member ){
        group.addMember( member );
    }

    public void addMemberToGroup( Group group, User user ){
        group.addMember(user);
    }
}
