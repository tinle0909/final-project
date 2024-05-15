package Controllers;

import Modeles.ManageAnime;
import Modeles.data;
import Util.DBConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CardAnimeController {

    @FXML
    private ImageView anime_imageView;

    @FXML
    private Hyperlink anime_hyperlinkTitle;

    Connection cnn;
    PreparedStatement st;
    ResultSet rs;
    int animeid;
    public Alert alert;

    private ManageAnime manageAnime;
    public void setData(ManageAnime manageAnime) {
        this.manageAnime = manageAnime;
        anime_imageView.setImage(manageAnime.getPoster());
        anime_imageView.setFitWidth(200);
        anime_imageView.setFitHeight(200);
        anime_imageView.setPreserveRatio(false);
        anime_imageView.setSmooth(true);
        anime_hyperlinkTitle.setText(manageAnime.getTitle());
    }

    public CardAnimeController(){
        cnn = DBConnect.makeConnection();
    }

    public void hyperlinkTitleClicked(){
        String checkID = "Select anime_id from Anime where title= '" + anime_hyperlinkTitle.getText() + "'";
        try{
            st = cnn.prepareStatement(checkID);
            rs = st.executeQuery();
            Stage stage = new Stage();
            while (rs.next()){
                animeid = rs.getInt("anime_id");

            }
            data.id = animeid;
            String query = "Select * from anime where anime_id=?";
            st = cnn.prepareStatement(query);
            st.setInt(1, animeid);
            rs = st.executeQuery();
            while(rs.next()){
                Parent root = FXMLLoader.load(getClass().getResource("/Views/Dialog/PopupAnimeUser.fxml"));
                stage.setTitle("Detail Anime");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
