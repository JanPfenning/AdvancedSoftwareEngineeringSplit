public class UserManager {

    private UserPersistenceInterface userPersistence;

    public UserManager(UserPersistenceInterface userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User getUser(String userName){
        User u = this.userPersistence.get(userName);
        return u;
    }

}
