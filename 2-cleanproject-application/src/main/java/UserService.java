import java.util.UUID;

public class UserService {

    private UserRepositoryInterface userRepository;

    public UserService(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    public UserAggregate getUser(Username name) throws UserNotFoundException {
        UserAggregate u = this.userRepository.getUserFrom(name);
        if(u == null) throw new UserNotFoundException(name);
        return u;
    }

    public UserAggregate getUser(UUID uuid) throws UserNotFoundException {
        UserAggregate u = this.userRepository.getUserFrom(uuid);
        if(u == null) throw new UserNotFoundException(uuid);
        return u;
    }

    public UserAggregate createNewUser(String userName) throws InvalidUsernameException {
        UserAggregate newUserAggregate = new UserAggregate(userName);
        this.userRepository.save(newUserAggregate);
        return newUserAggregate;
    }

    public Moneypool createNewMoneypoolFor(Username username) throws UserNotFoundException {
        UserAggregate user = this.getUser(username);
        Moneypool moneypool = new Moneypool();
        this.userRepository.save(moneypool, user);
        return moneypool;
    }

}
