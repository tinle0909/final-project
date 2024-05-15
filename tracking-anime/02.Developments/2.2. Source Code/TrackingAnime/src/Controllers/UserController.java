package Controllers;

import Modeles.ListAnime;
import Modeles.ManageAnime;
import Modeles.Notification;
import Modeles.data;
import Util.DBConnect;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UserController implements Initializable {


    // Anime
    @FXML
    private GridPane anime_gridPane;

    @FXML
    private GridPane anime_gridPaneTopAnime;
    // End Anime

    @FXML
    private TextField account_nickname;

    @FXML
    private Button account_submitNickName;

    @FXML
    private ImageView avatar_imageView;

    @FXML
    private ImageView avatar_imageView1;

    @FXML
    private AnchorPane animeForm;

    @FXML
    private Button animeBtn;

    @FXML
    private PasswordField account_confirmPass;

    @FXML
    private TextField account_email;

    @FXML
    private PasswordField account_newPass;

    @FXML
    private Button account_submitEmail;

    @FXML
    private Button account_submitPass;

    @FXML
    private BorderPane main_form;

    @FXML
    private Button account_submitUsername;

    @FXML
    private TextField account_username;

    @FXML
    private Button accountsettingBtn;

    @FXML
    private AnchorPane accountsettingForm;

    @FXML
    private Button animeListBtn;

    @FXML
    private AnchorPane animeListForm;

    @FXML
    private GridPane favoriteGridPane;

    @FXML
    private Label header_usernameLabel;

    @FXML
    private GridPane historyGridPane;

    @FXML
    private Label labelJoined;

    @FXML
    private Label labelOnline;

    @FXML
    private TextField txtSearch;

    @FXML
    private AnchorPane profileForm;

    @FXML
    private Button signoutBtn;

    // My List Anime
    @FXML
    private TableView<ListAnime> myl_tbvMyList;

    @FXML
    private TableColumn<ListAnime, Hyperlink> myl_tbvcolTitle;


    @FXML
    private TableColumn<ListAnime, Image> myl_tbvcolPoster;


    @FXML
    private TableColumn<ListAnime, Date> myl_tbvcolCreated;

    @FXML
    private TableColumn<ListAnime, Integer> myl_tbvcolStatus;

    @FXML
    private TableColumn<ListAnime, Integer> myl_tbvcolLastWatched;

    // End My List Anime

    @FXML
    private Hyperlink viewFavoriteLink;

    @FXML
    private Hyperlink viewHistoryLink;

    // Notification
    @FXML
    private TableView<Notification> nf_tbvNotification;

    @FXML
    private TableColumn<Notification, LocalDateTime> nf_tbvcolBroadcast;

    @FXML
    private TableColumn<Notification, Image> nf_tbvcolPoster;

    @FXML
    private TableColumn<Notification, Void> nf_tbvcolSeen;

    @FXML
    private TableColumn<Notification, Integer> nf_tbvcolStatus;

    @FXML
    private TableColumn<Notification, Hyperlink> nf_tbvcolTitle;

    @FXML
    private AnchorPane notificationForm;

    @FXML
    private AnchorPane cardNotification;

    @FXML
    private GridPane notificationGridPane;

    @FXML
    private ImageView notificationBell;

    @FXML
    private ToggleButton notificationToggleButton;

    // End Notification


    Connection cnn;
    PreparedStatement st;
    ResultSet rs;

    public Alert alert;
    public int second;
    public Image image;
    StringBuilder ID = new StringBuilder();
    public int animeid;
    // Constructor
    public UserController() {
        cnn = DBConnect.makeConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cnn = DBConnect.makeConnection();

        displayAccount();
        displayOnline();

        displayUsername();
        displayEmail();
        displayNickname();


        // Anime Display
        animeDisplay();

        // End Anime

        // Notification
        notificationShowcase();
        notificationBell();
        // End Notification

        // My List Anime
        listanimeShowcase();

        // End My List Anime


    }
    // End Constructor

    // Class
    public class regexPassword {
        private static final Pattern PASSWORD_PATTERN
                = Pattern.compile("^(?=.*[0-9]).{8,}$");

        public static boolean isValidPassword(String password) {
            return PASSWORD_PATTERN.matcher(password).matches();
        }
    }

    public class regexEmail {
        private static final Pattern EMAIL_PATTERN
                = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        public static boolean isValidEmail(String email) {
            return EMAIL_PATTERN.matcher(email).matches();
        }

    }

    // End Class

    // Method
    public void displayAccount() {
        String user = data.nickname;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        header_usernameLabel.setText(user);
    }


    public boolean stopRequested = false;

    public void displayOnline() {
        Thread online = new Thread(() -> {
            second = 0;
            while (!stopRequested) {
                int hours = second / 3600;
                int minutes = (second % 3600) / 60;
                int seconds = second % 60;

                if (second >= 0 && second < 60) {
                    Platform.runLater(() -> labelOnline.setText("Now"));
                    labelOnline.getStyleClass().add("online");
                    labelOnline.getStyleClass().remove("offline");
                } else if (second >= 60 && second < 3600) {
                    Platform.runLater(() -> labelOnline.setText(String.format("%02d minutes ago", minutes)));
                    labelOnline.getStyleClass().remove("online");
                    labelOnline.getStyleClass().add("offline");
                } else {
                    Platform.runLater(() -> labelOnline.setText(String.format("%02d hours ago", hours)));
                    labelOnline.getStyleClass().remove("online");
                    labelOnline.getStyleClass().add("offline");
                }

                // Dừng thực hiện một giây
                try {
                    Thread.sleep(100); // Dừng 1 giây
                    second++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        online.start();
    }


//    public ObservableList<ListAnime> dataListAnime() {
//        ObservableList<ListAnime> listAnime = FXCollections.observableArrayList();
//        String query = "select * from Anime";
//        try {
//            st = cnn.prepareStatement(query);
//            rs = st.executeQuery();
//            ListAnime dataAnime;
//
//            while (rs.next()) {
//                String imagePath = rs.getString("Poster");
//                File imageFile = new File(imagePath);
//                Image image = new Image(imageFile.toURI().toString(), 60, 60, false, true);
//                Hyperlink titleHyperlink = new Hyperlink(rs.getString("Title"));
//                dataAnime = new ListAnime(image, titleHyperlink
//                        , rs.getString("Status"), rs.getInt("episodes"),
//                        rs.getDate("Aried"));
//                listAnime.add(dataAnime);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return listAnime;
//    }
//
//    public ObservableList<ListAnime> dataListShowcase;
//
//    public void dataTableAnimeList() {
//        dataListShowcase = dataListAnime();
//
//        tbv_colImage.setCellValueFactory(new PropertyValueFactory<>("Poster"));
//        tbv_colImage.setCellFactory(column -> {
//            return new TableCell<ListAnime, Image>() {
//                private final ImageView imageView = new ImageView();
//
//                @Override
//                protected void updateItem(Image image, boolean empty) {
//                    super.updateItem(image, empty);
//                    if (empty || image == null) {
//                        setGraphic(null);
//                    } else {
//                        imageView.setImage(image);
//                        setGraphic(imageView);
//                    }
//                }
//            };
//        });
//        tbv_colAnimeTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
//        tbv_colAnimeTitle.setCellFactory(column -> {
//            return new TableCell<ListAnime, Hyperlink>() {
//                @Override
//                protected void updateItem(Hyperlink hyperlink, boolean empty) {
//                    super.updateItem(hyperlink, empty);
//                    if (empty || hyperlink == null) {
//                        setGraphic(null);
//                    } else {
//                        setGraphic(hyperlink);
////                        hyperlink.setOnAction(event -> {
////                            // Thực hiện các hành động khi hyperlink được nhấp vào
////                            // Ví dụ: Mở một URL trong trình duyệt
////                            // String url = hyperlink.getURL();
////                            // ...
////                        });
//                    }
//                }
//            };
//        });
//        tbv_colDate.setCellValueFactory(new PropertyValueFactory<>("Aried"));
//        tbv_colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
//        tbv_colProgress.setCellValueFactory(new PropertyValueFactory<>("episodes"));
//
//        tbv_ListAnime.setItems(dataListShowcase);
//    }
//

    public void displayEmail() {
        String query = "select Email from Account where account_id = '" + data.accountid + "'";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                account_email.setText(rs.getString("Email"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        String query = "select Username from Account where account_id = '" + data.accountid + "'";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                account_username.setText(rs.getString("Username"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayNickname() {
        String query = "select Nickname from Account where account_id = '" + data.accountid + "'";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                account_nickname.setText(rs.getString("Nickname"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ObservableList<ManageAnime> animedata = FXCollections.observableArrayList();

    public ObservableList<ManageAnime> dataAnime() {
        ObservableList<ManageAnime> listAnime = FXCollections.observableArrayList();
        String query = "select * from Anime";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            ManageAnime manageAnime;
            while (rs.next()) {
                String imagePath = rs.getString("Poster");
                Image image = new Image(imagePath, 200, 200, false, true);
                manageAnime = new ManageAnime(rs.getString("Status"),
                        rs.getInt("episodes"), image, rs.getString("Title"),
                        rs.getString("Type"), rs.getString("Season"), rs.getDate("Aried"),
                        rs.getString("Introduction"),rs.getString("Nation"));
                listAnime.add(manageAnime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAnime;
    }

    public void animeDisplay() {
        animedata.clear();

        animedata.addAll(dataAnime());
        // Tạo cột và dòng để nạp dữ liệu vào grid pane
        int row = 0;
        int col = 0;

        // Làm sạch dữ liệu trong grid pane , 1. Sẽ không bị dữ liệu cũ ở dưới dữ liệu mới, 2. Làm sạch dữ liệu dòng, 3. Làm sạch dữ liệu cột
        anime_gridPane.getChildren().clear();
        anime_gridPane.getRowConstraints().clear();
        anime_gridPane.getColumnConstraints().clear();

        // Vòng lặp để chạy design của cardProduct
        for (int q = 0; q < animedata.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/Views/Dialog/cardAnime.fxml"));
                AnchorPane pane = load.load();
                CardAnimeController cardC = load.getController();
                cardC.setData(animedata.get(q));
                if (col == 4) {
                    col = 0;
                    row++;
                }

                anime_gridPane.add(pane, col++, row);
                GridPane.setMargin(pane, new Insets(10, 10, 10, 23)); // Insets( top, left, right, bottom) ghi tắt ( all )

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // Notification
    public ObservableList<Notification> getNotificationData(){
        ObservableList<Notification> listData = FXCollections.observableArrayList();
        String query = " Select N.id,Acc.account_id,TL.tl_id,A.anime_id,A.poster,A.title,TA.status,N.time from Notification N " +
                "JOIN TrackingAnime TA ON N.ref_tl_id = TA.tl_id AND N.ref_anime_id = TA.anime_id " +
                "JOIN TrackingList TL ON TA.tl_id = TL.tl_id " + "JOIN Anime A ON TA.anime_id = A.anime_id " +
                "JOIN Account Acc ON TL.account_id = Acc.account_id;";
        try{
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            Notification notify;
            while(rs.next()){
                String imagePath = rs.getString("poster");
                Image image = new Image(imagePath, 60, 60, false, true);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String formattedDate = dateFormat.format(rs.getDate("time"));
                notify = new Notification(rs.getInt("tl_id"),rs.getString("title"),
                        image,rs.getInt("status"),formattedDate);
                listData.add(notify);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    ObservableList<Notification> notificationData;
    public void notificationShowcase(){
        notificationData = getNotificationData();
        nf_tbvcolPoster.setCellValueFactory(new PropertyValueFactory<>("poster"));
        nf_tbvcolPoster.setCellFactory(column -> {
            return new TableCell<Notification,Image>(){
                private final ImageView imageView = new ImageView();
                @Override
                protected void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else{
                        imageView.setImage(item);
                        setGraphic(imageView);
                    }
                }
            };
        });
        nf_tbvcolTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        nf_tbvcolStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        nf_tbvcolStatus.setCellFactory(column ->{
            return new TableCell<Notification, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else if(item == 1){
                        setText("Plan to Watch");
                    }
                    else if(item == 2){
                        setText("Watching");
                    }
                    else if(item == 3){
                        setText("Completed");
                    }
                    else if(item == 4){
                        setText("On Hold");
                    }
                    else if(item == 5){
                        setText("Dropped");
                    }
                }
            };
        });
        nf_tbvcolBroadcast.setCellValueFactory(new PropertyValueFactory<>("time"));
        nf_tbvcolSeen.setCellFactory(column ->{
            return new TableCell<Notification, Void>(){
                private final Button button = new Button();{
                    button.setOnAction(event -> {
                        int id = getTableRow().getIndex();
                        int offset = id + 1;
                        String query = "select * from Notification where id = ?" ;
                        try {
                            st = cnn.prepareStatement(query);
                            st.setInt(1, offset);
                            rs = st.executeQuery();
                            while (rs.next()) {
                                button.setDisable(true);
                                alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Error Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Are you sure you want to delete notificcation?");
                                Optional<ButtonType> option = alert.showAndWait();
                                if (option.get().equals(ButtonType.OK)) {
                                    String deleteData = "delete from Notification where ref_tl_id = '" + offset + "'";
                                    try {
                                        st = cnn.prepareStatement(deleteData);
                                        st.executeUpdate();

                                        alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Error Message");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Successfully Deleted!");
                                        alert.showAndWait();

                                        getNotificationData();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    button.setDisable(false);
                                    alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Cancelled");
                                    alert.showAndWait();
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty){
                        setText(null);
                        setGraphic(null);
                    }
                    else{
                        button.setText("Checked");
                        button.setCursor(Cursor.HAND);
                        button.getStyleClass().add("on-notify");
                        setGraphic(button);
                    }
                }
            };

        });
        nf_tbvNotification.setItems(notificationData);
    }

    public void notificationBell(){
        notificationData.clear();
        notificationData.addAll(getNotificationData());


        int row = 0;
        int col = 0;

        // Làm sạch dữ liệu trong grid pane , 1. Sẽ không bị dữ liệu cũ ở dưới dữ liệu mới, 2. Làm sạch dữ liệu dòng, 3. Làm sạch dữ liệu cột
        notificationGridPane.getChildren().clear();
        notificationGridPane.getRowConstraints().clear();
        notificationGridPane.getColumnConstraints().clear();

        // Vòng lặp để chạy design của cardProduct
        for (int q = 0; q < notificationData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/Views/Dialog/cardNotification.fxml"));
                AnchorPane pane = load.load();
                CardNotificationController cardN = load.getController();
                cardN.setData(notificationData.get(q));


                notificationGridPane.add(pane, col, row++);
                GridPane.setMargin(pane, new Insets(10, 0, 0, 0)); // Insets( top, left, right, bottom) ghi tắt ( all )

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // End Notification


    // My List Anime
    public ObservableList<ListAnime> getListAnimeData(){
        ObservableList<ListAnime> listData = FXCollections.observableArrayList();
        ListAnime listAnime;
        String query = "SELECT A.account_id, AN.anime_id, AN.poster, AN.title, TA.status, TA.last_watched_episode, TL.created_day\n" +
                "FROM Account A\n" +
                "INNER JOIN Anime AN ON A.account_id = AN.account_id\n" +
                "INNER JOIN TrackingAnime TA ON AN.anime_id = TA.anime_id\n" +
                "INNER JOIN TrackingList TL ON TA.tl_id = TL.tl_id";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                String imagePath = rs.getString("poster");
                Image image = new Image(imagePath, 60, 60, false, true);
                Hyperlink hyperlink = new Hyperlink(rs.getString("title"));
                listAnime = new ListAnime(image,hyperlink,rs.getInt("status"),rs.getInt("last_watched_episode"),
                        rs.getDate("created_day"));
                listData.add(listAnime);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return listData;
    }
    ObservableList<ListAnime> listanimeData;
    public void listanimeShowcase(){
        listanimeData = getListAnimeData();

        myl_tbvcolPoster.setCellValueFactory(new PropertyValueFactory<>("poster"));
        myl_tbvcolPoster.setCellFactory(column ->{
            return new TableCell<ListAnime,Image>(){
                private final ImageView imageView = new ImageView();
                @Override
                protected void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else{
                        imageView.setImage(item);
                        setGraphic(imageView);
                    }
                }
            };
        });
        myl_tbvcolTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        myl_tbvcolTitle.setCellFactory(column ->{
            return new TableCell<ListAnime, Hyperlink>(){
                @Override
                protected void updateItem(Hyperlink item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                        setGraphic(null);
                        setOnMouseClicked(null);
                    }
                    else{
                        setGraphic(item);
                        item.setOnAction(event ->{
                            String checkID = "Select * from Anime where title= '" + item.getText() + "'";
                            try{
                                data.title = item.getText();
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
                        });

                    }
                }
            };
        });
        myl_tbvcolStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        myl_tbvcolStatus.setCellFactory(column -> {
            return new TableCell<ListAnime, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else if(item == 1){
                        setText("Plan to Watch");
                    }
                    else if(item == 2){
                        setText("Watching");
                    }
                    else if(item == 3){
                        setText("Completed");
                    }
                    else if(item == 4){
                        setText("On Hold");
                    }
                    else if(item == 5){
                        setText("Dropped");
                    }
                }
            };
        });
        myl_tbvcolLastWatched.setCellValueFactory(new PropertyValueFactory<>("lastwatched"));
        myl_tbvcolCreated.setCellValueFactory(new PropertyValueFactory<>("createdday"));

        myl_tbvMyList.setItems(listanimeData);
    }
    // End My List Anime

    // End Method


    // Action Event

    // Modify Account
    public void profileBtn() {
        profileForm.setVisible(true);
        animeListForm.setVisible(false);
        accountsettingForm.setVisible(false);
        animeForm.setVisible(false);
        notificationForm.setVisible(false);
        cardNotification.setVisible(false);
        second = 0;
        displayOnline();
    }

    public void accountsettingBtn() {
        profileForm.setVisible(false);
        animeListForm.setVisible(false);
        accountsettingForm.setVisible(true);
        animeForm.setVisible(false);
        notificationForm.setVisible(false);
        cardNotification.setVisible(false);
    }

    public void signoutBtn() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText("You want to logout account ?");
        alert.setContentText("Select 'OK' to log out, or 'Cancel' to continue.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    signoutBtn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("Form.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Form Login");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    // End Modify Account

    // User Interface
    public void animeListBtn() {
        profileForm.setVisible(false);
        animeListForm.setVisible(true);
        accountsettingForm.setVisible(false);
        animeForm.setVisible(false);
        notificationForm.setVisible(false);
        cardNotification.setVisible(false);
        txtSearch.setText("");
        animeDisplay();
    }

    public void animeDisplayBtn() {
        profileForm.setVisible(false);
        animeListForm.setVisible(false);
        accountsettingForm.setVisible(false);
        animeForm.setVisible(true);
        notificationForm.setVisible(false);
        cardNotification.setVisible(false);
        txtSearch.setText("");
        animeDisplay();
    }

    public void notificationDisplayBtn() {
        profileForm.setVisible(false);
        animeListForm.setVisible(false);
        accountsettingForm.setVisible(false);
        animeForm.setVisible(false);
        notificationForm.setVisible(true);
        cardNotification.setVisible(false);
        notificationShowcase();
        txtSearch.setText("");
        animeDisplay();
    }

    public void submitPasswordBtn() {
        if (account_newPass.getText().isEmpty() || account_confirmPass.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else {
            if (!FormController.regexPassword.isValidPassword(account_newPass.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid password, atleast 8 characters are needed");
                alert.showAndWait();
            } else if (!(account_confirmPass.getText().equals(account_newPass.getText()))) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password does not match");
                alert.showAndWait();
            } else {
                String changePassword = "update Account set Password = '" + account_newPass.getText() +
                        "'where AcountID = '" + data.accountid + "'";
                try {
                    st = cnn.prepareStatement(changePassword);
                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password updated successfully");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void submitEmailBtn() {
        if (account_email.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else {
            if (!regexEmail.isValidEmail(account_email.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email address");
                alert.showAndWait();
            } else {
                String changeEmail = "update Account set Email = '" + account_email.getText() + "'where account_id = '" + data.accountid + "'";
                try {
                    st = cnn.prepareStatement(changeEmail);
                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Email updated successfully");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void submitUsernameBtn() {
        if (account_username.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();

        } else {
            String changeUsername = "update Account set Username = '" + account_username.getText() + "'where account_id = '" + data.accountid + "'";
            try {
                st = cnn.prepareStatement(changeUsername);
                st.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Username updated successfully");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void submitNickNameBtn() {
        if (account_nickname.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();

        } else {
            String changeNickName = "update Account set Nickname = '" + account_nickname.getText() + "'where account_id = '" + data.accountid + "'";
            try {
                st = cnn.prepareStatement(changeNickName);
                st.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Nickname updated successfully");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void importAvatar() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg")); // Set đuôi cho file ảnh

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 200, 160, false, true); // preserveRatio(false) : không giữ tỉ lệ gốc của ảnh ( và ngược lại ),Smooth(true): làm mịn khi phóng to ( và ngược lại )
            avatar_imageView.setImage(image); // Truyền hình ảnh
            avatar_imageView1.setImage(image);

        }
    }

    public void turnonnoticeBtn(){
        if(notificationToggleButton.isSelected()){
            cardNotification.setVisible(true);
        }
        else{
            cardNotification.setVisible(false);
        }
    }

    public void searchBtn() {
        String searchByName = "SELECT * FROM ANIME WHERE title LIKE '%" + txtSearch.getText() + "%'";
        try {
            st = cnn.prepareStatement(searchByName);
            rs = st.executeQuery();
            if (rs.next()) {
                // Xóa sạch dữ liệu có trong cardListData
                animedata.clear();

                // Thêm tất cả dữ liệu của phương listGetData vào trong cardListData
                animedata.addAll(dataAnime());

                // Tạo cột và dòng để nạp dữ liệu vào grid pane
                int row = 0;
                int col = 0;

                // Làm sạch dữ liệu trong grid pane , 1. Sẽ không bị dữ liệu cũ ở dưới dữ liệu mới, 2. Làm sạch dữ liệu dòng, 3. Làm sạch dữ liệu cột
                anime_gridPane.getChildren().clear();
                anime_gridPane.getRowConstraints().clear();
                anime_gridPane.getColumnConstraints().clear();

                // Vòng lặp để chạy design của cardProduct
                for (int q = 0; q < animedata.size(); q++) {
                    if (animedata.get(q).getTitle().contains(txtSearch.getText())) {
                        try {
                            FXMLLoader load = new FXMLLoader();
                            load.setLocation(getClass().getResource("/Views/Dialog/cardAnime.fxml"));
                            AnchorPane pane = load.load();
                            CardAnimeController cardA = load.getController();
                            cardA.setData(animedata.get(q));
                            if (col == 4) {
                                col = 0;
                                row++;
                            }
                            anime_gridPane.add(pane, col++, row);
                            GridPane.setMargin(pane, new Insets(10, 10, 10, 23)); // Insets( top, left, right, bottom) ghi tắt ( all )

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            if(txtSearch.getText().isEmpty()){
                animeDisplay();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // End User Interface

    }
}



    // End Action Event


