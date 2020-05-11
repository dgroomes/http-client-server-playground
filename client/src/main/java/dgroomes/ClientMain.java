package dgroomes;

public class ClientMain {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        System.out.println(new ClientMain().getGreeting());
    }
}
