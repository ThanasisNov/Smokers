import java.util.concurrent.Semaphore;
public class Client implements Runnable {
    private Table table;
    private String name;  // "C12", "C13", "C23"
    private Semaphore mySem;  // Semaphore που περιμένει ο Client

    public Client(Table table, String name, Semaphore sem) {
        this.table = table;
        this.name = name;
        this.mySem = sem;
    }

    @Override
    public void run() {
        try {
            while(true) {
                System.out.println(name + " waiting for resources...");
                mySem.acquire(); // Περιμένει το κατάλληλο signal από τους pushers
                System.out.println(name + " acquired both resources! Consuming and releasing table...");
                // Απελευθερώνει και τις 2 θέσεις στο τραπέζι για τον επόμενο κύκλο
                table.tableEmpty.release(2);
                Thread.sleep(500); // Καθυστέρηση για προσομοίωση κατανάλωσης
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
