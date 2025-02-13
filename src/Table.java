import java.util.concurrent.Semaphore;

public class Table {
    // Semaphores για τους πόρους (fair)
    public Semaphore r1 = new Semaphore(0, true);
    public Semaphore r2 = new Semaphore(0, true);
    public Semaphore r3 = new Semaphore(0, true);

    // Semaphores για τους πελάτες (fair)
    public Semaphore client12 = new Semaphore(0, true);
    public Semaphore client13 = new Semaphore(0, true);
    public Semaphore client23 = new Semaphore(0, true);

    // Το τραπέζι έχει 2 θέσεις για πόρους (fair)
    public Semaphore tableEmpty = new Semaphore(2, true);

    // Flags για τους πόρους που υπάρχουν ήδη στο τραπέζι
    public boolean hasR1 = false;
    public boolean hasR2 = false;
    public boolean hasR3 = false;

    // Mutex για συγχρονισμό στους pushers (fair)
    public Semaphore mutex = new Semaphore(1, true);

    // Semaphores για round-robin scheduling των servers (fair)
    public Semaphore semS12 = new Semaphore(1, true);  // S12 ξεκινάει (permit 1)
    public Semaphore semS13 = new Semaphore(0, true);
    public Semaphore semS23 = new Semaphore(0, true);
}
