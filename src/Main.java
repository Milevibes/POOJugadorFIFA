import models.Jugador;

import java.time.LocalDate;
import java.util.Scanner;

import static operations.Operation.*;

public class Main {
    public static void main(String[] args) {

        //models.Jugador newPlayer = new models.Jugador("Pele", LocalDate.of(1940, 10,23));
        //newPlayer.is_alive = false;
        //newPlayer.dorsal=10;
        //TODO crear menu para usar y operar las acciones de los jugadores

        Scanner leer = new Scanner(System.in);
        //System.out.println("Ingrese el jugador a buscar");
        //readPlayers();
        //readOnePlayer(leer.next());
        /*System.out.println("Ingrese el nombre del jugador");
        String name_player = leer.nextLine();
        System.out.println("Ingrese año de nacimiento");
        int year = leer.nextInt();
        System.out.println("Ingrese mes de naciemiento");
        int month = leer.nextInt();
        System.out.println("Ingrese día de nacimiento");
        int day = leer.nextInt();
        savePlayer(new Jugador(name_player, LocalDate.of(year,month,day)));
*/
        readPlayers();


        //Menu
        //Ingresar jugador -> pulsa 1
        //Leer todos jugadores -> pulsa 2
        //Buscar jugador -> pulsa 3

    }



}
