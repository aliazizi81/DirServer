package biz;

import com.google.gson.Gson;
import entity.NameValue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Blob;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;
import org.hibernate.lob.BlobImpl;
//import org.hibernate.lob.BlobImpl;

public class Protocol {

    private Socket incoming;

    Protocol(Socket incoming) {
        this.incoming = incoming;
    }

    public TransferedObject recieveJson() throws Exception {

        InputStream is = incoming.getInputStream();
        Scanner sc = new Scanner(is);
        String jsonStr = sc.nextLine();
        sendMessage("your request recieved successfully!");
        Gson gson = new Gson();
        TransferedObject to = gson.fromJson(jsonStr, TransferedObject.class);

        return to;

    }

    public NameValue createEntity(TransferedObject nv) throws Exception {

        if (nv == null) {
            return null;
        }

        boolean isFile = nv.isIsFile();
        String name = nv.getName();
        String value = nv.getValue();
        String fValue64 = nv.getfValue();

        entity.NameValue env = new entity.NameValue(isFile, name, value);
        if (isFile) {

            //using jaxb rather than my own class
            env.setfValue(new BlobImpl(DatatypeConverter.parseBase64Binary(fValue64)));
        }
        return env;
    }

    public TransferedObject createTransferedObject(NameValue nv) throws Exception {

        int command;
        if (nv == null) {
            command = 4;//not found
            TransferedObject to = new TransferedObject(command);
            return to;
        } else {
            boolean isFile = nv.isIsFile();
            String name = nv.getName();
            String value = nv.getValue();
            Blob fValueBlob = nv.getfValue();
            if (nv != null) {
                command = 3;//found
            } else {
                command = 4;//not found
            }
            TransferedObject to = new TransferedObject(command, isFile, name, value, "");
            if (isFile) {
                byte[] b = fValueBlob.getBytes(1, (int) fValueBlob.length());
                //using jaxb rather than my own class
                String str = DatatypeConverter.printBase64Binary(b);
                to.setfValue(str);
            }
            return to;
        }
    }

    public void sendJson(TransferedObject to) throws Exception {

        Gson gson = new Gson();
        String jsonString = gson.toJson(to, TransferedObject.class);

        System.out.println("Uploading... please wait...");
        OutputStream os = incoming.getOutputStream();
        PrintStream ps = new PrintStream(os);
        InputStream is = incoming.getInputStream();
        Scanner sc = new Scanner(is);
        ps.println(jsonString);
        System.out.println("Uploading Completed!");
        System.out.println(sc.nextLine() + " from client.");

    }

    public void sendMessage(String str) throws Exception {

        OutputStream out = incoming.getOutputStream();
        PrintStream pw = new PrintStream(out);
        pw.println(str);
        //pw.close();
        //out.close();

    }

    /*public File readJSonFile() throws Exception {

     String path = "MyFiles\\temp.json";
     OutputStream os = new FileOutputStream(path);
     File f = new File(path);
     InputStream is = incoming.getInputStream();
     byte[] block = new byte[256 * 1024];
     int len;
     while ((len = is.read(block)) != -1) {
     os.write(block, 0, len);
     }
     os.close();
     is.close();
     return f;

     }*/
}