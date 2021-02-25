package biz;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
//import com.google.gson.stream.JsonReader;

public class DirServer {

    private static ServerSocket listener;
    //private static ArrayList<Socket> arr;
    //protected static Handler h = null;
    public static String confPath = "config.properties";

    public static void main(String[] args) throws Exception {


        
        Properties p = new Properties();
        p.load(new FileInputStream(confPath));
        int port = Integer.valueOf(p.getProperty("port"));
        
        

        listener = new ServerSocket(port);
        Socket s;

        DirServerLog.log("DirServer Started Successfully!");
        
                                                                                                                                                                                                                
       /*int adminCommand;
       Scanner sc=new Scanner(System.in);
       System.out.println("if You want to stop server please enter zero:");
       while (sc.nextInt()!=0){*/
        while (true) {
            s = listener.accept();
            DirServerLog.log("New Client from "+s.getInetAddress()+" Accpeted");
            new PerClient(s).start();
        }
        //}
    }

  /*  protected void finalize() throws Exception{
       // if (h != null) {
            myLog.log(Level.INFO, "DirServer stoped Successfully!");
            System.out.println("hhhhhh");
            h.close();
            myLog.removeHandler(h);
      //  }
        //super.finalize();
    }*/
   
}
