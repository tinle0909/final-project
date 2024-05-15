package Controllers;

import Modeles.Information;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CardLastUpdateController implements Initializable {

    @FXML
    private Label cardH_Date;

    @FXML
    private Hyperlink cardH_Name;

    @FXML
    private ProgressBar cardH_ProgressBar;

    @FXML
    private Label cardH_Score;

    @FXML
    private Label cardH_Watching;

    @FXML
    private ImageView cardH_imageView;

    private Information info;
    private Image image;
    private int totalEpsiodes = 25;
    private int episodesWatched = 15;
    // Method
    public void setData(Information info){
        this.info = info;
        String path = info.getImage();
        image = new Image(path, 110, 150, false, true);
        cardH_imageView.setImage(image);
        cardH_Name.setText(info.getName());
        cardH_Watching.setText(episodesWatched + "/" + totalEpsiodes);
        cardH_Score.setText(String.valueOf(info.getScore()));
        cardH_Date.setText(info.getDate());
        double progress = (double) episodesWatched / totalEpsiodes;
        cardH_ProgressBar.setProgress(progress);
    }
    // End Method


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TO DO
    }
}
