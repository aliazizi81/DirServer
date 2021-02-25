package biz;

import dao.NameValueDao;
import dao.NameValueDaoFactory;
import entity.NameValue;
import java.net.Socket;
import biz.Protocol;

public class Facade {

    private Socket incoming;

    Facade(Socket incoming) {
        this.incoming = incoming;
    }

    public void insert(NameValue nv) throws Exception {



        NameValueDao nvd = NameValueDaoFactory.getDao();
        int r = nvd.insert(nv);
        if (r == 1) {
            new Protocol(incoming).sendMessage("the name already exists!");
        } else {
            new Protocol(incoming).sendMessage("successfuly inserted!");
        }

    }
    public NameValue findByName(String nm) throws Exception{
        
        NameValueDao nvd = NameValueDaoFactory.getDao();
        NameValue nv = nvd.findByName(nm);
        return nv;
               
    }
    
}