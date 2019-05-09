/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ta1.conn.Conn;

/**
 *
 * @author TRISNA NURDIANA
 */
public class Impl 
{
    Conn con;
    Integer urut = 0;
    public void insertDiary(Model m)
    {
        con = new Conn();
        PreparedStatement ps;
        String sql ="insert into diary values(?,?,?,?);";
        try{
            ps = con.connect().prepareStatement(sql);
            ps.setString(1, m.getTitle());
            ps.setString(2, m.getDes());
            ps.setString(3, m.getDat());
            ps.setString(4, m.getWriter());
            ps.executeUpdate();
        }catch(Exception e)
        {
            Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void deleteDiary(String id)
    {
        con = new Conn();
        PreparedStatement ps;
        String sql ="delete from diary where title='"+id+"'";
        try{
            ps = con.connect().prepareStatement(sql);
            ps.executeUpdate();
        }catch(Exception e)
        {
            Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public ObservableList<Model> getAll()
    {
        con = new Conn();
        ObservableList<Model> listData = FXCollections.observableArrayList();
        try{
            String sql = "select * from diary";
            ResultSet rs = con.connect().createStatement().executeQuery(sql);
            while(rs.next())
            {
                urut = urut + 1;
                String isi = urut.toString();
                Model m = new Model();
                m.setTitle(rs.getString("title"));
                m.setDes(rs.getString("description"));
                m.setDat(rs.getString("date"));
                m.setWriter(rs.getString("writer"));
                listData.add(m);
                
            }
            urut = 0;
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
        return listData;
    }
    
    
}
