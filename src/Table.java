import java.util.concurrent.Semaphore;

public class Table {
    // Semaphores για πόρους
    public Semaphore r1 = new Semaphore(0);  // δηλώνει "βρέθηκε r1 στο τραπέζι"
    public Semaphore r2 = new Semaphore(0);  // δηλώνει "βρέθηκε r2 στο τραπέζι"
    public Semaphore r3 = new Semaphore(0);  // δηλώνει "βρέθηκε r3 στο τραπέζι"

    // Semaphores για τους πελάτες
    public Semaphore client12 = new Semaphore(0);  // C12 θέλει r1, r2
    public Semaphore client13 = new Semaphore(0);  // C13 θέλει r1, r3
    public Semaphore client23 = new Semaphore(0);  // C23 θέλει r2, r3

    // Ελεγχος προσβασιμότητας του τραπεζιού
    public Semaphore tableEmpty = new Semaphore(1); // Αν είναι 1, σημαίνει ότι μπορεί να ξαναμπεί "νέος" πόρος

    // Καταστάσεις αν υπάρχει ήδη κάποιος πόρος στο τραπέζι
    public boolean hasR1 = false;
    public boolean hasR2 = false;
    public boolean hasR3 = false;

    // Για απλούστευση, μπορούμε να χρησιμοποιήσουμε κι έναν κοινό mutex
    public Semaphore mutex = new Semaphore(1);
}
