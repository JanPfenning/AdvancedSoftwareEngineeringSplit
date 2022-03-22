public class Payment extends Transfer {

    private Invoice invoice;

    public Payment(Invoice invoice) {
        super(invoice.getRecipient(), invoice.getBiller(), invoice.getAmount());
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
