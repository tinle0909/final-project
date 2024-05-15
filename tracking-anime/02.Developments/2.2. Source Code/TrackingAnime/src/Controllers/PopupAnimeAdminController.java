package Controllers;

import Modeles.Database;
import Modeles.data;
import Util.DBConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class PopupAnimeAdminController implements Initializable {
    @FXML
    private Label pu_lblAired;

    @FXML
    private Hyperlink pu_hyperlinkPrimiered;

    @FXML
    private Hyperlink pu_hyperlinkSeason;

    @FXML
    private Hyperlink pu_hyperlinkStudios;

    @FXML
    private Hyperlink pu_hyperlinkType;

    @FXML
    private Hyperlink pu_hyperlinkTypeAll;

    @FXML
    private Hyperlink pu_hyperlinkStudio;

    @FXML
    private ImageView pu_imgViewPoster;

    @FXML
    private Label pu_lblEpisodes;

    @FXML
    private Label pu_lblIntroduction;

    @FXML
    private Label pu_lblRanked;

    @FXML
    private Label pu_lblScore;

    @FXML
    private Label pu_lblStatus;

    Connection cnn;
    PreparedStatement st;
    ResultSet rs;
    public Alert alert;
    Database database;

    // Popup Details
    public void displaydetails(){
        String query = "select * from Anime where anime_id= ?";
        try{
            st = cnn.prepareStatement(query);
            st.setInt(1, data.id);
            rs = st.executeQuery();
            while (rs.next()){
                Image image = new Image(rs.getString("poster"),150,180,false,true);
                pu_imgViewPoster.setImage(image);
                if(rs.getInt("score") == 0){
                    pu_lblScore.setText("N/A");
                }
                if(rs.getInt("rankded") == -1){
                    pu_lblRanked.setText("N/A");
                }

                pu_lblEpisodes.setText(String.valueOf(rs.getInt("episodes")));
                LocalDate selectedDate = rs.getDate("aried").toLocalDate();
                String formattedDate = selectedDate.toString();
                pu_lblAired.setText(formattedDate);
                pu_hyperlinkStudio.setText(rs.getString("studio"));
                pu_hyperlinkStudios.setText(rs.getString("studio"));
                pu_lblIntroduction.setText(rs.getString("introduction"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void displaystatus(){
        String query = "select * from Anime where anime_id= ?";
        try{
            st = cnn.prepareStatement(query);
            st.setInt(1,data.id);
            rs = st.executeQuery();
            Date currentDate = new Date(System.currentTimeMillis());
            while (rs.next()){
                Date dbDate = rs.getDate("aried");
                if(dbDate.compareTo(currentDate) < 0){
                    pu_lblStatus.setText("Currently Airing");
                }
                else if(dbDate.compareTo(currentDate) > 0){
                    pu_lblStatus.setText("Not yet aired");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void displayseason(){
        String query = "select * from Anime where anime_id= ?";
        try{
            st = cnn.prepareStatement(query);
            st.setInt(1, data.id);
            rs = st.executeQuery();
            while (rs.next()){
                LocalDate selectedDate = rs.getDate("aried").toLocalDate();
                int year = selectedDate.getYear();
                if(rs.getInt("season") == 1){
                    pu_hyperlinkSeason.setText("Spring " + year);
                    pu_hyperlinkPrimiered.setText("Spring " + year);
                }
                else if(rs.getInt("season") == 2){
                    pu_hyperlinkSeason.setText("Summer " + year);
                    pu_hyperlinkPrimiered.setText("Summer " + year);
                }
                else if(rs.getInt("season") == 3){
                    pu_hyperlinkSeason.setText("Autumn " + year);
                    pu_hyperlinkPrimiered.setText("Autumn " + year);
                }
                else if(rs.getInt("season") == 4){
                    pu_hyperlinkSeason.setText("Winter " + year);
                    pu_hyperlinkPrimiered.setText("Winter " + year);
                }
                else{
                    pu_hyperlinkSeason.setText(String.valueOf(year));
                    pu_hyperlinkPrimiered.setText(String.valueOf(year));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // End Popup Details
    public void displaytype(){
        String query = "select * from Anime where anime_id= ?";
        try{
            st = cnn.prepareStatement(query);
            st.setInt(1, data.id);
            rs = st.executeQuery();
            while (rs.next()){
                if(rs.getInt("type") == 1){
                    pu_hyperlinkType.setText("Series");
                    pu_hyperlinkTypeAll.setText("Series");
                }
                else if(rs.getInt("type") == 2){
                    pu_hyperlinkType.setText("Movies");
                    pu_hyperlinkTypeAll.setText("Movies");
                }
                else if(rs.getInt("type") == 3){
                    pu_hyperlinkType.setText("OVA");
                    pu_hyperlinkTypeAll.setText("OVA");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public PopupAnimeAdminController(){
        cnn = DBConnect.makeConnection();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TO DO
        displaydetails();
        displayseason();
        displaytype();
        displaystatus();
    }
}
