
package dao;

public class NameValueDaoFactory {
    public static NameValueDao getDao() {
        
        NameValueDao dao = new MySqlDaoImpl();
        return dao;
        
    }
}
