package biz;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.FileHandler;
import java.util.logging.StreamHandler;

public class DirServerLog {

    private static Logger logger = null;

    private DirServerLog() {
    }

    static {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(DirServer.confPath));
            String logEnabled = p.getProperty("logger");
            if (logEnabled.equals("true")) {
                String logAddr = p.getProperty("logFileAddr");
                String logName = p.getProperty("loggerName");
                logger = Logger.getLogger(logAddr);
                Handler h = new StreamHandler(new FileOutputStream(logAddr), new SimpleFormatter());

                //FileHandler fh=new FileHandler();
                //fh.
                logger.addHandler(h);
                //LogManager lm=LogManager.getLogManager();
                //lm.new();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(String st) {

        synchronized (logger) {
            if (logger != null) {
                logger.log(Level.INFO, st);
                Handler[] harray = logger.getHandlers();
                for (Handler h : harray) {
                    h.flush();
                }
            }
        }

    }
    /* if (logger.equals("true")) {
     //System.out.println("hallow");
     String logAddr = p.getProperty("logFileAddr");
     String logName = p.getProperty("loggerName");
     myLog = Logger.getLogger(logName);
     h = 
     myLog.addHandler(h);
     myLog.log(Level.INFO, "DirServer started Successfully!");
     //return;
     //myLog.
     //f.close();
     }*/
}
