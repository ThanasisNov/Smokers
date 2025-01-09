public class Main {
    public static void main(String[] args) {
        Table table = new Table();
        // 3 pushers
        Thread p1 = new Thread(new Pusher(table, 1));
        Thread p2 = new Thread(new Pusher(table, 2));
        Thread p3 = new Thread(new Pusher(table, 3));
        p1.start(); p2.start(); p3.start();

        // 3 clients
        Thread c12 = new Thread(new Client(table, "C12", table.client12));
        Thread c13 = new Thread(new Client(table, "C13", table.client13));
        Thread c23 = new Thread(new Client(table, "C23", table.client23));
        c12.start(); c13.start(); c23.start();

        // 3 servers
        Thread s12 = new Thread(new Server(table, "S12", 1, 2));
        Thread s13 = new Thread(new Server(table, "S13", 1, 3));
        Thread s23 = new Thread(new Server(table, "S23", 2, 3));
        s12.start(); s13.start(); s23.start();
    }

}
