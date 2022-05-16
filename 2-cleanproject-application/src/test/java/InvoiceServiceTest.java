import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvoiceServiceTest {

    private UserRepositoryInterface mockedUserRepository;
    private InvoiceRepositoryInterface mockedInvoiceRepository;
    private TransferRepositoryInterface mockedTransferRepository;

    private UUID moneypoolIDofIssuer;
    private UUID accountIDofIssuer;
    private UUID moneypoolIDofPayer;
    private UUID accountIDofPayer;

    private Username usernameOfPayer;

    public UserAggregate mockedIssuer;
    public UserAggregate mockedPayer;

    @BeforeEach
    void init() throws InvalidUsernameException {
        this.moneypoolIDofIssuer = UUID.fromString("223e4567-e89b-12d3-a456-000000000000");
        this.accountIDofIssuer = UUID.fromString("123e4567-e89b-12d3-a456-111111111111");

        this.moneypoolIDofPayer = UUID.fromString("223e4567-e89b-12d3-a456-111111111111");
        this.accountIDofPayer = UUID.fromString("123e4567-e89b-12d3-a456-222222222222");

        this.usernameOfPayer = new Username("USERNAME_OF_PAYER");

        this.mockedTransferRepository = mock(TransferRepositoryInterface.class);
        this.mockedInvoiceRepository = mock(InvoiceRepositoryInterface.class);
        this.mockedUserRepository = mock(UserRepositoryInterface.class);

        this.mockedIssuer = mock(UserAggregate.class);
        this.mockedPayer = mock(UserAggregate.class);
    }

    @Test
    void sendInvoiceHappyPathAccount() throws Exception {
        Depot mockedIssuerDepot = mock(Account.class);

        when(mockedUserRepository.getUserFrom(accountIDofIssuer)).thenReturn(mockedIssuer);
        when(mockedPayer.getDepotBy(accountIDofIssuer)).thenReturn(mockedIssuerDepot);
        when(mockedIssuerDepot.getBalance()).thenReturn(new Balance(0));

        when(mockedUserRepository.getUserFrom(usernameOfPayer)).thenReturn(mockedPayer);

        InvoiceService service = new InvoiceService(mockedUserRepository, mockedInvoiceRepository, mockedTransferRepository);
        assertDoesNotThrow(()->{
            service.sendInvoice(accountIDofIssuer.toString(), usernameOfPayer, new Amount(1));
        });
    }

    @Test
    void sendInvoiceHappyPathMoneypool() throws Exception {
        Depot mockedIssuerDepot = mock(Moneypool.class);

        when(mockedUserRepository.getUserFrom(moneypoolIDofIssuer)).thenReturn(mockedIssuer);
        when(mockedIssuer.getDepotBy(moneypoolIDofIssuer)).thenReturn(mockedIssuerDepot);
        when(mockedIssuerDepot.getBalance()).thenReturn(new Balance(0));

        when(mockedUserRepository.getUserFrom(usernameOfPayer)).thenReturn(mockedPayer);

        InvoiceService service = new InvoiceService(mockedUserRepository, mockedInvoiceRepository, mockedTransferRepository);
        assertDoesNotThrow(()->{
            service.sendInvoice(moneypoolIDofIssuer.toString(), usernameOfPayer, new Amount(1));
        });
    }

    @Test
    void InvoiceToUnknownUser() throws InvalidUsernameException {
        Depot mockedIssuerDepot = mock(Moneypool.class);

        when(mockedUserRepository.getUserFrom(moneypoolIDofIssuer)).thenReturn(mockedIssuer);
        when(mockedIssuer.getDepotBy(moneypoolIDofIssuer)).thenReturn(mockedIssuerDepot);

        InvoiceService service = new InvoiceService(mockedUserRepository, mockedInvoiceRepository, mockedTransferRepository);
        Username unknownUser = new Username("UNKNOWN_USER");
        assertThrows(UnknownUserAggregateException.class, ()->{
            service.sendInvoice(moneypoolIDofIssuer.toString(), unknownUser, new Amount(1));
        });
    }

    @Test
    public void negativeInvoice(){
        Depot mockedIssuerDepot = mock(Moneypool.class);

        when(mockedUserRepository.getUserFrom(moneypoolIDofIssuer)).thenReturn(mockedIssuer);
        when(mockedIssuer.getDepotBy(moneypoolIDofIssuer)).thenReturn(mockedIssuerDepot);
        //when(mockedIssuerDepot.getBalance()).thenReturn(new Balance(0));

        when(mockedUserRepository.getUserFrom(this.usernameOfPayer)).thenReturn(mockedPayer);

        InvoiceService service = new InvoiceService(mockedUserRepository, mockedInvoiceRepository, mockedTransferRepository);

        assertThrows(InvalidAmountException.class, ()->{
            service.sendInvoice(moneypoolIDofIssuer.toString(), usernameOfPayer, new Amount(-1));
        });
    }

    @Test
    public void payInvoiceHappyAccount() throws InvalidBalanceException, InvalidAmountException {
        Depot mockedIssuerDepot = mock(Account.class);
        Depot mockedPayerDepot = mock(Account.class);

        when(mockedUserRepository.getUserFrom(accountIDofIssuer)).thenReturn(mockedIssuer);
        when(mockedIssuer.getDepotBy(accountIDofIssuer)).thenReturn(mockedIssuerDepot);
        when(mockedIssuerDepot.getBalance()).thenReturn(new Balance(100));
        when(mockedIssuerDepot.getId()).thenReturn(accountIDofIssuer);

        when(mockedUserRepository.getUserFrom(accountIDofPayer)).thenReturn(mockedPayer);
        when(mockedPayer.getDepotBy(accountIDofPayer)).thenReturn(mockedPayerDepot);
        when(mockedPayerDepot.getBalance()).thenReturn(new Balance(100));
        when(mockedPayerDepot.getId()).thenReturn(accountIDofPayer);

        Invoice invoice = new Invoice(mockedIssuerDepot, mockedPayer, new Amount(10));
        when(mockedInvoiceRepository.get(invoice.getId())).thenReturn(invoice);

        InvoiceService service = new InvoiceService(mockedUserRepository, mockedInvoiceRepository, mockedTransferRepository);
        assertDoesNotThrow(()->{
            service.payInvoice(invoice.getId(), accountIDofPayer);
        });
    }

    @Test
    public void payInvoiceWrongPayer() throws InvalidBalanceException, InvalidAmountException {
        Depot mockedIssuerDepot = mock(Account.class);
        Depot mockedPayerDepot = mock(Account.class);

        when(mockedUserRepository.getUserFrom(accountIDofIssuer)).thenReturn(mockedIssuer);
        when(mockedIssuer.getDepotBy(accountIDofIssuer)).thenReturn(mockedIssuerDepot);
        when(mockedIssuerDepot.getBalance()).thenReturn(new Balance(100));
        when(mockedIssuerDepot.getId()).thenReturn(accountIDofIssuer);

        when(mockedUserRepository.getUserFrom(accountIDofPayer)).thenReturn(mockedPayer);
        when(mockedPayer.getDepotBy(accountIDofPayer)).thenReturn(null);
        when(mockedPayerDepot.getBalance()).thenReturn(new Balance(100));
        when(mockedPayerDepot.getId()).thenReturn(accountIDofPayer);

        Invoice invoice = new Invoice(mockedIssuerDepot, mockedPayer, new Amount(10));
        when(mockedInvoiceRepository.get(invoice.getId())).thenReturn(invoice);

        InvoiceService service = new InvoiceService(mockedUserRepository, mockedInvoiceRepository, mockedTransferRepository);
        assertThrows(DepotNotFoundException.class, ()->{
            service.payInvoice(invoice.getId(), accountIDofPayer);
        });
    }

    @Test
    public void payInvoiceInsufficientAmount() throws InvalidBalanceException, InvalidAmountException {
        Depot mockedIssuerDepot = mock(Account.class);
        Depot mockedPayerDepot = mock(Account.class);

        when(mockedUserRepository.getUserFrom(accountIDofIssuer)).thenReturn(mockedIssuer);
        when(mockedIssuer.getDepotBy(accountIDofIssuer)).thenReturn(mockedIssuerDepot);
        when(mockedIssuerDepot.getBalance()).thenReturn(new Balance(100));
        when(mockedIssuerDepot.getId()).thenReturn(accountIDofIssuer);

        when(mockedUserRepository.getUserFrom(accountIDofPayer)).thenReturn(mockedPayer);
        when(mockedPayer.getDepotBy(accountIDofPayer)).thenReturn(mockedPayerDepot);
        when(mockedPayerDepot.getBalance()).thenReturn(new Balance(0));
        when(mockedPayerDepot.getId()).thenReturn(accountIDofPayer);

        Invoice invoice = new Invoice(mockedIssuerDepot, mockedPayer, new Amount(10));
        when(mockedInvoiceRepository.get(invoice.getId())).thenReturn(invoice);

        InvoiceService service = new InvoiceService(mockedUserRepository, mockedInvoiceRepository, mockedTransferRepository);
        assertThrows(InvalidBalanceException.class,()->{
            service.payInvoice(invoice.getId(), accountIDofPayer);
        });
    }

//    TODO: Bug, does not execute the Override Methode of Moneypool setBalance()
//    @Test
//    public void payInvoiceWithMoneypool() throws InvalidBalanceException, InvalidAmountException {
//        Depot mockedIssuerDepot = mock(Account.class);
//        Depot mockedPayerDepot = mock(Moneypool.class);
//
//        when(mockedUserRepository.getUserFrom(accountIDofIssuer)).thenReturn(mockedIssuer);
//        when(mockedIssuer.getDepotBy(accountIDofIssuer)).thenReturn(mockedIssuerDepot);
//        when(mockedIssuerDepot.getBalance()).thenReturn(new Balance(100));
//        when(mockedIssuerDepot.getId()).thenReturn(accountIDofIssuer);
//
//        when(mockedUserRepository.getUserFrom(accountIDofPayer)).thenReturn(mockedPayer);
//        when(mockedPayer.getDepotBy(accountIDofPayer)).thenReturn(mockedPayerDepot);
//        when(mockedPayerDepot.getBalance()).thenReturn(new Balance(100));
//        when(mockedPayerDepot.getId()).thenReturn(accountIDofPayer);
//
//        Invoice invoice = new Invoice(mockedIssuerDepot, mockedPayer, new Amount(10));
//        when(mockedInvoiceRepository.get(invoice.getId())).thenReturn(invoice);
//
//        InvoiceService service = new InvoiceService(mockedUserRepository, mockedInvoiceRepository, mockedTransferRepository);
//        assertThrows(TransferOutOfMoneypoolException.class,()->{
//            service.payInvoice(invoice.getId(), accountIDofPayer);
//        });
//    }
}