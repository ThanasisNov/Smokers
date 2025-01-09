import java.util.Random;

public class Server implements Runnable {
    private Table table;
    private String name; // "S12", "S13", ή "S23"
    private int resA;
    private int resB;
    private Random rand = new Random();

    public Server(Table table, String name, int rA, int rB) {
        this.table = table;
        this.name = name;
        this.resA = rA;
        this.resB = rB;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Αποκτούμε πρόσβαση στο τραπέζι
                System.out.println(name + " acquiring tableEmpty...");
                table.tableEmpty.acquire();
                System.out.println(name + " acquired tableEmpty.");

                // Παρέχουμε τους δύο πόρους που αντιστοιχούν σε αυτόν τον Server
                provideResource(resA);
                provideResource(resB);

                // Μικρή καθυστέρηση για να «προλάβει» το σύστημα να λειτουργήσει
                Thread.sleep(rand.nextInt(300) + 200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void provideResource(int resource) {
        switch (resource) {
            case 1:
                System.out.println(name + " provides r1");
                table.r1.release();
                break;
            case 2:
                System.out.println(name + " provides r2");
                table.r2.release();
                break;
            case 3:
                System.out.println(name + " provides r3");
                table.r3.release();
                break;
        }
    }
}
