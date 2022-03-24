public class UserService {

    private UserRepositoryInterface userPersistence;

    public UserService(UserRepositoryInterface userPersistence) {
        this.userPersistence = userPersistence;
    }

    public UserAggregate getUser(String userName){
        UserAggregate u = this.userPersistence.getUserFrom(new Username(userName));
        return u;
    }

    public UserAggregate createNewUser(String userName){
        UserAggregate newUserAggregate = new UserAggregate(userName);
        this.userPersistence.save(newUserAggregate);
        return newUserAggregate;
    }

    public Moneypool createNewMoneypoolFor(UserAggregate user){
        Moneypool moneypool = new Moneypool();
        this.userPersistence.save(moneypool, user);
        return moneypool;
    }

}
