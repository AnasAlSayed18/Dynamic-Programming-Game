import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TwoPlayerGame {
    private Scene scene;
    private int[] coins; //  coin array
    private int player1Score = 0;
    private int player2Score = 0;
    private boolean player1Turn = true; // Player 1 starts
    private int left = 0;
    private int right;

    private Label p1ScoreLabel, p2ScoreLabel;
    private ImageView turnLabel;
    private HBox coinBox;

    private Media sound;
    private MediaPlayer mediaPlayer;

    TwoPlayerGame(int[] coins, Scene mainMenuScene, Stage stage) {
        this.coins = coins;
        this.right = coins.length - 1;
        sound = new Media(getClass().getResource("/img/menu.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-image: url('img/table1.jpg');" + "-fx-background-size: cover;");

        // Labels for scores and turn indication
        p1ScoreLabel = labelStyle("0", "p1");
        p2ScoreLabel = labelStyle("0", "p2");

        turnLabel = new ImageView(new Image("img/p2turn.png"));
        turnLabel.setFitWidth(130);
        turnLabel.setFitHeight(100);
        ImageView p1Score = new ImageView(new Image("img/p11.png"));
        p1Score.setFitWidth(150);
        p1Score.setFitHeight(50);

        ImageView p2Score = new ImageView(new Image("img/p2.png"));
        p2Score.setFitWidth(150);
        p2Score.setFitHeight(50);


        ImageView vsimg = new ImageView(new Image("img/vs.png"));
        vsimg.setFitWidth(130);
        vsimg.setFitHeight(100);


        ImageView backButtonimg = new ImageView(new Image("img/back.png"));
        backButtonimg.setFitWidth(110);
        backButtonimg.setFitHeight(50);


        Button backButton = new Button("", backButtonimg);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setAlignment(Pos.CENTER);
        backButton.setOnAction(event -> {
            buttonSound(3);
            mediaPlayer.stop();
            stage.setScene(mainMenuScene);

        });

        ImageView musicimg = new ImageView(new Image("img/music.png"));
        musicimg.setFitWidth(60);
        musicimg.setFitHeight(60);
        Button music = new Button("", musicimg);
        music.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");
        root.setTop(music);


        // HBox to display coins as buttons
        coinBox = new HBox(10);
        coinBox.setAlignment(Pos.CENTER);
        updateCoinButtons();

        HBox hBox = new HBox(p1Score, p1ScoreLabel, vsimg, p2Score, p2ScoreLabel);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        // VBox layout to arrange all elements vertically
        VBox V1 = new VBox(20, turnLabel, hBox, coinBox, backButton);
        V1.setAlignment(Pos.CENTER);
        root.setCenter(V1);

        scene = new Scene(root, 1024, 768);

    }


    // Updates the coin buttons and allows players to click to choose a coin
    private void updateCoinButtons() {
        coinBox.getChildren().clear();

        // Add a button for each remaining coin
        for (int i = left; i <= right; i++) {
            final int index = i; // Copy i to a final variable
            Button coinButton = buttonStyle(String.valueOf(coins[i]));
            coinButton.setOnAction(e -> {
                buttonSound(1);
                handleCoinSelection(index);
            });
            coinBox.getChildren().add(coinButton);
        }
    }


    // Handles a player's selection and updates the game state
    private void handleCoinSelection(int index) {
        int chosenCoin;

        // Determine if the player picked from the left or right end
        if (index == left) {
            chosenCoin = coins[left++];
        } else if (index == right) {
            chosenCoin = coins[right--];
        } else {
            return; // Invalid selection, do nothing
        }

        // Update the current player's score
        if (player1Turn) {
            player1Score += chosenCoin;
        } else {
            player2Score += chosenCoin;
        }

        // Update scores and switch turns
        player1Turn = !player1Turn;
        p1ScoreLabel.setText(String.valueOf(player1Score));
        p2ScoreLabel.setText(String.valueOf(player2Score));


        if (player1Turn) {
            turnLabel.setImage(new Image("img/p2turn.png"));
            turnLabel.setFitWidth(130);
            turnLabel.setFitHeight(100);
        } else {
            turnLabel.setImage(new Image("img/p1turn.png"));
            turnLabel.setFitWidth(130);
            turnLabel.setFitHeight(100);
        }

        // Check if the game is over
        if (left > right) {
            if (player1Score > player2Score) {
                turnLabel.setImage(new Image("img/win1.png"));
                turnLabel.setFitWidth(150);
                turnLabel.setFitHeight(50);
            } else if (player2Score > player1Score) {
                turnLabel.setImage(new Image("img/win2.png"));
                turnLabel.setFitWidth(150);
                turnLabel.setFitHeight(50);
            } else {
                turnLabel.setImage(new Image("img/tie.png"));
                turnLabel.setFitWidth(150);
                turnLabel.setFitHeight(50);
            }
            coinBox.getChildren().clear(); // Clear coin buttons when game ends
        } else {
            updateCoinButtons();
        }
    }


    public static Button buttonStyle(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setTextFill(Color.rgb(255, 255, 255));
        ;
        button.setPadding(new Insets(10));
        button.setBackground(new Background(new BackgroundFill(
                Color.rgb(183, 0, 0), new CornerRadii(5), Insets.EMPTY)));

        // Set border style for the button
        button.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));

        return button;
    }

    public static Label labelStyle(String text, String type) {
        Label label = new Label(text);

        // Apply different styles based on the type
        if (type.equals("p1")) {
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setTextFill(Color.rgb(255, 255, 255));
            ;
            label.setPadding(new Insets(10));
            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(246, 200, 0), new CornerRadii(5), Insets.EMPTY)));
        } else if (type.equals("p2")) {
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setTextFill(Color.rgb(255, 255, 255));
            ;
            label.setPadding(new Insets(10));
            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 132, 142), new CornerRadii(5), Insets.EMPTY)));
        }

        label.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));

        return label;
    }

    private void buttonSound(int type) {
        if (type == 1) {
            Media sound = new Media(getClass().getResource("sound/add.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();

        } else if (type == 2) {
            Media sound = new Media(getClass().getResource("sound/back.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
    }

    private void playSound(boolean playe) {
        if (playe)
            mediaPlayer.play();
        else
            mediaPlayer.stop();
    }

    public Scene getScene() {
        return scene;
    }

}
