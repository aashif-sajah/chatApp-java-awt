import java.util.Scanner;


public class MainApp {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 'server' to start as server, or 'client' to start as client:");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("server")) {
            System.out.println("Starting server...");
            new server.App();
        } else if (input.equalsIgnoreCase("client")) {
            System.out.println("Starting client...");
            new client.App2(); 
        } else {
            System.out.println("Invalid input. Please type 'server' or 'client'.");
        }
        scanner.close();
    }
}
