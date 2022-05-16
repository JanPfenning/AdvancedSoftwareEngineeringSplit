import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferServiceTest {

    private UUID moneypoolIDofSender;
    private UUID moneypoolIDofReciever;

    private UUID accountIDofSender;
    private UUID accountIDofReciever;

    public TransferRepositoryInterface mockedTransferRepository;
    public UserRepositoryInterface mockedUserRepository;

    public UserAggregate mockedSender;
    public UserAggregate mockedReceiver;

    @BeforeEach
    void init(){
        this.moneypoolIDofSender = UUID.fromString("223e4567-e89b-12d3-a456-000000000000");
        this.moneypoolIDofReciever = UUID.fromString("223e4567-e89b-12d3-a456-111111111111");
        this.accountIDofSender = UUID.fromString("123e4567-e89b-12d3-a456-111111111111");
        this.accountIDofReciever = UUID.fromString("123e4567-e89b-12d3-a456-222222222222");

        this.mockedTransferRepository = mock(TransferRepositoryInterface.class);
        this.mockedUserRepository = mock(UserRepositoryInterface.class);

        this.mockedSender = mock(UserAggregate.class);
        this.mockedReceiver = mock(UserAggregate.class);
    }

    @Test
    void senderInsufficientMoney() throws Exception {
        Depot mockedSenderDepot = mock(Account.class);
        Depot mockedReceiverDepot = mock(Account.class);

        when(mockedUserRepository.getUserFrom(accountIDofSender)).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(accountIDofReciever)).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(accountIDofSender)).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(accountIDofReciever)).thenReturn(mockedReceiverDepot);

        when(mockedSenderDepot.getBalance()).thenReturn(new Balance(0));
        when(mockedReceiverDepot.getBalance()).thenReturn(new Balance(0));

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertThrows(InvalidBalanceException.class, ()->{
            service.sendMoney(accountIDofSender.toString(), accountIDofReciever.toString(), new Amount(1));
        });
    }

    @Test
    void AccountToAccountHappyPath() throws Exception {
        Depot mockedSenderDepot = mock(Account.class);
        Depot mockedReceiverDepot = mock(Account.class);

        when(mockedUserRepository.getUserFrom(accountIDofSender)).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(accountIDofReciever)).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(accountIDofSender)).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(accountIDofReciever)).thenReturn(mockedReceiverDepot);

        when(mockedSenderDepot.getBalance()).thenReturn(new Balance(2));
        when(mockedReceiverDepot.getBalance()).thenReturn(new Balance(0));

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertDoesNotThrow(()->{
            service.sendMoney(accountIDofSender.toString(), accountIDofReciever.toString(), new Amount(1));
        });
    }

    @Test
    void AccountToMoneypoolHappyPath() throws Exception {
        Depot mockedSenderDepot = mock(Account.class);
        Depot mockedReceiverDepot = mock(Moneypool.class);

        when(mockedUserRepository.getUserFrom(accountIDofSender)).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(moneypoolIDofReciever)).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(accountIDofSender)).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(moneypoolIDofReciever)).thenReturn(mockedReceiverDepot);

        when(mockedSenderDepot.getBalance()).thenReturn(new Balance(2));
        when(mockedReceiverDepot.getBalance()).thenReturn(new Balance(0));

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertDoesNotThrow(()->{
            service.sendMoney(accountIDofSender.toString(), moneypoolIDofReciever.toString(), new Amount(1));
        });
    }

    /*
    @Test
    void MoneypoolToAccount() throws Exception {
        Depot mockedSenderDepot = mock(Moneypool.class);
        Depot mockedReceiverDepot = mock(Account.class);

        when(mockedUserRepository.getUserFrom(moneypoolIDofSender)).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(accountIDofReciever)).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(moneypoolIDofSender)).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(accountIDofReciever)).thenReturn(mockedReceiverDepot);

        when(mockedSenderDepot.getBalance()).thenReturn(new Balance(5));
        when(mockedReceiverDepot.getBalance()).thenReturn(new Balance(2));

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertThrows(TransferOutOfMoneypoolException.class, ()->{
            //TODO Junit skips the overwritten Method Set balance in moneypool
            service.sendMoney(moneypoolIDofSender.toString(), accountIDofReciever.toString(), new Amount(1));
        });
    }
     */

}