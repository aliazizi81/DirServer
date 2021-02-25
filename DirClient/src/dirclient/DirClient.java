package dirclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import com.google.gson.Gson;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;

public class DirClient {

    static private Socket outgoing;
    static String ip = "127.0.0.1";
    static int port = 1234;
    static String sourcePath = "MyFiles\\";

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int command;// = sc.nextInt();
        while (true) {
            System.out.println("please enter your command:");
            command=sc.nextInt();
            if (command==1){create("ali.jpg", "ali33", "wm.jpg");}
            if (command==2){find("ali32");}
            if (command==0){end();break;}
            
        }
        
    }

    public static void create(String fName, String name, String value) throws Exception {

        byte[] block = new byte[256 * 1024];
        int len;

        try {
            TransferedObject to = new TransferedObject(1, true, name, value, "");

            byte[] f = new DirClient().readUserFile(sourcePath + fName);
            String str = DatatypeConverter.printBase64Binary(f);
            to.setfValue(str);

            Gson gson = new Gson();
            String jsonString = gson.toJson(to, TransferedObject.class);

            System.out.println("Uploading... please wait...");
            if (outgoing == null) {
                outgoing = new Socket(ip, port);
            }


            OutputStream os = outgoing.getOutputStream();
            PrintStream ps = new PrintStream(os);
            InputStream is = outgoing.getInputStream();
            Scanner sc = new Scanner(is);
            ps.println(jsonString);
            System.out.println(sc.nextLine() + " from server.");
            System.out.println(sc.nextLine() + " from server.");


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Upload completed");


    }

    public static void find(String name) throws Exception {

        if (outgoing == null) {
            outgoing = new Socket(ip, port);
        }
        TransferedObject to = new TransferedObject(2, name);

        Gson gson = new Gson();
        String jsonString = gson.toJson(to, TransferedObject.class);

        System.out.println("Sending find Command...");

        OutputStream os = outgoing.getOutputStream();
        PrintStream ps = new PrintStream(os);
        InputStream is = outgoing.getInputStream();
        Scanner sc = new Scanner(is);
        ps.println(jsonString);
        System.out.println(sc.nextLine() + " from server.");

        TransferedObject rto = recieveJson();
        ps.println("your answer for my \"" + name + "\" find request, recieved!");
        if (rto.getCommand() == 4) {
            System.out.println("the name dosen't exist in Dir Server");
        } else if (rto.getCommand() == 3) {
            if (!rto.isIsFile()) {
                System.out.println("the value is " + rto.getValue());
            } else {
                FileOutputStream f = new FileOutputStream(sourcePath + rto.getValue());
                f.write(DatatypeConverter.parseBase64Binary(rto.getfValue()));
                f.close();
                System.out.println("your value is a file at " + sourcePath + rto.getValue());
            }
        }



    }

    public static void end() throws Exception {
        TransferedObject to = new TransferedObject(0);
        Gson gson = new Gson();
        String jsonString = gson.toJson(to, TransferedObject.class);
        if (outgoing == null) {
            outgoing = new Socket(ip, port);
        }
        //System.out.println(jsonString);
        OutputStream os = outgoing.getOutputStream();
        PrintStream ps = new PrintStream(os);
        InputStream is = outgoing.getInputStream();
        Scanner sc = new Scanner(is);
        ps.println(jsonString);
        System.out.println(sc.nextLine() + " from server.");
        System.out.println(sc.nextLine() + " from server.");

    }

    private byte[] readUserFile(String path) throws Exception {

        FileInputStream f = new FileInputStream(path);
        File ff = new File(path);

        byte[] b = new byte[(int) ff.length()];
        f.read(b);
        f.close();

        return b;

    }

    public static TransferedObject recieveJson() throws Exception {

        InputStream is = outgoing.getInputStream();
        Scanner sc = new Scanner(is);
        String jsonStr = sc.nextLine();
        Gson gson = new Gson();
        TransferedObject to = gson.fromJson(jsonStr, TransferedObject.class);

        return to;

    }

    /*private void saveAsJson(TransferedObject nv, String path) {

     Gson gson = new Gson();
     String jsonString = gson.toJson(nv, TransferedObject.class);

     try {
     OutputStream out = new FileOutputStream(path);
     PrintStream p = new PrintStream(out);
     p.print(jsonString);
     p.close();
     out.close();
     } catch (IOException e) {
     e.printStackTrace();
     }

     }*/

    /* private void test(Socket s) throws Exception {
     OutputStream os = s.getOutputStream();
     os.write(10);
     os.close();
     }*/
}
