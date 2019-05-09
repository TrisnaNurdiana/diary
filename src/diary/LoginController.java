/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diary;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.controlsfx.control.textfield.TextFields;
import ta1.conn.Conn;

/**
 * FXML Controller class
 *
 * @author TRISNA NURDIANA
 */
public class LoginController implements Initializable {
    Conn con;
    
    PreparedStatement ps = null;
    
    ResultSet resultSet = null;
    
    @FXML
    private TextField TFTitle;
    @FXML
    private TextArea TFDesc;
    @FXML
    private Button BSave;
    
    @FXML
    private TableView<Model> TVDiary;
    @FXML
    private TableColumn<Model, String> colTitle;
    @FXML
    private TableColumn<Model, String> colWriter;
    @FXML
    private TableColumn<Model, String> colDate;
    
    @FXML
    private ComboBox<String> CBWriter;
    @FXML
    private PasswordField PFPass;
    
    Impl crud = new Impl();
    
    @FXML
    private TextField TFDate;
    
    ObservableList<Model> listData;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        start();
        showCBWriter();
        actionSelect();
        selectTable();
    }    

    @FXML
    private void actionSave(ActionEvent event)
    {
        Model m = new Model();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            
        m.setTitle(TFTitle.getText());
        m.setDes(TFDesc.getText());
        m.setDat(TFDate.getText());
        m.setWriter(CBWriter.getValue());
        crud.insertDiary(m);
        JOptionPane.showMessageDialog(null, "data berhasil dimasukan!");
        clear();
    }

    public void selectTable()
    {
        TVDiary.setPlaceholder(new Label("DATA TIDAK DITEMUKAN"));
        colTitle.setCellValueFactory(
                (TableColumn.CellDataFeatures<Model, String> cellData) ->
                        cellData.getValue().TitleProperty());
        colWriter.setCellValueFactory(
                (TableColumn.CellDataFeatures<Model, String> cellData) ->
                        cellData.getValue().WriterProperty());
        colDate.setCellValueFactory(
                (TableColumn.CellDataFeatures<Model, String> cellData) ->
                        cellData.getValue().DatProperty());
        listData = FXCollections.observableArrayList();
        TVDiary.getSelectionModel().clearSelection();
    }
    public void actionSelect()
    {
        listData = crud.getAll();
        TVDiary.setItems(listData);
    } 
    
    @FXML
    private void klikTable(MouseEvent event)
    {
        try {
            Model klik = TVDiary.getSelectionModel().getSelectedItems().get(0);
            TFTitle.setText(klik.getTitle());
            TFDesc.setText(klik.getDes());
            TFDate.setText(klik.getDat());
            CBWriter.setValue(klik.getWriter());
            TFTitle.setDisable(true);
            BSave.setText("EDIT");
        } 
        catch (Exception e) 
        {
            System.err.println("KLIKTABLEDATAERROR!!");
        }
    }
    
    public void start()
    {
        TFTitle.setVisible(false);
        TFDesc.setVisible(false);
        TFDate.setVisible(false);
        CBWriter.setVisible(false);
        BSave.setVisible(false);
        TVDiary.setVisible(false);
        PFPass.setVisible(true);
        
    }
    public void log()
    {
        TFTitle.setVisible(true);
        TFDesc.setVisible(true);
        TFDate.setVisible(true);
        CBWriter.setVisible(true);
        BSave.setVisible(true);
        TVDiary.setVisible(true);
        PFPass.setText("");
        PFPass.setVisible(false);
        selectTable();
        actionSelect();
    }

    @FXML
    private void actionLogin(KeyEvent event) 
    {
        try {
            con = new Conn();

            PreparedStatement ps;
            ResultSet rs;

            String sql ="select * from akun where pass=?";
            ps = con.connect().prepareStatement(sql);
            ps.setString(1, PFPass.getText());
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                log();
            }
            if(event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.T)
            {
                Stage closeStage = new Stage();
                Node node = (Node)event.getSource();
                closeStage = (Stage) node.getScene().getWindow();
                closeStage.close();
            }
            
        }
        catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void showCBWriter()
    {
        con = new Conn();
        Model m = new Model();
        
        String sqlSatu = "select * from diary order by writer";
    
        try {
            ResultSet rs = con.connect().createStatement().executeQuery(sqlSatu);
         
            while(rs.next())
            {
                m.setWriter(rs.getString("writer"));
                
            CBWriter.getItems().addAll(rs.getString("writer"));
            }
            CBWriter.setPromptText("Pilih Writer");
            CBWriter.setEditable(true);
            TextFields.bindAutoCompletion(CBWriter.getEditor(), CBWriter.getItems());    
            
        } catch (SQLException ex) 
        
        {
            ex.printStackTrace();
        }
                            
    }
    public void clear()
    {
        
        TFTitle.setDisable(false);
        TFTitle.setText("");
        TFDesc.setText("");
        TFDate.setText("");
        CBWriter.setValue("");
        BSave.setText("SAVE");
        selectTable();
        actionSelect();
    }

    @FXML
    private void press(KeyEvent event) 
    {
            
        if(event.getCode() == KeyCode.F5)
        {
            CBWriter.getItems().clear();
            showCBWriter();
            clear();
        }
        if(event.getCode() == KeyCode.ESCAPE)
        {
            start();
        }
        if(event.getCode() == KeyCode.DELETE)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);///////////////////////////
            alert.setContentText("YAKIN MAU DIHAPUS?\nKlik OK!");////////////////////////////////////////
            alert.setTitle(null);///////////////////////////////////////////////////
            alert.setHeaderText("KONFIRMASI");////////////////////////////////////////
            Optional<ButtonType> res = alert.showAndWait();///////////////////////////////////////////////////
                
                   
            if(res.get().equals(ButtonType.OK))
            {
                if(!TFTitle.getText().isEmpty())
                {
                    delete();
                }
            }
        }
    }
    public void delete()
    {
        crud.deleteDiary(TFTitle.getText());
        
        clear();
        showCBWriter();
    }
}
