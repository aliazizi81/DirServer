
package dao;
import entity.NameValue;
import java.util.List;

public interface NameValueDao {
    public int insert(NameValue nv);
    public NameValue findByName(String nm);
    public List findByNameMulty(String nam);
    
}
