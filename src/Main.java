import models.Jugador;

import java.time.LocalDate;
import java.util.Scanner;
import operations.Operation;
import operations.OperationEquipo;

import static operations.Operation.*;

public class Main {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        int opcionPrincipal;

        do {
            System.out.println("\n********MENÚ PRINCIPAL FIFA*******");
            System.out.println("1. Menú Jugadores");
            System.out.println("2. Menú Equipos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcionPrincipal = leer.nextInt();

            switch (opcionPrincipal) {
                case 1:
                    menuJugadores(leer); break;
                case 2:
                    menuEquipos(leer); break;
                case 0:
                    System.out.println("Hasta luego :)");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcionPrincipal != 0);
    }
    public static void menuJugadores(Scanner leer) {
        int opcion;
        do {
            System.out.println("\n*****MENÚ DE JUGADORES*****");
            System.out.println("1. Crear Jugador");
            System.out.println("2. Buscar Jugador");
            System.out.println("3. Mostrar todos los Jugadores");
            System.out.println("4. Actualizar Jugador");
            System.out.println("5. Borrar Jugador");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = leer.nextInt();
            leer.nextLine();

            switch (opcion) {
                case 1: newPlayer(); break;
                case 2: findPlayer(); break;
                case 3: showAllPlayers(); break;
                case 4: updatePlayer(); break;
                case 5: deletePlayer(); break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    public static void menuEquipos(Scanner leer) {
        int opcion;
        do {
            System.out.println("\n*****MENÚ DE EQUIPOS*****");
            System.out.println("1. Crear Equipo");
            System.out.println("2. Listar Equipos");
            System.out.println("3. Actualizar Equipo");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = leer.nextInt();
            leer.nextLine();

            switch (opcion) {
                case 1: newTeam(); break;
                case 2: OperationEquipo.readTeams(); break;
                case 3: updateTeam(); break;
                case 0: break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    public static void newPlayer() {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el nombre del jugador");
        String name_player = leer.nextLine();
        System.out.println("Ingrese año de nacimiento");
        int year = leer.nextInt();
        leer.nextLine();
        System.out.println("Ingrese mes de naciemiento");
        int month = leer.nextInt();
        leer.nextLine();
        System.out.println("Ingrese día de nacimiento");
        int day = leer.nextInt();
        leer.nextLine();
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
        leer.nextLine();
        System.out.println("Ingrese el nuevo mes de nacimiento:");
        int month = leer.nextInt();
        leer.nextLine();
        System.out.println("Ingrese el nuevo día de nacimiento:");
        int day = leer.nextInt();
        leer.nextLine();

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

    public static void newTeam() {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el nombre del equipo:");
        String name_team = leer.nextLine();

        System.out.println("Ingrese el nombre del técnico del equipo:");
        String name_tecnico = leer.nextLine();

        System.out.println("Ingrese el club del técnico:");
        String club_tecnico = leer.nextLine();

        System.out.println("¿Cuántos jugadores desea agregar a la plantilla inicial?");
        int totalJugadores = leer.nextInt();
        leer.nextLine();

        String[] jugadoresEquipo = new String[totalJugadores];
        for (int i = 0; i < totalJugadores; i++) {
            System.out.println("Ingrese el nombre o ID del jugador " + (i + 1) + ":");
            jugadoresEquipo[i] = leer.nextLine();

            OperationEquipo.saveTeam(name_team, name_tecnico, club_tecnico, jugadoresEquipo);
        }
    }

    public static void updateTeam() {
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese el nombre del equipo que desea actualizar:");
        String oldName = leer.nextLine();
        }

}
