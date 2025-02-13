public class Pusher implements Runnable {
    private Table table;
    private int resourceId; // 1 -> r1, 2 -> r2, 3 -> r3

    public Pusher(Table table, int resourceId) {
        this.table = table;
        this.resourceId = resourceId;
    }

    @Override
    public void run() {
        try {
            while(true) {
                switch(resourceId) {
                    case 1:
                        System.out.println("Pusher(1): Waiting for r1...");
                        table.r1.acquire();
                        System.out.println("Pusher(1): Detected r1. Acquiring mutex...");
                        table.mutex.acquire();
                        if(table.hasR2) {
                            System.out.println("Pusher(1): Found r2. Signaling client12...");
                            table.hasR2 = false;
                            table.client12.release();
                        } else if(table.hasR3) {
                            System.out.println("Pusher(1): Found r3. Signaling client13...");
                            table.hasR3 = false;
                            table.client13.release();
                        } else if(table.hasR1) {
                            System.out.println("Pusher(1): Duplicate r1 received, discarding resource.");
                            table.tableEmpty.release();
                        } else {
                            System.out.println("Pusher(1): No pairs found. Marking hasR1=true.");
                            table.hasR1 = true;
                        }
                        table.mutex.release();
                        break;
                    case 2:
                        System.out.println("Pusher(2): Waiting for r2...");
                        table.r2.acquire();
                        System.out.println("Pusher(2): Detected r2. Acquiring mutex...");
                        table.mutex.acquire();
                        if(table.hasR1) {
                            System.out.println("Pusher(2): Found r1. Signaling client12...");
                            table.hasR1 = false;
                            table.client12.release();
                        } else if(table.hasR3) {
                            System.out.println("Pusher(2): Found r3. Signaling client23...");
                            table.hasR3 = false;
                            table.client23.release();
                        } else if(table.hasR2) {
                            System.out.println("Pusher(2): Duplicate r2 received, discarding resource.");
                            table.tableEmpty.release();
                        } else {
                            System.out.println("Pusher(2): No pairs found. Marking hasR2=true.");
                            table.hasR2 = true;
                        }
                        table.mutex.release();
                        break;
                    case 3:
                        System.out.println("Pusher(3): Waiting for r3...");
                        table.r3.acquire();
                        System.out.println("Pusher(3): Detected r3. Acquiring mutex...");
                        table.mutex.acquire();
                        if(table.hasR1) {
                            System.out.println("Pusher(3): Found r1. Signaling client13...");
                            table.hasR1 = false;
                            table.client13.release();
                        } else if(table.hasR2) {
                            System.out.println("Pusher(3): Found r2. Signaling client23...");
                            table.hasR2 = false;
                            table.client23.release();
                        } else if(table.hasR3) {
                            System.out.println("Pusher(3): Duplicate r3 received, discarding resource.");
                            table.tableEmpty.release();
                        } else {
                            System.out.println("Pusher(3): No pairs found. Marking hasR3=true.");
                            table.hasR3 = true;
                        }
                        table.mutex.release();
                        break;
                }
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
