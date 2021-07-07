import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerUiController {
    public TextArea txtMessageArea;
    public TextField txtMessage;


    static ServerSocket serverSocket;
    static Socket socket;
    static DataOutputStream dataOutputStream;
    static DataInputStream  dataInputStream;
    String messageIn="";

    public void initialize(){
         new Thread(()->{
             try{

                 serverSocket=new ServerSocket(5000);
                 System.out.println("Server Started");
                 socket=serverSocket.accept();
                 System.out.println("Client Accepted!");

                 dataInputStream = new DataInputStream(socket.getInputStream());
                 dataOutputStream = new DataOutputStream(socket.getOutputStream());

                 while(!messageIn.equals("end")){
                     messageIn=dataInputStream.readUTF();
                     txtMessageArea.appendText("\n Client : "+messageIn.trim());
                 }

             }catch (IOException e){
                 e.printStackTrace();
             }
         }).start();

    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtMessage.getText().trim());
    }
}
