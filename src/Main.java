public class Main {
    public static void main(String[] args) {
        Table table = new Table();

        // Εκκίνηση των Pusher
        new Thread(new Pusher(table, 1)).start();
        new Thread(new Pusher(table, 2)).start();
        new Thread(new Pusher(table, 3)).start();

        // Εκκίνηση των Clients
        new Thread(new Client(table, "C12", table.client12)).start();
        new Thread(new Client(table, "C13", table.client13)).start();
        new Thread(new Client(table, "C23", table.client23)).start();

        // Εκκίνηση των Servers με round-robin scheduling:
        // S12 -> S13 -> S23 -> S12 ...
        new Thread(new Server(table, "S12", 1, 2, table.semS12, table.semS13)).start();
        new Thread(new Server(table, "S13", 1, 3, table.semS13, table.semS23)).start();
        new Thread(new Server(table, "S23", 2, 3, table.semS23, table.semS12)).start();
    }
}
