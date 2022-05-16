package socket;

import client.ClientThread;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import user.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class SocketManager {
    private static final Integer PORT = 8080;
    private boolean hasEnded = false;
    private Connection con;
    private List<User> users = new ArrayList<>();
    private List<ClientThread> activeThreads = new ArrayList<>();

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientThread currentClient = new ClientThread(socket, this.con, users);
                this.activeThreads.add(currentClient);
                currentClient.start();
                try {
                    currentClient.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
