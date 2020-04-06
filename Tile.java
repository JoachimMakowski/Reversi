package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Tile extends Button {
    private int wartosc;
    private int x;
    private int y;
    private int opcja; //odpowiada, za to który kolor został wybrany
    public Tile(int war,int x,int y, int opcja){
        this.wartosc = war;
        this.x = x;
        this.y = y;
        this.opcja = opcja;
        /*setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println(x);
                System.out.println(y);
                if (wartosc==0) System.out.println("Accepted");
                else System.out.println("Not accepted");
            }
        });*/
        setPrefSize(Plansza.TILE_SIZE,Plansza.TILE_SIZE);
        if(this.wartosc==0) setStyle("-fx-background-color: #228B22");
        else if((this.wartosc==1 && this.opcja==1) || (this.wartosc==1 && this.opcja==2)) setStyle("-fx-background-color: #000000");
        else if(this.wartosc==2 && this.opcja==1 || (this.wartosc==1 && this.opcja==3)) setStyle("-fx-background-color: #FFFFFF");
        else if(this.wartosc==2 && this.opcja==2 || (this.wartosc==2 && this.opcja==3)) setStyle("-fx-background-color: #FF0000");
        else if (this.wartosc==-1) setStyle("-fx-background-color: #C0C0C0");
    }

    public void setWartosc(int wartosc) {
        this.wartosc = wartosc;
        if((this.wartosc==1 && this.opcja==1) || (this.wartosc==1 && this.opcja==2)) setStyle("-fx-background-color: #000000");
        else if(this.wartosc==2 && this.opcja==1 || (this.wartosc==1 && this.opcja==3)) setStyle("-fx-background-color: #FFFFFF");
        else if(this.wartosc==2 && this.opcja==2 || (this.wartosc==2 && this.opcja==3)) setStyle("-fx-background-color: #FF0000");
    }

    public int getWartosc() {
        return wartosc;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
