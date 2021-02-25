package dao;

import entity.NameValue;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import java.util.List;
import org.hibernate.lob.*;

public class MySqlDaoImpl implements NameValueDao {

    private Session getSession() {

        Configuration config = new Configuration();
        config.configure();
        config.addClass(NameValue.class);
        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();
        return session;

    }

    @Override
    public int insert(NameValue nv) {
        NameValue test = new NameValue();
        test = findByName(nv.getName());
        if (test == null) {
            Session session = getSession();
            Transaction tx = session.beginTransaction();
            session.save(nv);
            tx.commit();
            session.close();
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public NameValue findByName(String nam) {

        NameValue nv = null;
        Session session = getSession();
        Transaction tx = session.beginTransaction();
  
        Query q = session.createQuery("select nv from  NameValue nv where nv.name = :nm");
        q.setString("nm", nam);
        
        nv = (NameValue) q.uniqueResult();
        tx.commit();
        
        session.close();
        return nv;


    }

    @Override
    public List findByNameMulty(String nam) {

        List list = null;
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select nv from  NameValue nv where nv.name = :nm");
        q.setString("nm", nam);
        list = q.list();
        tx.commit();
        session.close();
        return list;


    }
    /*
     @Override
     public void insert(NameValue nv) {

     Connection con = null;
     PreparedStatement pstmt = null;
     FileInputStream f = null;
     String insertQuery = "insert into name_value (is_file, name, value,f_value) values (?,?,?,?)";

     String url = "jdbc:mysql://localhost/dir";
     String un = "root";
     String pw = "hasan110";

     try {
     con = DriverManager.getConnection(url, un, pw);
     pstmt = con.prepareStatement(insertQuery);
     pstmt.setBoolean(1, nv.isIsFile());
     pstmt.setString(2, nv.getName());
     pstmt.setString(3, nv.getValue());
     pstmt.setBlob(4, (Blob) null);

     if (nv.isIsFile()) {
     try {
     f = new FileInputStream("MyFiles\\"+nv.getValue());
     pstmt.setBlob(4, f);
     } catch (Exception e) {
     e.printStackTrace();
     }
     }

     pstmt.executeUpdate();


     } catch (SQLException e) {
     e.printStackTrace();
     } finally {
     try {
     pstmt.close();
     } catch (Exception e) {
     e.printStackTrace();
     }
     try {
     con.close();
     } catch (Exception e) {
     e.printStackTrace();
     }
     if (f != null) {
     try {
     f.close();
     File f1 = new File("MyFiles\\"+nv.getValue());
     f1.delete();
     } catch (IOException e) {
     e.printStackTrace();
     }
     }
     }
     }
     */
}
