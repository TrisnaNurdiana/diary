/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diary;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author TRISNA NURDIANA
 */
public class Model 
{
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty des = new SimpleStringProperty();
    private final StringProperty dat = new SimpleStringProperty();
    private final StringProperty writer = new SimpleStringProperty();
    
    public Model(){}
    
    public String getTitle()
    {
        return title.get();
    }
    public void setTitle(String value)
    {
        title.set(value);
    }
    public StringProperty TitleProperty()
    {
        return title;
    }
    public String getDes()
    {
        return des.get();
    }
    public void setDes(String value)
    {
        des.set(value);
    }
    public StringProperty DesProperty()
    {
        return des;
    }
    public String getDat()
    {
        return dat.get();
    }
    public void setDat(String value)
    {
        dat.set(value);
    }
    public StringProperty DatProperty()
    {
        return dat;
    }
    public String getWriter()
    {
        return writer.get();
    }
    public void setWriter(String value)
    {
        writer.set(value);
    }
    public StringProperty WriterProperty()
    {
        return writer;
    }
    
    
}
