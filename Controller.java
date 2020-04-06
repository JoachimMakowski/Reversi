package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private static Stage stage;
    @FXML
    private ComboBox wybor_czasu;
    @FXML
    private CheckBox guzik6x6;
    @FXML
    private CheckBox guzik8x8;
    @FXML
    private CheckBox guzik10x10;
    @FXML
    private CheckBox guzikczarbia;
    @FXML
    private CheckBox guzikczarczer;
    @FXML
    private CheckBox guzikbiaczer;
    @FXML
    private RadioButton multiplayer;
    @FXML
    private static GridPane gridpane_plansza = new GridPane();
    @FXML
    private static GridPane gridpane_gra = new GridPane();
    @FXML
    private Opcje opcje = new Opcje("czarny-bialy", 1, 8, false);
    private static Scene scena;
    @FXML
    private AnchorPane optionsWindow;
    @FXML
    private AnchorPane startWindow;
    @FXML
    private AnchorPane gameWindow;

    private Zegar zegar;



    @FXML
    private void loadSecond(ActionEvent actionEvent) throws IOException {
        optionsWindow = FXMLLoader.load(getClass().getResource("opcje.fxml"));
        startWindow.getChildren().setAll(optionsWindow);
    }

    @FXML
    private void loadFirst(ActionEvent actionEvent) throws IOException {
        startWindow = FXMLLoader.load(getClass().getResource("sample.fxml"));
        optionsWindow.getChildren().setAll(startWindow);
    }

    @FXML
    private void loadStart(ActionEvent actionEvent) throws IOException {
        startWindow = FXMLLoader.load(getClass().getResource("sample.fxml"));
        gameWindow.getChildren().setAll(startWindow);
    }

    @FXML
    private void to_game(ActionEvent actionEvent) throws IOException {
        gameWindow = FXMLLoader.load(getClass().getResource("new_game.fxml"));
        optionsWindow = FXMLLoader.load(getClass().getResource("opcje.fxml"));
        gridpane_gra.setPrefSize(700, 700);
        gridpane_gra.setPadding(new Insets(3));
        gridpane_gra.setHgap(3);
        gridpane_gra.setVgap(3);
        gameWindow.getChildren().addAll(gridpane_gra);
        System.out.println(opcje.kolory);
        System.out.println(opcje.czas);
        System.out.println(opcje.multiplayer);
        System.out.println(opcje.rozmiar);
        gridpane_plansza.setPrefSize(500, 500);
        gridpane_plansza.setPadding(new Insets(10));
        gridpane_plansza.setHgap(10);
        gridpane_plansza.setVgap(10);
        Label zegarek = new Label();
        Button powrot_do_gry = new Button("Opcje");
        powrot_do_gry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gameWindow.getChildren().setAll(optionsWindow);

            }
        });
        //podstawa = plansza.CreateContent(podstawa);
        zegar = new Zegar(opcje.czas, zegarek);
        zegar.start();
        Plansza plansza = new Plansza(opcje.rozmiar, opcje.kolory, opcje.multiplayer, zegar);
        plansza.CreateContent(gridpane_plansza);
        gridpane_gra.add(gridpane_plansza, 1, 1);
        gridpane_gra.add(powrot_do_gry, 2, 2);
        gridpane_gra.add(zegarek, 2, 1);
        stage.setScene(new Scene(gameWindow));

    }

    @FXML
    private void wyjscie_z_programu(ActionEvent actionEvent) throws IOException {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void rozpocznij_gre(ActionEvent actionEvent) throws IOException {
        gameWindow = FXMLLoader.load(getClass().getResource("new_game.fxml"));
        optionsWindow = FXMLLoader.load(getClass().getResource("opcje.fxml"));
        gridpane_gra.setPrefSize(700, 700);
        gridpane_gra.setPadding(new Insets(3));
        gridpane_gra.setHgap(3);
        gridpane_gra.setVgap(3);
        gameWindow.getChildren().addAll(gridpane_gra);
        System.out.println(opcje.kolory);
        System.out.println(opcje.czas);
        System.out.println(opcje.multiplayer);
        System.out.println(opcje.rozmiar);
        gridpane_plansza.setPrefSize(500, 500);
        gridpane_plansza.setPadding(new Insets(10));
        gridpane_plansza.setHgap(10);
        gridpane_plansza.setVgap(10);
        Button powrot_do_gry = new Button("Opcje");
        powrot_do_gry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gameWindow.getChildren().setAll(optionsWindow);

            }
        });
        Label labelka = new Label();
        labelka.prefHeight(100);
        labelka.prefWidth(100);
        zegar = new Zegar(opcje.czas, labelka);
        zegar.start();
        Plansza plansza = new Plansza(opcje.rozmiar, opcje.kolory, opcje.multiplayer,zegar);
        plansza.CreateContent(gridpane_plansza);
        //podstawa = plansza.CreateContent(podstawa);
        gridpane_gra.add(gridpane_plansza, 1, 1);
        gridpane_gra.add(powrot_do_gry, 2, 2);
        gridpane_gra.add(labelka, 2, 1);
        stage.setScene(new Scene(gameWindow));
    }

    @FXML
    private void guzik1akcja(ActionEvent actionEvent) throws IOException {
        opcje.setRozmiar(6);
        guzik6x6.setSelected(true);
        guzik8x8.setSelected(false);
        guzik10x10.setSelected(false);
    }

    @FXML
    private void guzik2akcja(ActionEvent actionEvent) throws IOException {
        opcje.setRozmiar(8);
        guzik6x6.setSelected(false);
        guzik8x8.setSelected(true);
        guzik10x10.setSelected(false);
    }

    @FXML
    private void guzik3akcja(ActionEvent actionEvent) throws IOException {
        opcje.setRozmiar(10);
        guzik6x6.setSelected(false);
        guzik8x8.setSelected(false);
        guzik10x10.setSelected(true);
        System.out.println(opcje.rozmiar);
    }

    @FXML
    private void guzik4akcja(ActionEvent actionEvent) throws IOException {
        opcje.setKolory("czarny-bialy");
        guzikczarbia.setSelected(true);
        guzikbiaczer.setSelected(false);
        guzikczarczer.setSelected(false);
    }

    @FXML
    private void guzik5akcja(ActionEvent actionEvent) throws IOException {
        opcje.setKolory("czarny-czerwony");
        guzikczarczer.setSelected(true);
        guzikczarbia.setSelected(false);
        guzikbiaczer.setSelected(false);
    }

    @FXML
    private void guzik6akcja(ActionEvent actionEvent) throws IOException {
        opcje.setKolory("bialy-czerwony");
        guzikbiaczer.setSelected(true);
        guzikczarczer.setSelected(false);
        guzikczarbia.setSelected(false);
    }

    @FXML
    private void multiplayerAkcja(ActionEvent actionEvent) throws IOException {
        if (multiplayer.isSelected()) opcje.setMultiplayer(true);
        else opcje.setMultiplayer(false);
        System.out.println(opcje.multiplayer);
    }

    @FXML
    private void dlugosc_rozgrywki(ActionEvent actionEvent) throws IOException {
        int i = (int) wybor_czasu.getSelectionModel().getSelectedItem();
        System.out.println(i);
        opcje.setCzas(i);
    }


    public static void inicjalizacja(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Controller.class.getResource("sample.fxml"));
        stage = primaryStage;
        stage.setTitle("Witam w Reversi");
        scena = new Scene(root, 800, 800);
        stage.setScene(scena);
        //primaryStage.setResizable(false);
        stage.show();
    }
}

class Opcje {
    String kolory;
    int czas;
    int rozmiar;
    boolean multiplayer;

    public Opcje(String k, int czas, int rozmiar, boolean multiplayer) {
        this.kolory = k;
        this.czas = czas;
        this.rozmiar = rozmiar;
        this.multiplayer = multiplayer;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }

    public void setKolory(String kolory) {
        this.kolory = kolory;
    }

    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
    }

    public void setRozmiar(int rozmiar) {
        this.rozmiar = rozmiar;
    }
}
