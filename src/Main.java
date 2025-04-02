import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;


public class Main extends Application {
    int coin[];
    OptimalDp oDp;
    DpTableScreen Dscreen;
    Scene mainMenuScene, mainMenuScene1;
    private Media sound;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize the media player with background music
        sound = new Media(getClass().getResource("/img/menu.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        // Set up the root layout with a background image
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-image: url('img/main4.jpg');" + "-fx-background-size: cover;");

        // Create a vertical box for buttons
        VBox vbox = new VBox();

        // Create buttons for selecting one-player or two-player mode
        Button onePlayer = new Button("", new ImageView(new Image("img/1P.png")));
        onePlayer.setStyle("-fx-background-color: transparent;");

        Button twoPlayer = new Button("", new ImageView(new Image("img/2P.png")));
        twoPlayer.setStyle("-fx-background-color: transparent;");

        // Create a music button to switch background music
        ImageView musicimg = new ImageView(new Image("img/music.png"));
        musicimg.setFitWidth(60);
        musicimg.setFitHeight(60);
        Button music = new Button("", musicimg);
        music.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");
        music.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                playSound(false); // Stop music if playing
            } else {
                playSound(true); // Play music if not playing
            }
        });

        // Add the buttons to the vertical box
         vbox.getChildren().addAll(onePlayer, twoPlayer);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        vbox.setTranslateY(-45);
        root.setTop(music);
        root.setCenter(vbox);

        // Set actions for the one-player and two-player buttons
        onePlayer.setOnAction(e -> {
            buttonSound(1);
            stage.setScene(inPutOpotion(stage, true)); // Switch to the input options for one-player
        });

        twoPlayer.setOnAction(e -> {
            buttonSound(1); // Play button sound
            stage.setScene(inPutOpotion(stage, false)); // Switch to the input options for two-player

        });

        // Set the initial scene and show the stage
        mainMenuScene1 = new Scene(root, 1024, 768);
        stage.setTitle("𝗖𝗢𝗜𝗡 𝗗𝗨𝗘𝗟");
        stage.getIcons().add(new Image("img/lose.png", 300, 300, false, false));
        stage.setScene(mainMenuScene1);
        stage.show();
    }

    /*
     * This method sets up the input options scene, allowing the user to choose
     * whether they want to play with random coins, manually input coins, or upload a file.
     */
    public Scene inPutOpotion(Stage stage, Boolean ifOnePlayer) {


        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-image: url('img/main4.jpg');" + "-fx-background-size: cover;");

        VBox vbox = new VBox();

        // Buttons for selecting different coin input methods
        Button rand = new Button("", new ImageView(new Image("img/rand.png")));
        rand.setStyle("-fx-background-color: transparent;");

        Button manual = new Button("", new ImageView(new Image("img/manual.png")));
        manual.setStyle("-fx-background-color: transparent;");

        Button file = new Button("", new ImageView(new Image("img/file.png")));
        file.setStyle("-fx-background-color: transparent;");

        // Create a music button to switch background music
        ImageView musicimg = new ImageView(new Image("img/music.png"));
        musicimg.setFitWidth(60);
        musicimg.setFitHeight(60);
        Button music = new Button("", musicimg);
        music.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");

        // Back button to return to the main menu
        ImageView backButtonimg = new ImageView(new Image("img/back1.png"));
        backButtonimg.setFitWidth(150);
        backButtonimg.setFitHeight(50);

        Button backButton = new Button("", backButtonimg);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setAlignment(Pos.CENTER);


        backButton.setOnAction(event -> {
            buttonSound(3);
            stage.setScene(mainMenuScene1);
        });

        // Add the buttons to the vertical box
        vbox.getChildren().addAll(rand, manual, file, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);

        root.setTop(music);
        root.setCenter(vbox);

        // Set actions for each button
        rand.setOnAction(e -> {
            buttonSound(1); // Play button sound
            stage.setScene(rand(stage, ifOnePlayer)); // Switch to random coin input scene
        });

        manual.setOnAction(e -> {
            buttonSound(1); // Play button sound
            stage.setScene(manual(stage, ifOnePlayer)); // Switch to manual coin input scene
        });

        file.setOnAction(e -> {
            buttonSound(1); // Play button sound
            stage.setScene(file(stage, ifOnePlayer)); // Switch to file coin input scene
        });

        // Set action to switch music
        music.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                playSound(false); // Stop music if playing
            } else {
                playSound(true); // Play music if not playing
            }
        });

        // Return the scene for the input options
        mainMenuScene = new Scene(root, 1024, 768);
        return mainMenuScene;
    }


