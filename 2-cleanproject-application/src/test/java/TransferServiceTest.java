import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    @Test
    void senderInsufficientMoney() throws Exception {
        TransferRepositoryInterface mockedTransferRepository = mock(TransferRepositoryInterface.class);
        UserRepositoryInterface mockedUserRepository = mock(UserRepositoryInterface.class);
        Depot mockedSender = mock(Account.class);
        Depot mockedReceiver = mock(Account.class);

        when(mockedUserRepository.getDepotFrom(UUID.fromString("123e4567-e89b-12d3-a456-111111111111"))).thenReturn(mockedSender);
        when(mockedUserRepository.getDepotFrom(UUID.fromString("123e4567-e89b-12d3-a456-222222222222"))).thenReturn(mockedReceiver);

        when(mockedSender.getBalance()).thenReturn(new Balance(0));
        when(mockedReceiver.getBalance()).thenReturn(new Balance(0));

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertThrows(Exception.class, ()->{
            service.sendMoney("123e4567-e89b-12d3-a456-111111111111", "123e4567-e89b-12d3-a456-222222222222", new Amount(1));
        });
    }

    @Test
    void senderSufficientMoney() throws Exception {
        TransferRepositoryInterface mockedTransferRepository = mock(TransferRepositoryInterface.class);
        UserRepositoryInterface mockedUserRepository = mock(UserRepositoryInterface.class);
        UserAggregate mockedUser = mock(UserAggregate.class);
        Depot mockedSender = mock(Account.class);
        Depot mockedReceiver = mock(Account.class);

        when(mockedUserRepository.getDepotFrom(UUID.fromString("123e4567-e89b-12d3-a456-111111111111"))).thenReturn(mockedSender);
        when(mockedUserRepository.getDepotFrom(UUID.fromString("123e4567-e89b-12d3-a456-222222222222"))).thenReturn(mockedReceiver);
        when(mockedUserRepository.getUserFrom(UUID.fromString("123e4567-e89b-12d3-a456-111111111111"))).thenReturn(mockedUser);
        when(mockedUserRepository.getUserFrom(UUID.fromString("123e4567-e89b-12d3-a456-222222222222"))).thenReturn(mockedUser);

        when(mockedSender.getBalance()).thenReturn(new Balance(2));
        when(mockedReceiver.getBalance()).thenReturn(new Balance(0));

        TransferService service = new TransferService(mockedUserRepository, mockedTransferRepository);
        assertDoesNotThrow(()->{
            service.sendMoney("123e4567-e89b-12d3-a456-111111111111", "123e4567-e89b-12d3-a456-222222222222", new Amount(1));
        });
    }

}