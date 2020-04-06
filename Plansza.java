package sample;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.util.StringTokenizer;

public class Plansza {
    public static int TILE_SIZE = 50;
    public static int MAX_WIDTH = 10;
    public static int MAX_HEIGHT = 10;
    private int odjemna;
    private int WIDTH;
    private int HEIGHT;
    private String kolor1;
    private String kolor2;
    private boolean Multiplayer;
    Tile[][] tablica_tile = new Tile[MAX_WIDTH][MAX_HEIGHT];
    private int ktory_gracz = 0;    //0 ruch gracza 1szego 1 ruch gracza 2
    private int opcja;              //opcja odpowiada za wybrane kolory
    private Zegar zegar;
    //private Group tileGroup = new Group();

    Plansza(int rozmiar, String kolory, boolean Multiplayer, Zegar zegar) {
        this.WIDTH = rozmiar;
        this.HEIGHT = rozmiar;
        this.Multiplayer = Multiplayer;
        this.zegar = zegar;
        if (rozmiar == 6) this.odjemna = 2;
        else if (rozmiar == 8) this.odjemna = 1;
        else if (rozmiar == 10) this.odjemna = 0;
        else System.out.println("Blad");
        StringTokenizer stringTokenizer = new StringTokenizer(kolory, "-");
        this.kolor1 = stringTokenizer.nextToken();
        this.kolor2 = stringTokenizer.nextToken();
        System.out.println(kolor1);
        System.out.println(kolor2);
        opcja = 0;
        if (this.kolor1.equals("bialy")) opcja = 3;
        else if (this.kolor2.equals("czerwony")) opcja = 2;
        else if (this.kolor1.equals("czarny") && this.kolor2.equals("bialy")) opcja = 1;
        System.out.println(opcja);
    }