// Method to create a scene for manual coin input
    public Scene manual(Stage stage, Boolean ifOnePlayer) {

        // Create root layout (BorderPane) with background image
        BorderPane root = new BorderPane();
        root.setStyle(
                "-fx-background-image: url('img/main4.jpg');" +
                        "-fx-background-size: cover;");

        // Create layout containers for different sections
        VBox vbox = new VBox(),
                v1 = new VBox(),
                v3 = new VBox();

        HBox h1 = new HBox(),
                h2 = new HBox(),
                h3 = new HBox();

        // Create a button for music control with an image
        ImageView musicimg = new ImageView(new Image("img/music.png"));
        musicimg.setFitWidth(60);
        musicimg.setFitHeight(60);
        Button music = new Button("", musicimg);
        music.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");

        // Action to switch music playback
        music.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                playSound(false);
            } else {
                playSound(true);
            }
        });

        Label l1 = labelTransition("\uD835\uDE4B\uD835\uDE47\uD835\uDE40\uD835\uDE3C\uD835\uDE4E\uD835\uDE40 \uD835\uDE40\uD835\uDE49\uD835\uDE4F\uD835\uDE40\uD835\uDE4D \uD835\uDE4F\uD835\uDE43\uD835\uDE40 \uD835\uDE3E\uD835\uDE4A\uD835\uDE44\uD835\uDE49\uD835\uDE4E \uD835\uDE4E\uD835\uDE44\uD835\uDE55\uD835\uDE40 :");
        TextField t1 = new TextField();
        t1.setPromptText("𝑪𝑶𝑰𝑵 𝑺𝑰𝒁𝑬");


        ImageView b1img = new ImageView(new Image("img/conform.png"));
        b1img.setFitWidth(150);
        b1img.setFitHeight(50);


        // Button to confirm coin size
        Button b1 = new Button("", b1img);

        // Add coin size input field and button to HBox
        b1.setPrefSize(150, 50);
        b1.setStyle("-fx-padding: 10 20 10 20;-fx-background-color: transparent; -fx-pref-width: 150px;-fx-pref-height: 50px;");
        b1.setMaxWidth(60);
        h1.getChildren().addAll(t1, b1);
        h1.setSpacing(15);
        h1.setAlignment(Pos.CENTER);
        v1.getChildren().addAll(l1, h1);
        v1.setAlignment(Pos.CENTER);


        TextField t2 = new TextField();
        t2.setPromptText("\uD835\uDE3E\uD835\uDE4A\uD835\uDE44\uD835\uDE49\uD835\uDE4E");

        // Button to add a coin
        ImageView b2img = new ImageView(new Image("img/add.png"));
        b2img.setFitWidth(150);
        b2img.setFitHeight(50);
        Button b2 = new Button("", b2img);
        b2.setStyle("-fx-background-color: transparent;");
        b2.setMinWidth(60);
        b2.setDisable(true);

        // Add second input field and button to HBox
        h2.getChildren().addAll(t2, b2);
        h2.setSpacing(27);
        h2.setAlignment(Pos.CENTER);

        h3.setSpacing(10);
        h3.setAlignment(Pos.CENTER);

        // Button to submit the coins
        ImageView b3img = new ImageView(new Image("img/submit.png"));
        b3img.setFitWidth(150);
        b3img.setFitHeight(50);
        Button b3 = new Button("", b3img);
        b3.setStyle("-fx-background-color: transparent;");
        b3.setMinWidth(60);
        b3.setDisable(true);
        v3.getChildren().addAll(h2, h3, b3);
        v3.setSpacing(15);
        v3.setAlignment(Pos.CENTER);

        root.setTop(music);

        // Action for coin size confirmation button (b1)
        b1.setOnAction(e -> {
            buttonSound(1);
            if (t1.getText().isBlank()) {
                showErrorAlert("𝗘𝗥𝗥𝗢𝗥: 𝗣𝗟𝗘𝗔𝗦𝗘 𝗘𝗡𝗧𝗘𝗥 𝗧𝗛𝗘 𝗖𝗢𝗜𝗡𝗦 𝗦𝗜𝗭𝗘!");
            } else if (Integer.parseInt(t1.getText().replace(" ", "")) % 2 != 0) {
                showErrorAlert("𝗘𝗥𝗥𝗢𝗥: 𝗧𝗛𝗘 𝗧𝗔𝗕𝗟𝗘 𝗦𝗜𝗭𝗘 𝗠𝗨𝗦𝗧 𝗕𝗘 𝗔𝗡 𝗘𝗩𝗘𝗡 𝗡𝗨𝗠𝗕𝗘𝗥!");
            } else {
                b2.setDisable(false);
                coin = new int[Integer.parseInt(t1.getText().replace(" ", ""))];
            }

        });


        // Action for adding coins button (b2)
        int[] index = {0};
        b2.setOnAction(e -> {
            buttonSound(4);  // Play button sound
            try {
                int t2Input = Integer.parseInt(t2.getText().replace(" ", ""));
                t2.clear();  // Clear the input field

                // Check if all coins are added
                if (index[0] == coin.length - 1) {
                    b3.setDisable(false);


                }
                if (index[0] < coin.length) {
                    coin[index[0]++] = t2Input;// Add coin value to array
                    h3.getChildren().addAll(labelStyle(String.valueOf(coin[index[0] - 1])));


                } else {
                    showErrorAlert("𝗔𝗥𝗥𝗔𝗬 𝗜𝗦 𝗙𝗨𝗟𝗟. 𝗖𝗔𝗡𝗡𝗢𝗧 𝗔𝗗𝗗 𝗠𝗢𝗥𝗘 𝗖𝗢𝗜𝗡𝗦.");

                }
            } catch (NumberFormatException ex) {
                showErrorAlert("𝗜𝗡𝗩𝗔𝗟𝗜𝗗 𝗜𝗡𝗣𝗨𝗧. 𝗣𝗟𝗘𝗔𝗦𝗘 𝗘𝗡𝗧𝗘𝗥 𝗔 𝗩𝗔𝗟𝗜𝗗 𝗡𝗨𝗠𝗕𝗘𝗥.");
            }
        });

        // Back button for returning to main menu
        ImageView backButtonimg = new ImageView(new Image("img/back1.png"));
        backButtonimg.setFitWidth(150);
        backButtonimg.setFitHeight(50);

        Button backButton = new Button("", backButtonimg);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setAlignment(Pos.CENTER);


        // Action for back button
        backButton.setOnAction(event -> {
            buttonSound(3);  // Play button sound
            stage.setScene(mainMenuScene);  // Switch to main menu scene
        });



        // Action for submit button (b3)
        b3.setOnAction(e -> {
            buttonSound(2);  // Play button sound
            oDp = new OptimalDp(coin);  // Create an OptimalDp object with the coins array
            try {
                mediaPlayer.stop();  // Stop music

                // Determine if it's a one-player or two-player game
                if (ifOnePlayer) {
                    DpTableScreen dScreen = new DpTableScreen(oDp, mainMenuScene1,coin ,stage);
                    stage.setScene(dScreen.getScene());
                } else {
                    TwoPlayerGame TwoPlayerScreen = new TwoPlayerGame(coin, mainMenuScene1, stage);
                    stage.setScene(TwoPlayerScreen.getScene());
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);  // Handle exceptions during scene change
            }
        });

        vbox.getChildren().addAll(v1, v3, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        root.setCenter(vbox);
        // Create the scene with specified dimensions
        Scene scene = new Scene(root, 1024, 768);
        return scene;
    }


    public Scene rand(Stage stage, Boolean ifOnePlayer) {

        BorderPane root = new BorderPane();
        root.setStyle(
                "-fx-background-image: url('img/main4.jpg');" +
                        "-fx-background-size: cover;");
        VBox vbox = new VBox(),
                v1 = new VBox(),
                v3 = new VBox();

        HBox h1 = new HBox(),
                h2 = new HBox();

        ImageView musicimg = new ImageView(new Image("img/music.png"));
        musicimg.setFitWidth(60);
        musicimg.setFitHeight(60);
        Button music = new Button("", musicimg);
        music.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");
        music.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                playSound(false);
            } else {
                playSound(true);
            }
        });


        Label l1 = labelTransition("𝗣𝗟𝗘𝗔𝗦𝗘 𝗘𝗡𝗧𝗘𝗥 𝗧𝗛𝗘 𝗖𝗢𝗜𝗡𝗦 𝗦𝗜𝗭𝗘 𝗔𝗡𝗗 𝗠𝗔𝗫 𝗥𝗔𝗡𝗚:");


        TextField t1 = new TextField();
        TextField t2 = new TextField();

        t1.setPromptText("𝗖𝗢𝗜𝗡𝗦 𝗦𝗜𝗭𝗘");
        t2.setPromptText("𝗠𝗔𝗫 𝗥𝗔𝗡𝗚");

        ImageView b1img = new ImageView(new Image("img/conform.png"));
        b1img.setFitWidth(150);
        b1img.setFitHeight(50);


        Button b1 = new Button("", b1img);
        b1.setStyle("-fx-background-color: transparent;");
        h1.getChildren().addAll(t1, t2);
        h1.setSpacing(15);
        h1.setAlignment(Pos.CENTER);
        v1.getChildren().addAll(l1, h1,b1);
        v1.setSpacing(20);
        v1.setAlignment(Pos.CENTER);

        root.setTop(music);

        Label l2 = new Label("Your coins are: ");
        l2.setVisible(false);
        l2.setStyle(
                "-fx-background-color: #1d6acd; " +
                        "-fx-text-fill: #f9f9f9; " +
                        "-fx-padding: 10px; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold;"
        );

        h2.setSpacing(10);
        h2.setAlignment(Pos.CENTER);
        ;


        ImageView b3img = new ImageView(new Image("img/submit.png"));
        b3img.setFitWidth(150);
        b3img.setFitHeight(50);
        Button b3 = new Button("", b3img);
        b3.setStyle("-fx-background-color: transparent;");
        b3.setMinWidth(60);
        b3.setDisable(true);
        v3.getChildren().addAll(l2, h2, b3);
        v3.setSpacing(20);
        v3.setAlignment(Pos.CENTER);


        b1.setOnAction(e -> {
            buttonSound(1);

            try {
                if (t1.getText().isBlank()) {
                    showErrorAlert("𝗘𝗥𝗥𝗢𝗥: 𝗣𝗟𝗘𝗔𝗦𝗘 𝗘𝗡𝗧𝗘𝗥 𝗧𝗛𝗘 𝗖𝗢𝗜𝗡𝗦 𝗦𝗜𝗭𝗘!");

                } else if (Integer.parseInt(t1.getText().replace(" ", "")) % 2 != 0) {
                    showErrorAlert("𝗘𝗥𝗥𝗢𝗥: 𝗧𝗛𝗘 𝗧𝗔𝗕𝗟𝗘 𝗦𝗜𝗭𝗘 𝗠𝗨𝗦𝗧 𝗕𝗘 𝗔𝗡 𝗘𝗩𝗘𝗡 𝗡𝗨𝗠𝗕𝗘𝗥!");

                } else if (t2.getText().isBlank()){
                    showErrorAlert("𝗘𝗥𝗥𝗢𝗥: 𝗣𝗟𝗘𝗔𝗦𝗘 𝗘𝗡𝗧𝗘𝗥 𝗧𝗛𝗘 𝗠𝗔𝗫 𝗥𝗔𝗡𝗚!");


                }



                else {
                    int size = Integer.parseInt(t1.getText());
                    int maxRang = Integer.parseInt(t2.getText());

                    coin = new int[size];
                    Random random = new Random();
                    for (int i = 0; i < size; i++) {
                        coin[i] = random.nextInt(maxRang);
                    }
                    for (int i = 0; i < coin.length; i++) {
                        h2.getChildren().addAll(labelStyle(String.valueOf(coin[i])));
                    }

                    l2.setVisible(true);
                    b3.setDisable(false);

                }
            } catch (NumberFormatException ex) {
                showErrorAlert("𝗘𝗥𝗥𝗢𝗥: 𝗣𝗟𝗘𝗔𝗦𝗘 𝗘𝗡𝗧𝗘𝗥 𝗔 𝗩𝗔𝗟𝗜𝗗 𝗡𝗨𝗠𝗕𝗘𝗥");
            }




        });


        ImageView backButtonimg = new ImageView(new Image("img/back1.png"));
        backButtonimg.setFitWidth(150);
        backButtonimg.setFitHeight(50);

        Button backButton = new Button("", backButtonimg);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setAlignment(Pos.CENTER);


        backButton.setOnAction(event -> {
            buttonSound(3);
            stage.setScene(mainMenuScene);
        });


        vbox.getChildren().addAll(v1, v3, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);


        root.setCenter(vbox);
        Scene scene = new Scene(root, 1024, 768);

        b3.setOnAction(e -> {
            buttonSound(2);
            oDp = new OptimalDp(coin);
            try {
                mediaPlayer.stop();
                if (ifOnePlayer == true) {
                    DpTableScreen dScreen = new DpTableScreen(oDp, mainMenuScene1,coin ,stage);
                    stage.setScene(dScreen.getScene());
                } else {
                    TwoPlayerGame TwoPlayerScreen = new TwoPlayerGame(coin, mainMenuScene1, stage);
                    stage.setScene(TwoPlayerScreen.getScene());
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return scene;
    }

    public Scene file(Stage stage, Boolean ifOnePlayer) {

        BorderPane root = new BorderPane();
        root.setStyle(
                "-fx-background-image: url('img/main4.jpg');" +
                        "-fx-background-size: cover;");
        VBox vbox = new VBox(),
                v1 = new VBox(),
                v3 = new VBox();

        HBox h1 = new HBox(),
                h2 = new HBox();

        ImageView musicimg = new ImageView(new Image("img/music.png"));
        musicimg.setFitWidth(60);
        musicimg.setFitHeight(60);
        Button music = new Button("", musicimg);
        music.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");
        music.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                playSound(false);
            } else {
                playSound(true);
            }
        });

        root.setTop(music);

        Label l1 = labelTransition("Please select your file: ");

        l1.setStyle(
                "-fx-background-color: #1d6acd; " +
                        "-fx-text-fill: #f9f9f9; " +
                        "-fx-padding: 10px; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold;");
        l1.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        l1.setPadding(new Insets(10));


        ImageView ChooseFileimg = new ImageView(new Image("img/file2.png"));
        ChooseFileimg.setFitWidth(150);
        ChooseFileimg.setFitHeight(50);


        Button ChooseFile = new Button("", ChooseFileimg);

        ChooseFile.setStyle("-fx-background-color: transparent;");


        h1.getChildren().addAll(ChooseFile);
        h1.setSpacing(15);
        h1.setAlignment(Pos.CENTER);
        v1.getChildren().addAll(l1, h1);
        v1.setSpacing(15);
        v1.setAlignment(Pos.CENTER);


        ImageView b3img = new ImageView(new Image("img/submit.png"));
        b3img.setFitWidth(150);
        b3img.setFitHeight(50);
        Button b3 = new Button("", b3img);
        b3.setStyle("-fx-background-color: transparent;");
        b3.setMinWidth(60);
        b3.setDisable(true);

        Label l2 = new Label("Your coins are: ");
        l2.setVisible(false);
        l2.setStyle(
                "-fx-background-color: #1d6acd; " +
                        "-fx-text-fill: #f9f9f9; " +
                        "-fx-padding: 10px; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold;"
        );
        l1.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        l1.setPadding(new Insets(10));

        h2.setSpacing(10);
        h2.setAlignment(Pos.CENTER);


        ChooseFile.setOnAction(e -> {
            FileChooser fileC = new FileChooser();
            fileC.getExtensionFilters().removeAll();
            fileC.getExtensionFilters()
                    .addAll(new FileChooser.ExtensionFilter("Include",  "*.txt"));
            File file = fileC.showOpenDialog(stage);
            try {
                Scanner input = new Scanner(file);

                int size = Integer.parseInt(input.nextLine().replace(" ", ""));
                coin = new int[size];

                String line = input.nextLine();
                String[] tokens = line.split(",");

if(tokens.length==size) {
    for (int i = 0; i < tokens.length; i++) {
        coin[i] = Integer.parseInt(tokens[i]);
        h2.getChildren().addAll(labelStyle(String.valueOf(coin[i])));

    }
    l2.setVisible(true);
    b3.setDisable(false);
}else{
    showErrorAlert("𝗘𝗥𝗥𝗢𝗥: The number of coins exceeds the maximum size entered....");

}
            } catch (Exception ex) {
                showErrorAlert("𝗘𝗥𝗥𝗢𝗥: 𝗣𝗟𝗘𝗔𝗦𝗘 𝗘𝗡𝗧𝗘𝗥 𝗔 𝗙𝗜𝗟𝗘...");

            }
        });


        v3.getChildren().addAll(l2, h2, b3);
        v3.setSpacing(15);
        v3.setAlignment(Pos.CENTER);

        ImageView backButtonimg = new ImageView(new Image("img/back1.png"));
        backButtonimg.setFitWidth(150);
        backButtonimg.setFitHeight(50);

        Button backButton = new Button("", backButtonimg);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setAlignment(Pos.CENTER);


        backButton.setOnAction(event -> {
            buttonSound(3);
            stage.setScene(mainMenuScene);
        });


        vbox.getChildren().addAll(v1, v3, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);


        root.setCenter(vbox);
        Scene scene = new Scene(root, 1024, 768);

        b3.setOnAction(e -> {
            buttonSound(2);
            oDp = new OptimalDp(coin);
            try {
                mediaPlayer.stop();
                if (ifOnePlayer == true) {
                    DpTableScreen dScreen = new DpTableScreen(oDp, mainMenuScene1,coin, stage);
                    stage.setScene(dScreen.getScene());
                } else {
                    TwoPlayerGame TwoPlayerScreen = new TwoPlayerGame(coin, mainMenuScene1, stage);
                    stage.setScene(TwoPlayerScreen.getScene());
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return scene;
    }


    private Label labelStyle(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.rgb(255, 255, 255));
        label.setPadding(new Insets(10));
        label.setBackground(new Background(new BackgroundFill(
                Color.rgb(34, 130, 216), new CornerRadii(5), Insets.EMPTY)));

        label.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));
        return label;
    }

    private Label labelTransition(String text) {
        Label label = new Label(text);
        label.setStyle(
                "-fx-background-color: #1d6acd; " +
                        "-fx-text-fill: #f9f9f9; " +
                        "-fx-padding: 10px; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold;"
        );

        TranslateTransition transition = new TranslateTransition();
        transition.setNode(label);
        transition.setDuration(Duration.seconds(3));  // Animation duration

        transition.setToX(-50);  // Move 200px to the right
        transition.setCycleCount(TranslateTransition.INDEFINITE);  // Repeat the animation
        transition.setAutoReverse(true);  // Make it move back and forth
        transition.play();  // Start the animation
// Optionally, you can adjust the font, padding, or other styles
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        label.setPadding(new Insets(10));
        return label;
    }


    public void showErrorAlert(String errorMessage) {
        buttonSound(5);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("⚠ 𝐄𝐑𝐑𝐎𝐑 𝐌𝐄𝐒𝐒𝐀𝐆𝐄 ⚠");

        // Customize header with emojis and text effects
        Text header = new Text("🚫 𝗖𝗢𝗜𝗡 𝗗𝗨𝗘𝗟 🚫");
        header.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        header.setStyle("-fx-fill: linear-gradient(to right, #2274dc, #d3d0c6); -fx-effect: dropshadow(one-pass-box, rgb(246,210,0), 3, 0.5, 0, 1);");
        alert.setHeaderText(null);

        // Add the custom header to the dialog pane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeader(header);

        // Customize content text with color and font weight
        Text content = new Text("❗ " + errorMessage + " ❗");
        content.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        content.setStyle("-fx-fill: #ffffff; -fx-effect: innershadow(one-pass-box, rgba(255,255,255,0.6), 2, 0.5, 0, 1);");
        dialogPane.setContent(content);

        // Set dialog pane styling for background and layout
        dialogPane.setStyle("-fx-background-image: url('img/3.png'); " +  // Replace with your image path
                "-fx-background-size: cover; " +
                "-fx-border-color: #d3d0c6; -fx-border-radius: 10; -fx-padding: 20px;");

        // Set minimum width for better readability
        dialogPane.setMinWidth(Region.USE_PREF_SIZE);

        // Style OK button with a rounded red background
        dialogPane.lookupButton(ButtonType.OK).setStyle(
                "-fx-background-color: #2274dc; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");

        Optional<ButtonType> result = alert.showAndWait();
    }

    private void playSound(boolean playe) {
        if (playe)
            mediaPlayer.play();
        else
            mediaPlayer.stop();
    }

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
        } else if (type == 4) {
            Media sound = new Media(getClass().getResource("sound/add.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } else if (type == 5) {
            Media sound = new Media(getClass().getResource("sound/error.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }


    }

    public static void main(String[] args) {
        launch();
    }


}

