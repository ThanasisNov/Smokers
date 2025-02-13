import java.util.Random;
import java.util.concurrent.Semaphore;

public class Server implements Runnable {
    private Table table;
    private String name; // "S12", "S13", "S23"
    private int resA;    // Πρώτος πόρος που μπορεί να δώσει
    private int resB;    // Δεύτερος πόρος που μπορεί να δώσει
    private int lastResource = -1;  // Για εναλλαγή πόρων
    private Random rand = new Random();
    private Semaphore mySem;   // Το semaphore του συγκεκριμένου server (για round-robin)
    private Semaphore nextSem; // Το semaphore του επόμενου server στη σειρά

    public Server(Table table, String name, int resA, int resB, Semaphore mySem, Semaphore nextSem) {
        this.table = table;
        this.name = name;
        this.resA = resA;
        this.resB = resB;
        this.mySem = mySem;
        this.nextSem = nextSem;
    }

    @Override
    public void run() {
        try {
            while(true) {
                // Αναμονή μέχρι να φτάσει η σειρά (round-robin)
                mySem.acquire();
                // Καταλαμβάνουμε μία θέση στο τραπέζι για την παραγωγή πόρου
                table.tableEmpty.acquire();

                // Επιλογή πόρου: εναλλαγή ώστε να μην παράγει συνεχόμενα τον ίδιο
                int chosen;
                if(lastResource == resA) {
                    chosen = resB;
                } else if(lastResource == resB) {
                    chosen = resA;
                } else {
                    chosen = rand.nextBoolean() ? resA : resB;
                }
                lastResource = chosen;
                System.out.println(name + " [Server] will provide resource r" + chosen);
                provideResource(chosen);

                // Δεν απελευθερώνουμε εδώ το tableEmpty – ο Client το απελευθερώνει μετά την κατανάλωση.

                // Σήμα στο επόμενο server για να πάρει τη σειρά
                nextSem.release();

                // Προαιρετική καθυστέρηση πριν την επόμενη παραγωγή
                Thread.sleep(rand.nextInt(300) + 200);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void provideResource(int resource) {
        switch(resource) {
            case 1:
                System.out.println(name + " signals r1");
                table.r1.release();
                break;
            case 2:
                System.out.println(name + " signals r2");
                table.r2.release();
                break;
            case 3:
                System.out.println(name + " signals r3");
                table.r3.release();
                break;
        }
    }
}
