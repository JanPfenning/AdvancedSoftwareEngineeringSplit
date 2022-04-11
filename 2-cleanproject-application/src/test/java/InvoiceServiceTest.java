import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvoiceServiceTest {
    public TransferRepositoryInterface mockedTransferRepository;
    public UserRepositoryInterface mockedUserRepository;

    public UserAggregate mockedSender;
    public UserAggregate mockedReceiver;

    @BeforeEach
    void init(){

    }
}