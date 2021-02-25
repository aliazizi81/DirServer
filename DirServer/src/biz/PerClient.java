package biz;

//import static biz.DirServer.myLog;
import entity.NameValue;
import java.net.Socket;
import java.util.logging.Level;

public class PerClient extends Thread {

    private Socket incoming;
    private String username;

    public PerClient(Socket incoming) {
        this.incoming = incoming;
    }

    public void run() {
        try {
            Protocol p = new Protocol(incoming);
            int end = 0;
            while (end == 0) {

                TransferedObject to = p.recieveJson();
                NameValue nv = p.createEntity(to);

                int command = to.getCommand();

                if (command == 1) {

                    new Facade(incoming).insert(nv);
                    DirServerLog.log("The user:"+username+" The Command:Create.");

                } else if (command == 2) {

                    NameValue snv = new Facade(incoming).findByName(to.getName());
                    TransferedObject sto = p.createTransferedObject(snv);
                    p.sendJson(sto);
                    DirServerLog.log("The user:"+username+" The Command:Find.");
                    
                } else if (command == 0) {

                    p.sendMessage("The connection closed, Goodbye!");
                    System.out.println("Client Left!");
                    incoming.close();
                    end = 1;
                    DirServerLog.log("The user:"+username+" The Command:Close Connection.");

                } else {
                    p.sendMessage("The user:"+username+" The Command:Wrong Command!");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
