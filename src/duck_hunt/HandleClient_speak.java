/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt;
import static duck_hunt.Duck_hunt.ChattedMessage;
import static duck_hunt.Duck_hunt.WhoAmI;
import static duck_hunt.Duck_hunt.client_speak;
import static duck_hunt.Duck_hunt.server_speak;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class HandleClient_speak implements Runnable {

    //      Making necessary streams
    
    public String txt;
    private Socket socket = null;
    ObjectOutputStream out = null;
    public String name;
    
    public HandleClient_speak (Socket socket , String name) {
        this.socket = socket;
        this.name = name;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    //  nothing to do directly only does work when prompted from the window
    
    @Override
    public void run() {

        if(WhoAmI.equals("Server")){
            server_speak.suspend();
        }
        else if(WhoAmI.equals("Client")){
            client_speak.suspend();
        }

        while(true){
            try {
                send_msg(ChattedMessage);
            } catch (IOException ex) {
                Logger.getLogger(HandleClient_speak.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(WhoAmI.equals("Server")){
                    server_speak.suspend();
            }
            else if(WhoAmI.equals("Client")){
                    client_speak.suspend();
            }
        }
    }
        
    
    //         This function will Send Message
    
    public void send_msg( String text) throws IOException{
    
        String from = this.name + " : ";
        Message message = new Message(text, from, WhoAmI);
        out.writeObject(message);
        out.flush();
    
    }

}



    