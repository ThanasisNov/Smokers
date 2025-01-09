import java.util.concurrent.Semaphore;

public class Client implements Runnable {
    private Table table;
    private String name;  // "C12", "C13", "C23"
    private Semaphore mySem;  // semaphore που πρέπει να περιμένει

    public Client(Table table, String name, Semaphore sem) {
        this.table = table;
        this.name = name;
        this.mySem = sem;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(name + " waiting for resources...");
                mySem.acquire(); // Περιμένει τον Pusher να τον ξυπνήσει
                System.out.println(name + " acquired both resources! Releasing tableEmpty...");
                table.tableEmpty.release(); // Απελευθερώνει το τραπέζι για νέους πόρους
                Thread.sleep(500); // Καθυστέρηση κατανάλωσης
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
