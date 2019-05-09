/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diary;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;

import javafx.animation.Timeline;

import javafx.application.Application;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;

import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;

import javafx.scene.text.Text;

import javafx.stage.Stage;

import javafx.util.Duration;
import ta1.conn.Conn;

/**
 *
 * @author TRISNA NURDIANA
 */
public class Diary extends Application {
    Conn con;
//    
//    @Override
//    public void start(Stage stage) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("TryStyle.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
    
	Scene scene;

	VBox vBox;

	HBox hBox;

	Button sButton, rButton;

	Text text;

	Timeline timeline;

	Integer mins = 0, secs = 0, millis = 0;

	boolean sos = true;

	public static void main(String[] args) {

		launch(args);

	}



	void change(Text text) {
          
               if(millis == 1000) {

			secs++;

			millis = 0;

		}

		if(secs == 60) {

			mins++;

			secs = 0;

		}
		text.setText((((mins/10) == 0) ? "0" : "") + mins + ":"

		 + (((secs/10) == 0) ? "0" : "") + secs + ":" 

			+ (((millis/10) == 0) ? "00" : (((millis/100) == 0) ? "0" : "")) + millis++);

    }



	@Override

	public void start(Stage stage) {
            con = new Conn();
            text = new Text("00:00:000");
            
            try {
            String sqlauto = "select minute(time) as mins,second(time) as secs,time from trytime";
            ResultSet rs = con.connect().createStatement().executeQuery(sqlauto);
            if(rs.last())
            {
                String mili = rs.getString("time").substring(9);
                mins = Integer.parseInt(rs.getString("mins"));
                secs = Integer.parseInt(rs.getString("secs"));
                millis =Integer.parseInt(mili);
                text = new Text(""+mins+":"+secs+":"+millis+"");
            
            }
		timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {

			@Override

			public void handle(ActionEvent event) {

            	change(text);
                           }

		}));

		timeline.setCycleCount(Timeline.INDEFINITE);

		timeline.setAutoReverse(false);

		sButton = new Button("Start");
		sButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override

            public void handle(ActionEvent event) {

            	if(sos) {

            		timeline.play();

            		sos = false;

            		sButton.setText("Stop");

            	}
                else {

            		timeline.pause();

            		sos = true;

            		sButton.setText("Start");

            	}

            }

        });

		rButton = new Button("Reset");

		rButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override

            public void handle(ActionEvent event) {

            	mins = 0;

            	secs = 0;

            	millis = 0;

            	timeline.pause();

            	text.setText("00:00:000");

            	if(!sos) {

            		sos = true;

            		sButton.setText("Start");

            	}

            }

        });

		hBox = new HBox(30);

		hBox.setAlignment(Pos.CENTER);

		hBox.getChildren().addAll(sButton, rButton);

		vBox = new VBox(30);

		vBox.setAlignment(Pos.CENTER);

		vBox.getChildren().addAll(text, hBox);

		scene = new Scene(vBox, 200, 150);

		stage.setScene(scene);

        stage.setTitle("Stopwatch");

		stage.show();

                  
        } catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
	
    }

    
}
