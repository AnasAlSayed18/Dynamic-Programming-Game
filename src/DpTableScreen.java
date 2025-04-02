import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class DpTableScreen {
    private Scene scene;
    private Media sound;
    private MediaPlayer mediaPlayer;
    private int[][] dpTable;
    private int[][] firstCoinPicked;


    // Constructor for DpTableScreen
    DpTableScreen(OptimalDp oDp, Scene mainMenuScene, int[] coins,Stage stage) {
        this.dpTable = oDp.dp;
        this.firstCoinPicked = oDp.firstCoinPicked;
        // Initialize DP table and firstCoinPicked arrays
        BorderPane root = new BorderPane();
        root.setStyle(
                "-fx-background-image: url('img/main4.jpg');" +
                        "-fx-background-size: cover;");

        ImageView nextButtonimg = new ImageView(new Image("img/next.png"));
        nextButtonimg.setFitWidth(150);
        nextButtonimg.setFitHeight(50);
        Button nextButton = new Button("", nextButtonimg);
        nextButton.setStyle("-fx-background-color: transparent;");


        ImageView showDpTableButtonimg = new ImageView(new Image("img/showDp.png"));
        showDpTableButtonimg.setFitWidth(150);
        showDpTableButtonimg.setFitHeight(50);
        Button showDpTableButton = new Button("", showDpTableButtonimg);
        showDpTableButton.setStyle("-fx-background-color: transparent;");
        Label currentPlayerLabel = new Label("");
        ImageView turnLabel = new ImageView(new Image("img/p2turn.png"));
        turnLabel.setFitWidth(130);
        turnLabel.setFitHeight(100);

        VBox vbox = new VBox();
        HBox coinAnimationRow = new HBox();
        coinAnimationRow.setSpacing(15);
        coinAnimationRow.setAlignment(Pos.CENTER);

        Button[] coinButtons = new Button[coins.length];
        for (int i = 0; i < coins.length; i++) {
            coinButtons[i] = buttonStyle(String.valueOf(coins[i]));
            coinAnimationRow.getChildren().add(coinButtons[i]);
        }

        int[] front = {0};
        int[] end = {coins.length - 1};
        int[] counter = {0};
        int[] player1Score = {0};
        int[] player2Score = {0};


        ImageView p1Score = new ImageView(new Image("img/p11.png"));
        p1Score.setFitWidth(150);
        p1Score.setFitHeight(50);

        ImageView p2Score = new ImageView(new Image("img/p2.png"));
        p2Score.setFitWidth(150);
        p2Score.setFitHeight(50);


        ImageView vsimg = new ImageView(new Image("img/vs.png"));
        vsimg.setFitWidth(130);
        vsimg.setFitHeight(100);

        Label  p1ScoreLabel = labelStyle("0", "p11");
        Label  p2ScoreLabel = labelStyle("0", "p2");


        HBox hBox = new HBox(p1Score, p1ScoreLabel, vsimg, p2Score, p2ScoreLabel);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);


        vbox.getChildren().addAll(turnLabel,hBox,coinAnimationRow, nextButton, showDpTableButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        showDpTableButton.setDisable(true);
        root.setCenter(vbox);
        scene = new Scene(root, 1024, 768);
        // Next button action
        nextButton.setOnAction(e -> {
            buttonSound(4);
            if (front[0] > end[0]) {
                nextButton.setDisable(true);
                showDpTableButton.setDisable(false);
                return;
            }

            int index;
            if (firstCoinPicked[front[0]][end[0]] == 1) {
                index = front[0];
                front[0]++;
            } else {
                index = end[0];
                end[0]--;
            }

            coinButtons[index].setDisable(true);
            coinButtons[index].setStyle("-fx-background-color: #ffa500; -fx-text-fill: white;");

            int selectedCoinValue = Integer.parseInt(coinButtons[index].getText());
            if (counter[0] % 2 == 0) {
                player1Score[0] += selectedCoinValue;
                turnLabel.setImage(new Image("img/p1turn.png"));
                turnLabel.setFitWidth(130);
                turnLabel.setFitHeight(100);
                p1ScoreLabel.setText(String.valueOf(player1Score[0])); // Update Player 1's score
            } else {
                player2Score[0] += selectedCoinValue;
                turnLabel.setImage(new Image("img/p2turn.png"));
                turnLabel.setFitWidth(130);
                turnLabel.setFitHeight(100);
                p2ScoreLabel.setText(String.valueOf(player2Score[0])); // Update Player 2's score
            }
            counter[0]++;

            if (counter[0] == coins.length) {
                nextButton.setDisable(true);
                showDpTableButton.setDisable(false);
            }
        });


        // Show DP Table button action
        showDpTableButton.setOnAction(e -> {
            buttonSound(2);
            Scene dpScene = showDpTable(oDp, mainMenuScene, coins, stage);
            stage.setScene(dpScene); // Set the new scene to the stage
        });
    }

    // Compute DP table and firstCoinPicked array

    public Scene showDpTable(OptimalDp oDp, Scene mainMenuScene, int[] coins,Stage stage) {
        int[][] dpTable = oDp.dp;// DP table data from OptimalDp object

        // Load and play background music
        sound = new Media(getClass().getResource("/img/menu.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlayer.getOnRepeat();

        // Main layout container for the scene
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-image: url('img/table1.jpg');" + "-fx-background-size: cover;");

        // VBox container for main elements
        VBox vbox = new VBox(),
                v1 = new VBox(),
                v3 = new VBox();

        HBox B1h1 = new HBox(), // HBox for result display (B1)
                B2h1 = new HBox(), // HBox for Player 1 display (B2)
                B3h1 = new HBox(), // HBox for Player 2 display (B3)
                P1h1 = new HBox(), // HBox to show Player 1's coin choices
                P2h1 = new HBox(); // HBox to show Player 2's coin choices

        // Section B1: Display optimal score
        String result = String.valueOf(oDp.getOptimalScore());
        ImageView B1l1 = new ImageView(new Image("img/result.png"));
        B1l1.setFitWidth(110);
        B1l1.setFitHeight(35);

        Label B1t1 = labelStyle(result, "result");
        B1h1.getChildren().addAll(B1l1, B1t1);
        B1h1.setSpacing(30);

        // Section B2: Player 1 score display
        ImageView B2l1 = new ImageView(new Image("img/p1.png"));
        B2l1.setFitWidth(110);
        B2l1.setFitHeight(35);


        ImageView B2l12 = new ImageView(new Image("img/win.png"));
        B2l12.setFitWidth(70);
        B2l12.setFitHeight(60);



        B2h1.getChildren().addAll(B2l1, P1h1, B2l12);
        B2h1.setSpacing(27);
        P1h1.setSpacing(15);
        P2h1.setSpacing(15);

        int p1 = 0; // Variable to store Player 1's score sum
        for (int i = 0; i < oDp.getOptimalCoins().split(",").length; i += 2) {
            // Add Player 1's coin choices to HBox
            P1h1.getChildren().addAll(labelStyle((oDp.getOptimalCoins().split(",")[i]), "p1"));
            p1 += Integer.parseInt(oDp.getOptimalCoins().split(",")[i]); // Update sum
        }
        String p1Sum = String.valueOf(p1); // Convert score to String for comparison



        // Section B3: Player 2 score display
        ImageView B3l1 = new ImageView(new Image("img/p2.png"));
        B3l1.setFitWidth(110);
        B3l1.setFitHeight(35);

        ImageView B3l12 = new ImageView(new Image("img/win.png"));
        B3l12.setFitWidth(70);
        B3l12.setFitHeight(60);

        B3h1.getChildren().addAll(B3l1, P2h1, B3l12);
        B3h1.setSpacing(27);

        for (int i = 0; i < oDp.getOptimalCoins().split(",").length; i += 2) {
            // Add Player 2's coin choices to HBox
            if (i + 1 < oDp.getOptimalCoins().split(",").length)
                P2h1.getChildren().addAll(labelStyle((oDp.getOptimalCoins().split(",")[i + 1]), "p2"));
        }

        // Determine the winner by comparing Player 1's sum to the optimal score
        if (p1Sum.equals(result)) {
            B3l12.setImage(new Image("img/lose.png"));
        } else {
            B2l12.setImage(new Image("img/lose.png"));
        }


        // Align each section to center
        B1h1.setAlignment(Pos.CENTER);
        B2h1.setAlignment(Pos.CENTER);
        B3h1.setAlignment(Pos.CENTER);


        // Section B4: Display DP Table in a GridPane
        GridPane B4gridPane = new GridPane();
        B4gridPane.setHgap(10);
        B4gridPane.setVgap(10);
        B4gridPane.setPadding(new Insets(20));
        B4gridPane.setAlignment(Pos.CENTER);

        Label iJHeader = labelStyle("i \\ j" , "Header");
        B4gridPane.add(iJHeader, 0, 0);
        for (int i = 0; i < dpTable.length; i++) {
            // Add row and column headers
            Label columnHeader = labelStyle(" " + i +"-> "+coins[i], "Header");
            Label rowHeaders = labelStyle("" + i +"-> "+coins[i], "Header");

            B4gridPane.add(columnHeader, i + 1, 0); // Add column header
            B4gridPane.add(rowHeaders, 0, i + 1); // Add row header
        }

        for (int i = 0; i < dpTable.length; i++) {
            for (int j = 0; j < dpTable[i].length; j++) {
                // Fill DP table values

                String data = String.valueOf(dpTable[i][j]);
                Label B4l1 = labelStyle(data, "notHeader"); // Style each cell

                B4gridPane.add(B4l1, j + 1, i + 1); // Add cell to grid
            }
        }

        // Back button setup
        ImageView backButtonimg = new ImageView(new Image("img/back.png"));
        backButtonimg.setFitWidth(110);
        backButtonimg.setFitHeight(50);


        Button backButton = new Button("", backButtonimg);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setAlignment(Pos.CENTER);


        backButton.setOnAction(event -> {
            buttonSound(3); // Play back button sound
            mediaPlayer.stop(); // Stop background music
            stage.setScene(mainMenuScene); // Return to main menu

        });


        // Music button setup to toggle sound on/off
        ImageView musicimg = new ImageView(new Image("img/music.png"));
        musicimg.setFitWidth(60);
        musicimg.setFitHeight(60);

        Button music = new Button("", musicimg);
        music.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");
        root.setTop(music);


        music.setOnAction(e -> {
            // Switch music play/stop
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                playSound(false);
            } else {
                playSound(true);
            }
        });

        // Add all sections to main VBox and center it in the layout
        vbox.getChildren().addAll(B2h1, B3h1, B4gridPane, B1h1, backButton);
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);

        root.setCenter(vbox);
         scene = new Scene(root, 1024, 768);
        return scene;
    }


    // Method to create a styled label based on type
    private Label labelStyle(String text, String type) {
        Label label = new Label(text);
        // Style based on label type (Header, p1, p2, result, etc.)
        double fixedWidth = 100;
        if (type.equals("Header")) {
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setTextFill(Color.DARKBLUE);
            label.setPadding(new Insets(10));
            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(246, 210, 0), new CornerRadii(5), Insets.EMPTY)));
            label.setMinWidth(fixedWidth);
            label.setPrefWidth(fixedWidth);
            label.setMaxWidth(fixedWidth);

        } else if (type.equals("notHeader")) {
            label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            label.setTextFill(Color.rgb(0, 0, 0));
            label.setPadding(new Insets(10));
            label.setBackground(new Background(new BackgroundFill(
                    Color.GREY, new CornerRadii(5), Insets.EMPTY)));
            label.setMinWidth(fixedWidth);
            label.setPrefWidth(fixedWidth);
            label.setMaxWidth(fixedWidth);
        } else if (type.equals("p1")) {
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setTextFill(Color.rgb(255, 255, 253));
            label.setPadding(new Insets(10));
            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(0, 60, 213), new CornerRadii(5), Insets.EMPTY)));

        } else if (type.equals("p11")) {
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setTextFill(Color.rgb(255, 255, 255));
            ;
            label.setPadding(new Insets(10));
            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(246, 200, 0), new CornerRadii(5), Insets.EMPTY)));
        }


        else if (type.equals("p2")) {
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setTextFill(Color.rgb(255, 255, 253));
            label.setPadding(new Insets(10));
            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(2, 128, 110), new CornerRadii(5), Insets.EMPTY)));

        } else if (type.equals("result")) {
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setTextFill(Color.rgb(255, 255, 253));
            label.setPadding(new Insets(10));
            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(246, 210, 0), new CornerRadii(5), Insets.EMPTY)));
        }

        label.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));
        label.setAlignment(Pos.CENTER);

        return label;
    }


    // Method to play button sound effects
    private void buttonSound(int type) {
        if (type == 1) {
            Media sound = new Media(getClass().getResource("sound/button.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();

        } else if (type == 2) {
            Media sound = new Media(getClass().getResource("sound/conform.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } else if (type == 3) {
            Media sound = new Media(getClass().getResource("sound/back.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }else if (type == 4) {
            Media sound = new Media(getClass().getResource("sound/add.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
    }

    // Switch background music play/stop
    private void playSound(boolean playe) {
        if (playe)
            mediaPlayer.play();
        else
            mediaPlayer.stop();
    }
    public static Button buttonStyle(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
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

    // Getter for scene object
    public Scene getScene() {
        return scene;
    }
}
