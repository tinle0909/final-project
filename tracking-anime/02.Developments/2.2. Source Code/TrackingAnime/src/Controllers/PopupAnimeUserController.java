package Controllers;

import Modeles.Database;
import Modeles.data;
import Util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PopupAnimeUserController implements Initializable {

    @FXML
    private Button pu_btnAddMyList;

    @FXML
    private ComboBox<?> pu_cbSelectScore;

    @FXML
    private ComboBox<?> pu_cbStatus;

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
    int animeid = -1;
    public Integer[] listscore = {10,9,8,7,6,5,4,3,2,1};
    public Integer[] liststatus = {1,2,3,4,5};
    // Popup Details
    public void displaydetails(){
//        String query = "SELECT TL.tl_id, TA.status, A.*\n" +
//                "FROM TrackingList TL\n" +
//                "INNER JOIN TrackingAnime TA ON TL.tl_id = TA.tl_id\n" +
//                "INNER JOIN Anime A ON TA.anime_id = A.anime_id where A.anime_id= ?";
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


    // Optional
    public void optscore(){
        List<Integer> listScore = new ArrayList<>();
        for(Integer data: listscore){
            listScore.add(data);
        }
        ObservableList score = FXCollections.observableArrayList(listScore);
        pu_cbSelectScore.setItems(score);
    }
    public void optstatus(){
        List<Integer> listStatus = new ArrayList<>();
        for(Integer data: liststatus){
            listStatus.add(data);
        }
        ObservableList status = FXCollections.observableArrayList(listStatus);
        pu_cbStatus.setItems(status);
//        pu_cbStatus.setOnAction(event -> {
//            db_lblStatusShowcase.getStyleClass().clear();
//            int selectedIndex = db_cbStatus.getSelectionModel().getSelectedIndex();
//            if(selectedIndex >= 0 && selectedIndex < status.length){
//                int selectedStatus = status[selectedIndex];
//                switch (selectedStatus) {
//                    case 1:
//                        db_lblStatusShowcase.setText("Airing");
//                        db_lblStatusShowcase.getStyleClass().add("status-1-showcase");
//                        db_lblStatusShowcase.setVisible(true);
//                        enable();
//                        break;
//                    case 2:
//                        db_lblStatusShowcase.setText("Finished");
//                        db_lblStatusShowcase.getStyleClass().add("status-2-showcase");
//                        db_lblStatusShowcase.setVisible(true);
//                        enable();
//                        break;
//                    case 3:
//                        db_lblStatusShowcase.setText("Cancelled");
//                        db_lblStatusShowcase.getStyleClass().add("status-3-showcase");
//                        db_lblStatusShowcase.setVisible(true);
//                        disable();
//                        clearSelectionCancelled();
//                        break;
//                    case 4:
//                        db_lblStatusShowcase.setText("Upcoming");
//                        db_lblStatusShowcase.getStyleClass().add("status-4-showcase");
//                        db_lblStatusShowcase.setVisible(true);
//                        enable();
//                        break;
//                }
//
//
//            } else {
//                System.out.println("Nothing to display");
//            }
//
//
//        });
    }
    // End Optional

    public void checkaddmylist(){
        String findanimeid = "Select * from Anime where title= ?";
        try{
            st = cnn.prepareStatement(findanimeid);
            st.setString(1, data.title);
            rs = st.executeQuery();
            while (rs.next()) {
                animeid = rs.getInt("anime_id");
                if (animeid != -1) {
                    String query = "SELECT TL.tl_id, TL.account_id, TA.anime_id, TA.status\n" +
                            "FROM TrackingList TL\n" +
                            "INNER JOIN TrackingAnime TA ON TL.tl_id = TA.tl_id where TL.account_id= ? AND TA.anime_id= ?";
                    st = cnn.prepareStatement(query);
                    st.setInt(1, data.accountid);
                    st.setInt(2, animeid);
                    rs = st.executeQuery();
                    System.out.println(data.accountid);
                    System.out.println(animeid);
                    if (rs.next()) {
                        pu_btnAddMyList.setVisible(false);
                        pu_cbStatus.setVisible(true);
                    } else {
                        pu_btnAddMyList.setVisible(true);
                        pu_cbStatus.setVisible(false);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // Event
    public void addmylistBtn(){

    }

    // End Event
    public PopupAnimeUserController(){
        cnn = DBConnect.makeConnection();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TO DO
        displaydetails();
        displayseason();
        displaytype();
        displaystatus();

        // Optional
        optscore();
        optstatus();
        // End Optional

        checkaddmylist();
    }
}
