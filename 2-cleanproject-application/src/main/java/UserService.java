import java.util.UUID;

public class UserService {

    private UserRepositoryInterface userPersistence;

    public UserService(UserRepositoryInterface userPersistence) {
        this.userPersistence = userPersistence;
    }

    public UserAggregate getUser(Username name) throws UserNotFoundException {
        UserAggregate u = this.userPersistence.getUserFrom(name);
        if(u == null) throw new UserNotFoundException(name);
        return u;
    }

    public UserAggregate getUser(UUID uuid) throws UserNotFoundException {
        UserAggregate u = this.userPersistence.getUserFrom(uuid);
        if(u == null) throw new UserNotFoundException(uuid);
        return u;
    }

    public UserAggregate createNewUser(String userName) throws InvalidUsernameException {
        UserAggregate newUserAggregate = new UserAggregate(userName);
        this.userPersistence.save(newUserAggregate);
        return newUserAggregate;
    }

    public Moneypool createNewMoneypoolFor(Username username) throws UserNotFoundException {
        UserAggregate user = this.getUser(username);
        Moneypool moneypool = new Moneypool();
        this.userPersistence.save(moneypool, user);
        return moneypool;
    }

}
