import models.Jugador;

import java.time.LocalDate;
import java.util.Scanner;
import operations.Operation;

import static operations.Operation.*;

public class Main {
    public static void main(String[] args) {

        //models.Jugador newPlayer = new models.Jugador("Pele", LocalDate.of(1940, 10,23));
        //newPlayer.is_alive = false;
        //newPlayer.dorsal=10;
        //TODO crear menu para usar y operar las acciones de los jugadores

        Scanner leer = new Scanner(System.in);


        int opcion;
        do {
            System.out.println("\n *********** CRUD FIFAS *********** ");
            System.out.println("1. Crear Jugador");
            System.out.println("2. Buscar Jugador");
            System.out.println("3. Mostrar jugadores");
            System.out.println("4. Actualizar Jugador");
            System.out.println("5. Borrar Jugador");
            //TODO actualizar y borrar
            System.out.println("0. Salir");
            System.out.println("******************** ");

            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    newPlayer();
                    break;
                case 2:
                    findPlayer();
                    break;
                case 3:
                    showAllPlayers();
                    break;
                case 4:
                    updatePlayer();
                    break;
                case 5:
                    deletePlayer();
                    break;
                case 0:
                    System.out.println("Nos vemos");
                    break;
                default:
                    System.out.println("Opción invalida");


            }
        }
        while (opcion!=0);

    }

    public static void newPlayer() {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el nombre del jugador");
        String name_player = leer.nextLine();
        System.out.println("Ingrese año de nacimiento");
        int year = leer.nextInt();
        System.out.println("Ingrese mes de naciemiento");
        int month = leer.nextInt();
        System.out.println("Ingrese día de nacimiento");
        int day = leer.nextInt();
        savePlayer(new Jugador(name_player, LocalDate.of(year, month, day)));
    }

    public static void findPlayer() {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el jugador a buscar");
        readOnePlayer(leer.next());

    }

    public static void showAllPlayers() {
        readPlayers();

    }
    public static void updatePlayer() {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el nombre actual del jugador que desea actualizar:");
        String oldName = leer.nextLine();

        System.out.println("Ingrese el NUEVO nombre del jugador:");
        String newName = leer.nextLine();

        System.out.println("Ingrese el nuevo año de nacimiento:");
        int year = leer.nextInt();
        System.out.println("Ingrese el nuevo mes de nacimiento:");
        int month = leer.nextInt();
        System.out.println("Ingrese el nuevo día de nacimiento:");
        int day = leer.nextInt();

        Operation.updatePlayer(oldName, newName, year, month, day);
        System.out.println("Proceso de actualización preparado.");
    }
    public static void deletePlayer() {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el nombre del jugador a eliminar:");
        String name_player = leer.nextLine();

        Operation.deletePlayer(name_player);
        System.out.println("Proceso de eliminación preparado.");
    }


}
