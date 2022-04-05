import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferServiceTest {

    private static UUID moneypoolIDofSender = UUID.fromString("223e4567-e89b-12d3-a456-000000000000");
    private static UUID moneypoolIDofReciever = UUID.fromString("223e4567-e89b-12d3-a456-000000000000");

    private static UUID accountIDofSender = UUID.fromString("123e4567-e89b-12d3-a456-111111111111");
    private static UUID accountIDofReciever = UUID.fromString("123e4567-e89b-12d3-a456-222222222222");


    private TransferRepositoryInterface mockedTransferRepository;
    private UserRepositoryInterface mockedUserRepository;

    private UserAggregate mockedSender;
    private UserAggregate mockedReceiver;

    @BeforeAll
    void init(){
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
        when(mockedReceiver.getDepotBy(accountIDofReciever)).thenReturn(mockedSenderDepot);

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

        when(mockedUserRepository.getUserFrom(moneypoolIDofSender)).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(moneypoolIDofReciever)).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(moneypoolIDofSender)).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(moneypoolIDofReciever)).thenReturn(mockedReceiverDepot);

        when(mockedSenderDepot.getBalance()).thenReturn(new Balance(2));
        when(mockedReceiverDepot.getBalance()).thenReturn(new Balance(0));

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertDoesNotThrow(()->{
            service.sendMoney(moneypoolIDofSender.toString(), moneypoolIDofReciever.toString(), new Amount(1));
        });
    }

    @Test
    void moneypoolToMoneypool() {
        Depot mockedSenderDepot = mock(Moneypool.class);
        Depot mockedReceiverDepot = mock(Moneypool.class);

        when(mockedUserRepository.getUserFrom(moneypoolIDofSender)).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(moneypoolIDofReciever)).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(moneypoolIDofSender)).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(moneypoolIDofReciever)).thenReturn(mockedReceiverDepot);

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertThrows( TransferOutOfMoneypoolException.class,() ->{
            service.sendMoney(moneypoolIDofSender.toString(), moneypoolIDofReciever.toString(), new Amount(1));
        });
    }

    @Test
    void moneypoolToAccount() {
        Depot mockedSenderDepot = mock(Moneypool.class);
        Depot mockedReceiverDepot = mock(Account.class);

        when(mockedUserRepository.getUserFrom(moneypoolIDofSender)).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(accountIDofReciever)).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(moneypoolIDofSender)).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(accountIDofReciever)).thenReturn(mockedReceiverDepot);

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertThrows( TransferOutOfMoneypoolException.class,() ->{
            service.sendMoney(moneypoolIDofSender.toString(), accountIDofReciever.toString(), new Amount(1));
        });
    }

    @Test
    void accountToMoneypool() throws Exception{
        Depot mockedSenderDepot = mock(Account.class);
        Depot mockedReceiverDepot = mock(Moneypool.class);

        when(mockedUserRepository.getUserFrom(accountIDofSender)).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(moneypoolIDofReciever)).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(accountIDofSender)).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(moneypoolIDofReciever)).thenReturn(mockedReceiverDepot);

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertDoesNotThrow(() ->{
            service.sendMoney(accountIDofSender.toString(), moneypoolIDofReciever.toString(), new Amount(1));
        });
    }

}