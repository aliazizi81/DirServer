/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package old;

import dao.NameValueDao;
import dao.NameValueDaoFactory;
import entity.NameValue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import org.hibernate.lob.BlobImpl;

public class NameValueFacade {

    public int ins(NameValue nv) {

        int r = 0;
        try {
            if (!nv.isIsFile()) {

                NameValueDao nvd = NameValueDaoFactory.getDao();
                r = nvd.insert(nv);

            } else {

                File f = new File("MyFiles\\" + nv.getValue());
                FileInputStream inf = new FileInputStream(f);

                Blob blob = new BlobImpl(inf, (int) f.length());
                nv.setfValue(blob);

                NameValueDao nvd = NameValueDaoFactory.getDao();
                r = nvd.insert(nv);

                inf.close();
                if (r == 0) {
                    f.delete();
                }

            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    public NameValue findByName(String nm) {

        NameValueDao nvd = NameValueDaoFactory.getDao();
        NameValue nv = nvd.findByName(nm);
        if (nv.isIsFile()) {
            Blob blob = nv.getfValue();
            try {

                File fil = new File("MyFiles\\" + nv.getValue());
                if (fil.exists()) {
                    fil.delete();
                }
                FileOutputStream f = new FileOutputStream(fil);
                f.write(blob.getBytes(1, (int) blob.length()), 0, (int) blob.length());
                f.close();

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return nv;
    }
}
