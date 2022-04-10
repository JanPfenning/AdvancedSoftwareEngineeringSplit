import IO.CSVreader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvoiceRepositoryTest {

    private LinkedList<String> invoices;
    private Invoice foundInvoice;

    private UUID invoiceId;
    private UUID biller;
    private String receiverName;
    private double amount;
    private long creationtimestamp;
    private boolean paid;


    @BeforeEach
    void init(){
        this.invoices = new LinkedList<>();
        this.foundInvoice = null;
    }

    @Test
    void getInvoiceById(){
        this.invoices.add("71483cf4-ab1a-45c2-8706-32c7dff1094d;223e4567-e89b-12d3-a456-222222222222;Jan;100.0;1648532576;1\n");
        this.invoices.add("73483cf4-ab1a-45c2-8706-32c7dff1094d;223e4567-e89b-12d3-a456-222222222222;Jan;200.0;1648532576;0\n");

        this.invoiceId = UUID.fromString("73483cf4-ab1a-45c2-8706-32c7dff1094d");
        this.biller = UUID.fromString("223e4567-e89b-12d3-a456-222222222222");
        this.receiverName = "Jan";
        this.amount = 200.0;
        this.creationtimestamp = 1648532576;
        this.paid = false;


        try (MockedStatic<CSVreader> mockPersistence = Mockito.mockStatic(CSVreader.class)) {
            mockPersistence.when(() -> CSVreader.read(InvoiceRepository.INVOICE_FILEPATH, "\r\n")).thenReturn(invoices);

            InvoiceRepository repositoryToTest = new InvoiceRepository();
            foundInvoice = repositoryToTest.get(invoiceId);
        }
//        Should run when issue #44 is resolved
        assertNotNull(foundInvoice);
        assertEquals(foundInvoice.getId(), invoiceId);
        assertEquals(foundInvoice.isPaid(), paid);
        assertEquals(foundInvoice.getAmount().getValue(), amount);
        assertEquals(foundInvoice.getCreationTimestamp().getUnixTime(), creationtimestamp);
        assertEquals(foundInvoice.getRecipient().getUsername().getValue(), receiverName);
        assertEquals(foundInvoice.getBiller().getId(), biller);
    }

    @Test
    void getInvoiceByUsername() throws InvalidUsernameException {
        this.invoices.add("71483cf4-ab1a-45c2-8706-32c7dff1094d;223e4567-e89b-12d3-a456-222222222222;Jan;100.0;1648532576;1");
        this.invoices.add("73483cf4-ab1a-45c2-8706-32c7dff1094d;223e4567-e89b-12d3-a456-222222222222;Jan;200.0;1648532576;0");

        this.invoiceId = UUID.fromString("73483cf4-ab1a-45c2-8706-32c7dff1094d");
        this.biller = UUID.fromString("223e4567-e89b-12d3-a456-222222222222");
        this.amount = 200.0;
        this.creationtimestamp = 1648532576;
        this.paid = false;

        Username receiver = new Username("Jan");

        ArrayList<Invoice> foundInvoices = new ArrayList<>();

        try (MockedStatic<CSVreader> mockPersistence = Mockito.mockStatic(CSVreader.class)) {
            mockPersistence.when(() -> CSVreader.read(InvoiceRepository.INVOICE_FILEPATH, "\r\n")).thenReturn(invoices);

            InvoiceRepository repositoryToTest = new InvoiceRepository();
            foundInvoices = repositoryToTest.get(receiver);
        }

        assertNotNull(foundInvoices);
        assertFalse(foundInvoices.isEmpty());
        foundInvoice = foundInvoices.get(1);
        assertNotNull(foundInvoice);
        assertEquals(foundInvoice.getId(), invoiceId);
        assertEquals(foundInvoice.isPaid(), paid);
        assertEquals(foundInvoice.getAmount().getValue(), amount);
//        assertEquals(foundInvoice.getCreationTimestamp().getUnixTime(), creationtimestamp);
//        TODO
//         test for timestamp when issue #43 is resolved
        assertEquals(foundInvoice.getRecipient().getUsername().getValue(), receiver.getValue());
        assertEquals(foundInvoice.getBiller().getId(), biller);
    }

    @Test
    void getInvoiceForNonExistingId(){
        this.invoiceId = UUID.fromString("223e4567-e89b-12d3-a456-000000000000");

        this.invoices.add("71483cf4-ab1a-45c2-8706-32c7dff1094d;223e4567-e89b-12d3-a456-222222222222;Jan;100.0;1648532576;1\n");
        this.invoices.add("73483cf4-ab1a-45c2-8706-32c7dff1094d;223e4567-e89b-12d3-a456-222222222222;Jan;200.0;1648532576;0\n");

        try (MockedStatic<CSVreader> mockPersistence = Mockito.mockStatic(CSVreader.class)) {
            mockPersistence.when(() -> CSVreader.read(InvoiceRepository.INVOICE_FILEPATH, "\r\n")).thenReturn(invoices);

            InvoiceRepository repositoryToTest = new InvoiceRepository();
            foundInvoice = repositoryToTest.get(invoiceId);
        }
        assertNull(foundInvoice);
    }

    @Test
    void getInvoicesForNonExistingUsername(){
        Username receiver = mock(Username.class);
        when(receiver.getValue()).thenReturn("Jan 2");

        ArrayList<Invoice> foundInvoices = new ArrayList<>();

        this.invoices.add("71483cf4-ab1a-45c2-8706-32c7dff1094d;223e4567-e89b-12d3-a456-222222222222;Jan;100.0;1648532576;1\n");
        this.invoices.add("73483cf4-ab1a-45c2-8706-32c7dff1094d;223e4567-e89b-12d3-a456-222222222222;Jan;200.0;1648532576;0\n");

        try (MockedStatic<CSVreader> mockPersistence = Mockito.mockStatic(CSVreader.class)) {
            mockPersistence.when(() -> CSVreader.read(InvoiceRepository.INVOICE_FILEPATH, "\r\n")).thenReturn(invoices);

            InvoiceRepository repositoryToTest = new InvoiceRepository();
            foundInvoices = repositoryToTest.get(receiver);
        }
        assertTrue(foundInvoices.isEmpty());
    }

    @Test
    void saveAndGetInvoice() throws InvalidBalanceException {
        UUID uuid = UUID.fromString("223e4567-e89b-12d3-a456-222222222222");
        Invoice mockedInvoice = mock(Invoice.class);

        Depot mockedDepot = mock(Account.class);
        when(mockedDepot.getId()).thenReturn(uuid);

        UserAggregate mockedRecipient = mock(UserAggregate.class);
        when(mockedRecipient.getAccount()).thenReturn((Account) mockedDepot);

        Timestamp mockedTime = mock(Timestamp.class);
        when(mockedTime.getUnixTime()).thenReturn((long) 123456.0);

        when(mockedInvoice.getId()).thenReturn(uuid);
        when(mockedInvoice.getBiller()).thenReturn(mockedDepot);
        when(mockedInvoice.getRecipient()).thenReturn(mockedRecipient);
        when(mockedInvoice.getCreationTimestamp()).thenReturn(mockedTime);

        InvoiceRepository repositoryToTest = new InvoiceRepository();
        repositoryToTest.save(mockedInvoice);

        repositoryToTest = new InvoiceRepository();
        foundInvoice = repositoryToTest.get(UUID.fromString("223e4567-e89b-12d3-a456-000000000000"));

        assertEquals(mockedInvoice, foundInvoice);
    }

}
