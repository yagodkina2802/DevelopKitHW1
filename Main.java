package lesson1;

public class Main {
    public static void main(String[] args) {

        Server server = new Server(new ServerWindow(), new FileStorage());

        new Client(new ClientGUI(), server);
        new Client(new ClientGUI(), server);

    }
}
