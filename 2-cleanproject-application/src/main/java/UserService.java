public class UserService {

    private UserRepositoryInterface userPersistence;

    public UserService(UserRepositoryInterface userPersistence) {
        this.userPersistence = userPersistence;
    }

    public UserAggregate getUser(String userName){
        UserAggregate u = this.userPersistence.getUserFrom(new Username(userName));
        return u;
    }

    public void createNewUser(String userName){
        UserAggregate newUserAggregate = new UserAggregate(userName);
    }

}
