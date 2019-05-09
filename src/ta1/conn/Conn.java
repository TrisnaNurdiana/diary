/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ta1.conn;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author TRISNA NURDIANA
 */
public class Conn {
    private Connection con;
    
    public Connection connect()
    {
     if(con == null)
        {
            MysqlDataSource db = new MysqlDataSource();
            db.setDatabaseName("diary");
            db.setUser("root");
            db.setPassword("");
            try{
                con = db.getConnection();
            }catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null,"KONEKSI TIDAK TERHUBUNG");
                System.err.println("DISCONNECT");
                System.exit(0);
            }
        }
     return con;
    }
    
}
