import java.util.Scanner;

import static operations.Operation.readOnePlayer;
import static operations.Operation.readPlayers;

public class Main {
    public static void main(String[] args) {

        //models.Jugador newPlayer = new models.Jugador("Pele", LocalDate.of(1940, 10,23));
        //newPlayer.is_alive = false;
        //newPlayer.dorsal=10;
        //TODO crear menu para usar y operar las acciones de los jugadores

        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el jugador a buscar");
        readPlayers();
        readOnePlayer(leer.next());

        //Menu
        //Ingresar jugador -> pulsa 1
        //Leer todos jugadores -> pulsa 2
        //Buscar jugador -> pulsa 3

    }



}
