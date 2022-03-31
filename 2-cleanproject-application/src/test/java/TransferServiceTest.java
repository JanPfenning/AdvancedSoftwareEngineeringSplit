import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    @Test
    void senderInsufficientMoney() throws Exception {
        TransferRepositoryInterface mockedTransferRepository = mock(TransferRepositoryInterface.class);
        UserRepositoryInterface mockedUserRepository = mock(UserRepositoryInterface.class);
        Depot mockedSenderDepot = mock(Account.class);
        Depot mockedReceiverDepot = mock(Account.class);
        UserAggregate mockedSender = mock(UserAggregate.class);
        UserAggregate mockedReceiver = mock(UserAggregate.class);

        when(mockedUserRepository.getUserFrom(UUID.fromString("123e4567-e89b-12d3-a456-111111111111"))).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(UUID.fromString("123e4567-e89b-12d3-a456-222222222222"))).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(UUID.fromString("123e4567-e89b-12d3-a456-111111111111"))).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(UUID.fromString("123e4567-e89b-12d3-a456-222222222222"))).thenReturn(mockedSenderDepot);

        when(mockedSenderDepot.getBalance()).thenReturn(new Balance(0));
        when(mockedReceiverDepot.getBalance()).thenReturn(new Balance(0));

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertThrows(InvalidBalanceException.class, ()->{
            service.sendMoney("123e4567-e89b-12d3-a456-111111111111", "123e4567-e89b-12d3-a456-222222222222", new Amount(1));
        });
    }

    @Test
    void AccountToAccountHappyPath() throws Exception {
        TransferRepositoryInterface mockedTransferRepository = mock(TransferRepositoryInterface.class);
        UserRepositoryInterface mockedUserRepository = mock(UserRepositoryInterface.class);
        UserAggregate mockedSender = mock(UserAggregate.class);
        UserAggregate mockedReceiver = mock(UserAggregate.class);
        Depot mockedSenderDepot = mock(Account.class);
        Depot mockedReceiverDepot = mock(Account.class);

        when(mockedUserRepository.getUserFrom(UUID.fromString("123e4567-e89b-12d3-a456-111111111111"))).thenReturn(mockedSender);
        when(mockedUserRepository.getUserFrom(UUID.fromString("123e4567-e89b-12d3-a456-222222222222"))).thenReturn(mockedReceiver);

        when(mockedSender.getDepotBy(UUID.fromString("123e4567-e89b-12d3-a456-111111111111"))).thenReturn(mockedSenderDepot);
        when(mockedReceiver.getDepotBy(UUID.fromString("123e4567-e89b-12d3-a456-222222222222"))).thenReturn(mockedReceiverDepot);

        when(mockedSenderDepot.getBalance()).thenReturn(new Balance(2));
        when(mockedReceiverDepot.getBalance()).thenReturn(new Balance(0));

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertDoesNotThrow(()->{
            service.sendMoney("123e4567-e89b-12d3-a456-111111111111", "123e4567-e89b-12d3-a456-222222222222", new Amount(1));
        });
    }

    //TODO test send from moneypool to moneypool
    //TODO test send from Moneypool to account
    //TODO test send from Account to moneypool

}