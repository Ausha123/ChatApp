import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientUiController {
    public TextArea txtMessageArea;
    public JFXTextField txtMessage;

    static Socket socket=null;
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;

    public void initialize(){
          new Thread(()->{
              try{

                  socket = new Socket("localhost",5000);
                  dataInputStream=new DataInputStream(socket.getInputStream());
                  dataOutputStream=new DataOutputStream(socket.getOutputStream());

                  String messageIn="";

                  while (!messageIn.equals("end")){
                      messageIn=dataInputStream.readUTF();
                      txtMessageArea.appendText("\nServe:" + messageIn.trim());
                  }

              }catch (IOException e){
                  e.printStackTrace();
              }
        }).start();


    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        String reply = "";
        reply= txtMessage.getText();
        dataOutputStream.writeUTF(reply);
    }
}