    public GridPane CreateContent(GridPane pods) {
        //pods.setPrefSize(this.WIDTH*TILE_SIZE,this.HEIGHT*TILE_SIZE);
        pods.setPrefSize(500, 500);
        //pods.getChildren().addAll(tileGroup);
        System.out.println(opcja);
        Tile tile;
        for (int y = odjemna; y < MAX_HEIGHT - odjemna; y++)
            for (int x = odjemna; x < MAX_WIDTH - odjemna; x++) {
                if ((x == 4 && y == 5) || (x == 5 && y == 4)) {
                    tile = new Tile(1, x, y, opcja);
                } else if ((x == 4 && y == 4) || (x == 5 && y == 5)) {
                    tile = new Tile(2, x, y, opcja);
                } else {
                    tile = new Tile(0, x, y, opcja);
                }
                tablica_tile[x][y] = tile;
                int finalY = y;
                int finalX = x;
                tablica_tile[x][y].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        if (zegar.isAlive()) {
                            System.out.println(finalX);
                            System.out.println(finalY);
                            if (tablica_tile[finalX][finalY].getWartosc() == 0) {
                                System.out.println("Accepted");
                                czy_mozliwy_ruch(finalX, finalY);
                            } else System.out.println("Not accepted");
                        } else {
                            okno_koncowe();
                        }
                    }
                });

                pods.add(tile, x, y);
            }
        return pods;
    }


    public void czy_mozliwy_ruch(int x, int y) {
        System.out.println("cos");
        boolean prawo = (czy_mozliwy_ruch_prawo(x, y, true));
        boolean lewo = (czy_mozliwy_ruch_lewo(x, y, true));
        boolean gora = (czy_mozliwy_ruch_gora(x, y, true));
        boolean dol = (czy_mozliwy_ruch_dol(x, y, true));
        boolean gora_lewo = (czy_mozliwy_ruch_gora_lewo(x, y, true));
        boolean gora_prawo = (czy_mozliwy_ruch_gora_prawo(x, y, true));
        boolean dol_prawo = (czy_mozliwy_ruch_dol_prawo(x, y, true));
        boolean dol_lewo = (czy_mozliwy_ruch_dol_lewo(x, y, true));
        System.out.println("czy_mozliwy_ruch_prawo " + prawo);
        System.out.println("czy_mozliwy_ruch_lewo " + lewo);
        System.out.println("czy_mozliwy_ruch_gora " + gora);
        System.out.println("czy_mozliwy_ruch_dol " + dol);
        System.out.println("czy_mozliwy_ruch_gora_lewo " + gora_lewo);
        System.out.println("czy_mozliwy_ruch_gora_prawo " + gora_prawo);
        System.out.println("czy_mozliwy_ruch_dol_prawo " + dol_prawo);
        System.out.println("czy_mozliwy_ruch_dol_lewo " + dol_lewo);

        if (prawo || lewo || gora || dol || gora_lewo || gora_prawo || dol_prawo || dol_lewo) {
            boolean koniec_gry = czy_koniec_gry();
            if (Multiplayer) {
                ktory_gracz = 1 - ktory_gracz;
                boolean czy_moze_ruch_przeciwnik = czy_moze_wykonac_ruch();
                if (!czy_moze_ruch_przeciwnik) {
                    System.out.println("ZMIANA GRACZA!!!");
                    ktory_gracz = 1 - ktory_gracz;
                    boolean czy_moge_ja_ruch = czy_moze_wykonac_ruch();
                    if (!czy_moge_ja_ruch) {
                        System.out.println("KONIEC GRY - BRAK RUCHOW");
                        koniec_gry = czy_koniec_gry();
                        System.out.println("czy koniec gry" + koniec_gry);
                    }
                }
            } else {

                ktory_gracz = 1 - ktory_gracz;
                boolean komputer = czy_moze_ruch_komputer();
                System.out.println("komputer " + komputer);
                ktory_gracz = 1 - ktory_gracz;
                boolean gracz = czy_moze_wykonac_ruch();
                while (!gracz && komputer) {
                    ktory_gracz = 1 - ktory_gracz;
                    komputer = czy_moze_ruch_komputer();
                    ktory_gracz = 1 - ktory_gracz;
                    gracz = czy_moze_wykonac_ruch();
                    System.out.println("komputer " + komputer + "Gracz " + gracz);
                    if (!gracz && !komputer) {
                        koniec_gry = czy_koniec_gry();
                        if (koniec_gry) break;
                        System.out.println("Koniec gry - brak ruchow " + koniec_gry);
                    }
                }
                boolean puste_pola = false;
                if (!gracz && !komputer) {
                    koniec_gry = czy_koniec_gry();
                    puste_pola = false;
                    if (!koniec_gry) puste_pola = true;
                    System.out.println("Koniec gry - brak ruchow " + koniec_gry);

                }
                System.out.println("Gracz " + gracz);
                koniec_gry = czy_koniec_gry();
                if (koniec_gry || puste_pola) okno_koncowe();
                System.out.println("Koniec gry " + koniec_gry);
            }
        }
    }

    private boolean czy_mozliwy_ruch_prawo(int x, int y, boolean czy_ruch) {
        if (x + 1 < MAX_WIDTH - odjemna && tablica_tile[x + 1][y].getWartosc() == 2 - ktory_gracz) {

            for (int i = x + 1; i < MAX_WIDTH - odjemna; i++) {
                if (tablica_tile[i][y].getWartosc() == ktory_gracz + 1) {
                    if (czy_ruch) for (int j = x; j < i; j++) tablica_tile[j][y].setWartosc(ktory_gracz + 1);
                    return true;
                } else if (tablica_tile[i][y].getWartosc() == 0) return false;
            }
        }
        return false;
    }

    private boolean czy_mozliwy_ruch_lewo(int x, int y, boolean czy_ruch) {
        if (x - 1 > odjemna && tablica_tile[x - 1][y].getWartosc() == 2 - ktory_gracz) {

            for (int i = x - 1; i >= odjemna; i--) {
                if (tablica_tile[i][y].getWartosc() == ktory_gracz + 1) {
                    if (czy_ruch) for (int j = x; j > i; j--) tablica_tile[j][y].setWartosc(ktory_gracz + 1);
                    return true;
                } else if (tablica_tile[i][y].getWartosc() == 0) return false;
            }
        }
        return false;
    }

    private boolean czy_mozliwy_ruch_gora(int x, int y, boolean czy_ruch) {
        if (y - 1 > odjemna && tablica_tile[x][y - 1].getWartosc() == 2 - ktory_gracz) {

            for (int i = y - 1; i >= odjemna; i--) {
                if (tablica_tile[x][i].getWartosc() == ktory_gracz + 1) {
                    if (czy_ruch) for (int j = y; j > i; j--) tablica_tile[x][j].setWartosc(ktory_gracz + 1);
                    return true;
                } else if (tablica_tile[x][i].getWartosc() == 0) return false;
            }
        }
        return false;
    }

    private boolean czy_mozliwy_ruch_dol(int x, int y, boolean czy_ruch) {
        if (y + 1 < MAX_HEIGHT - odjemna && tablica_tile[x][y + 1].getWartosc() == 2 - ktory_gracz) {

            for (int i = y + 1; i < MAX_HEIGHT - odjemna; i++) {
                if (tablica_tile[x][i].getWartosc() == ktory_gracz + 1) {
                    if (czy_ruch) for (int j = y; j < i; j++) tablica_tile[x][j].setWartosc(ktory_gracz + 1);
                    return true;
                } else if (tablica_tile[x][i].getWartosc() == 0) return false;
            }
        }
        return false;
    }

    private boolean czy_mozliwy_ruch_gora_lewo(int x, int y, boolean czy_ruch) {
        if (x - 1 > odjemna && y - 1 > odjemna && tablica_tile[x - 1][y - 1].getWartosc() == 2 - ktory_gracz) {
            for (int i = 1; i <= Math.min(x - odjemna, y - odjemna); i++) {
                if (tablica_tile[x - i][y - i].getWartosc() == ktory_gracz + 1) {
                    if (czy_ruch) for (int j = 0; j < i; j++) tablica_tile[x - j][y - j].setWartosc(ktory_gracz + 1);
                    return true;
                } else if (tablica_tile[x - i][y - i].getWartosc() == 0) return false;
            }
        }
        return false;
    }

    private boolean czy_mozliwy_ruch_gora_prawo(int x, int y, boolean czy_ruch) {
        if (x + 1 < MAX_WIDTH - odjemna && y - 1 > odjemna && tablica_tile[x + 1][y - 1].getWartosc() == 2 - ktory_gracz) {
            for (int i = 1; i <= Math.min(MAX_WIDTH - odjemna - x - 1, y - odjemna); i++) {
                if (tablica_tile[x + i][y - i].getWartosc() == ktory_gracz + 1) {
                    if (czy_ruch) for (int j = 0; j < i; j++) tablica_tile[x + j][y - j].setWartosc(ktory_gracz + 1);
                    return true;
                } else if (tablica_tile[x + i][y - i].getWartosc() == 0) return false;
            }
        }
        return false;
    }

    private boolean czy_mozliwy_ruch_dol_lewo(int x, int y, boolean czy_ruch) {
        if (y + 1 < MAX_HEIGHT - odjemna && x - 1 > odjemna && tablica_tile[x - 1][y + 1].getWartosc() == 2 - ktory_gracz) {
            for (int i = 1; i <= Math.min(MAX_HEIGHT - odjemna - y - 1, x - odjemna); i++) {
                if (tablica_tile[x - i][y + i].getWartosc() == ktory_gracz + 1) {
                    if (czy_ruch) for (int j = 0; j < i; j++) tablica_tile[x - j][y + j].setWartosc(ktory_gracz + 1);
                    return true;
                } else if (tablica_tile[x - i][y + i].getWartosc() == 0) return false;
            }
        }
        return false;
    }

    private boolean czy_mozliwy_ruch_dol_prawo(int x, int y, boolean czy_ruch) {
        if (y + 1 < MAX_HEIGHT - odjemna && x + 1 < MAX_WIDTH - odjemna && tablica_tile[x + 1][y + 1].getWartosc() == 2 - ktory_gracz) {
            for (int i = 1; i <= Math.min(MAX_WIDTH - odjemna - x - 1, MAX_HEIGHT - odjemna - y - 1); i++) {
                if (tablica_tile[x + i][y + i].getWartosc() == ktory_gracz + 1) {
                    if (czy_ruch) for (int j = 0; j < i; j++) tablica_tile[x + j][y + j].setWartosc(ktory_gracz + 1);
                    return true;
                } else if (tablica_tile[x + i][y + i].getWartosc() == 0) return false;
            }
        }
        return false;
    }

    private void okno_koncowe()
    {
        int suma1 = 0;
        int suma2 = 0;
        for (int y = odjemna; y < MAX_HEIGHT - odjemna; y++)
            for (int x = odjemna; x < MAX_WIDTH - odjemna; x++) {
                if (tablica_tile[x][y].getWartosc() == 1) suma1 += 1;
                else if (tablica_tile[x][y].getWartosc() == 2) suma2 += 1;
            }
        System.out.println("WYNIKI:\nGracz1: " + suma1 + "\nGracz2: " + suma2);
        Stage stage = new Stage();
        stage.setTitle("Wyniki");
        GridPane gridpane = new GridPane();
        gridpane.setPrefSize(500, 500);
        gridpane.setPadding(new Insets(3));
        gridpane.setHgap(3);
        gridpane.setVgap(3);
        Label gracz1 = new Label("Gracz1: " + suma1);
        Label gracz2 = new Label("Gracz2: " + suma2);
        Label wyniki = new Label("Wyniki");
        Button wyjscie = new Button("Wyjscie");
        wyjscie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        gridpane.add(gracz1, 0, 1);
        gridpane.add(gracz2, 2, 1);
        gridpane.add(wyjscie, 1, 2);
        gridpane.add(wyniki, 1, 0);
        stage.setScene(new Scene(gridpane));
        stage.show();
    }


    private boolean czy_koniec_gry() {
        boolean wolne_miejsca = czy_nie_ma_wolnych_miejsc();
        boolean pionki_1_gracz = czy_nie_ma_pionkow_jeden_z_graczy(1);
        boolean pionki_2_gracz = czy_nie_ma_pionkow_jeden_z_graczy(2);
        if (wolne_miejsca || pionki_1_gracz || pionki_2_gracz) {
            System.out.println("KONIEC GRY!!!");
            //okno_koncowe();

            return true;
        } else return false;
    }

    private boolean czy_nie_ma_wolnych_miejsc() {
        for (int y = odjemna; y < MAX_HEIGHT - odjemna; y++)
            for (int x = odjemna; x < MAX_WIDTH - odjemna; x++) {
                if (tablica_tile[x][y].getWartosc() == 0) return false;
            }
        return true;
    }

    private boolean czy_nie_ma_pionkow_jeden_z_graczy(int gracz) {//gracz to numer 1 lub 2
        for (int y = odjemna; y < MAX_HEIGHT - odjemna; y++)
            for (int x = odjemna; x < MAX_WIDTH - odjemna; x++) {
                if (tablica_tile[x][y].getWartosc() == gracz) return false;
            }
        return true;
    }

    private boolean czy_moze_wykonac_ruch() {//trzeba zmienić gracza zeby sprawdzić ruch(najlepiej sprawdzić, czy przeciwnik ma ruch, jeśli tak to dać mu wykonać ruch
        for (int y = odjemna; y < MAX_HEIGHT - odjemna; y++)
            for (int x = odjemna; x < MAX_WIDTH - odjemna; x++)
                if (tablica_tile[x][y].getWartosc() == 0) {
                    boolean dol = czy_mozliwy_ruch_dol(x, y, false);
                    boolean gora = czy_mozliwy_ruch_gora(x, y, false);
                    boolean lewo = czy_mozliwy_ruch_lewo(x, y, false);
                    boolean prawo = czy_mozliwy_ruch_prawo(x, y, false);
                    boolean dol_lewo = czy_mozliwy_ruch_dol_lewo(x, y, false);
                    boolean dol_prawo = czy_mozliwy_ruch_dol_prawo(x, y, false);
                    boolean gora_lewo = czy_mozliwy_ruch_gora_lewo(x, y, false);
                    boolean gora_prawo = czy_mozliwy_ruch_gora_prawo(x, y, false);
                    if (dol || gora || lewo || prawo || dol_lewo || dol_prawo || gora_lewo || gora_prawo) return true;
                }
        return false;
    }

    private boolean czy_moze_ruch_komputer() {
        for (int y = odjemna; y < MAX_HEIGHT - odjemna; y++)
            for (int x = odjemna; x < MAX_WIDTH - odjemna; x++)
                if (tablica_tile[x][y].getWartosc() == 0) {
                    boolean dol = czy_mozliwy_ruch_dol(x, y, true);
                    boolean gora = czy_mozliwy_ruch_gora(x, y, true);
                    boolean lewo = czy_mozliwy_ruch_lewo(x, y, true);
                    boolean prawo = czy_mozliwy_ruch_prawo(x, y, true);
                    boolean dol_lewo = czy_mozliwy_ruch_dol_lewo(x, y, true);
                    boolean dol_prawo = czy_mozliwy_ruch_dol_prawo(x, y, true);
                    boolean gora_lewo = czy_mozliwy_ruch_gora_lewo(x, y, true);
                    boolean gora_prawo = czy_mozliwy_ruch_gora_prawo(x, y, true);
                    if (dol || gora || lewo || prawo || dol_lewo || dol_prawo || gora_lewo || gora_prawo) return true;
                }
        return false;
    }
}
