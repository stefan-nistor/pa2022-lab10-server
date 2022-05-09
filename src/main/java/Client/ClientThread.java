package Client;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import user.User;

import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.util.List;

@Data
@Getter
@Setter
public class ClientThread extends Thread{
    private final Socket socket;
    private final Connection conn;
    private List<User> users;
    private boolean hasEnded = false;
    private boolean isLoggedIn = false;
    private boolean isMessageSend = false;
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
}
