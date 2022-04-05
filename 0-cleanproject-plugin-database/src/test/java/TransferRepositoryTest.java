import IO.CSVreader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransferRepositoryTest {

    private LinkedList<String> transfers;
    private UUID querryId;
    private Transfer foundTransfer;

    @BeforeEach
    void init(){
        this.transfers = new LinkedList<String>();
        this.foundTransfer = null;
    }

    @Test
    void getFirst() throws InvalidAmountException {
        this.transfers.add("5267debe-af38-11ec-b909-0242ac120002;223e4567-e89b-12d3-a456-000000000000;223e4567-e89b-12d3-a456-222222222222;0.1;;1648118619\n");
        this.transfers.add("5267e1c0-af38-11ec-b909-0242ac120002;223e4567-e89b-12d3-a456-222222222222;07a3d2d5-95c3-4ef9-8c5d-03f32e3488f6;1.2;0;1648305732\n");

        this.querryId = UUID.fromString("5267debe-af38-11ec-b909-0242ac120002");
        try (MockedStatic<CSVreader> mockPersistence = Mockito.mockStatic(CSVreader.class)) {
            mockPersistence.when(() -> CSVreader.read(TransferRepository.TRANSFER_FILEPATH, "\r\n")).thenReturn(transfers);

            TransferRepository repositoryToTest = new TransferRepository();
            foundTransfer = repositoryToTest.get(querryId);
        }
        assertNotNull(foundTransfer);
        assertEquals(foundTransfer.getId(), querryId);
        assertEquals(foundTransfer.getAmount(), new Amount((float) 0.1));
    }

    @Test
    void getSecond() throws InvalidAmountException {
        this.transfers.add("5267debe-af38-11ec-b909-0242ac120002;223e4567-e89b-12d3-a456-000000000000;223e4567-e89b-12d3-a456-222222222222;0.1;;1648118619\n");
        this.transfers.add("5267e1c0-af38-11ec-b909-0242ac120002;223e4567-e89b-12d3-a456-222222222222;07a3d2d5-95c3-4ef9-8c5d-03f32e3488f6;1.2;;1648305732\n");

        this.querryId = UUID.fromString("5267e1c0-af38-11ec-b909-0242ac120002");
        try (MockedStatic<CSVreader> mockPersistence = Mockito.mockStatic(CSVreader.class)) {
            mockPersistence.when(() -> CSVreader.read(TransferRepository.TRANSFER_FILEPATH, "\r\n")).thenReturn(transfers);

            TransferRepository repositoryToTest = new TransferRepository();
            foundTransfer = repositoryToTest.get(querryId);
        }
        assertNotNull(foundTransfer);
        assertEquals(foundTransfer.getId(), querryId);
        assertEquals(foundTransfer.getAmount(), new Amount((float) 1.2));
    }

    @Test
    void searchIdThatDoesNotExists() {
        this.transfers.add("5267debe-af38-11ec-b909-0242ac120002;223e4567-e89b-12d3-a456-000000000000;223e4567-e89b-12d3-a456-222222222222;0.1;;1648118619\n");
        this.transfers.add("5267e1c0-af38-11ec-b909-0242ac120002;223e4567-e89b-12d3-a456-222222222222;07a3d2d5-95c3-4ef9-8c5d-03f32e3488f6;1.2;0;1648305732\n");

        this.querryId = UUID.fromString("A267debe-af38-11ec-b909-0242ac120002");
        try (MockedStatic<CSVreader> mockPersistence = Mockito.mockStatic(CSVreader.class)) {
            mockPersistence.when(() -> CSVreader.read(TransferRepository.TRANSFER_FILEPATH, "\r\n")).thenReturn(transfers);

            TransferRepository repositoryToTest = new TransferRepository();
            foundTransfer = repositoryToTest.get(querryId);
        }
        assertNull(foundTransfer);
    }
}