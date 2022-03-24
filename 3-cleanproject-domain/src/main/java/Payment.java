public class Payment extends Transfer {

    private Invoice invoice;

    public Payment(Invoice invoice, Depot sender) {
        super(sender, invoice.getBiller(), invoice.getAmount());
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
