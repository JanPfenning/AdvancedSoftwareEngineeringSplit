public class UserService {

    private UserRepositoryInterface userPersistence;

    public UserService(UserRepositoryInterface userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User getUser(String userName){
        User u = this.userPersistence.get(userName);
        return u;
    }

}
