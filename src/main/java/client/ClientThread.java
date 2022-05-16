package client;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.util.List;

@Data
@Getter
@Setter
public class ClientThread extends Thread {
    private final Socket socket;
    private final Connection conn;
    private List<User> users;
    private boolean isStopped = false;
    private boolean isEnded = false;
    private User currentUser = null;

    public ClientThread(Socket socket, Connection conn, List<User> users) {
        this.users = users;
        this.conn = conn;
        this.socket = socket;
        try {
            this.socket.setSoTimeout(30000);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                String request = in.readLine();
                String response = null;
                request = request.toLowerCase();
                var parsedCommand = request.split("");
                switch (parsedCommand[0]) {
                    case "stop" -> {
                        response = "Server stopped";
                        System.out.println("Client has stopped execution.");
                        isStopped = true;
                    }
                    case "end" -> {
                        response = "Server ended execution.";
                        System.out.println("Client ended the server");
                        isEnded = true;
                    }
                    default -> response = "Command unrecognized. Please try again.";
                }
                out.println(response);
                out.flush();
                if (isStopped) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
