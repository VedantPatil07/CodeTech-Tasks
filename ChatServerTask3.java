import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    static Vector<ClientHandler> clients = new Vector<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");
            ClientHandler handler = new ClientHandler(socket);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    // Inner class to handle each client
    static class ClientHandler implements Runnable {
        Socket socket;
        BufferedReader in;
        PrintWriter out;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    for (ClientHandler client : clients) {
                        if (client != this) {
                            client.out.println("Client says: " + message);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Client disconnected.");
            } finally {
                try {
                    socket.close();
                    clients.remove(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

