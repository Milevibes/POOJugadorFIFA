package operations;

import models.Equipo;
import models.Tecnico;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OperationEquipo {

    public static void saveTeam(String teamName, String tecnicoName, String tecnicoClub, String[] jugadores) {
        File file = new File("teams.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[1].trim().equalsIgnoreCase(teamName.trim())) {
                        System.out.println("El equipo '" + teamName + "' ya existe con el ID: " + parts[0].trim());
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int id = Equipo.generateTeamId();
        Tecnico tecnico = new Tecnico();
        tecnico.name = tecnicoName;
        tecnico.club = tecnicoClub;
        String teamData = id + "," + teamName + "," + tecnicoName + "," + tecnicoClub +  "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(teamData);
            System.out.println("Equipo guardado con éxito! ID asignado: " + id);
        } catch (IOException e) {
            System.out.println("Error al guardar el equipo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void mostrarJugadoresDeEquipo(String teamIdBuscado) {
        File file = new File("players.csv");
        if (!file.exists()) {
            System.out.println("Plantilla de Jugadores: [Sin jugadores registrados]");
            return;
        }

        boolean hayJugadores = false;
        System.out.print("Plantilla de Jugadores: ");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // La última columna es el idEquipo que relaciona al jugador con el equipo
                if (parts.length >= 4) {
                    String idEquipoJugador = parts[parts.length - 1].trim();
                    if (idEquipoJugador.equalsIgnoreCase(teamIdBuscado.trim())) {
                        String nombreJugador = parts[0].trim();
                        System.out.print("[" + nombreJugador + "] ");
                        hayJugadores = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer jugadores.");
        }

        if (!hayJugadores) {
            System.out.print("[Sin jugadores asignados]");
        }
        System.out.println();
    }

    public static void readTeams() {
        File file = new File("teams.csv");
        if (!file.exists()) {
            System.out.println("No hay equipos registrados todavía.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n          LISTA DE EQUIPOS              ");
            System.out.println("******************************************");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String teamId = parts[0].trim();
                    String teamName = parts[1].trim();
                    String tecnicoName = parts[2].trim();

                    System.out.println("Id: " + teamId + " | Equipo: " + teamName);
                    System.out.println(" Tecnico: " + tecnicoName);

                    if (parts.length >= 5) {
                        String nombresJugadores = parts[4].trim();
                        if (!nombresJugadores.equalsIgnoreCase("SinJugadores") && !nombresJugadores.isEmpty()) {
                            String[] arrayJugadores = nombresJugadores.split("-");
                            java.util.List<String> jugadoresValidos = new java.util.ArrayList<>();
                            for (String j : arrayJugadores) {
                                String limpio = j.trim();
                                if (!limpio.equalsIgnoreCase("null") && !limpio.isEmpty()) {
                                    jugadoresValidos.add(limpio);
                                }
                            }
                            if (!jugadoresValidos.isEmpty()) {
                                System.out.println("Plantilla de Jugadores: [" + String.join(",", jugadoresValidos) + "]");
                            } else {
                                System.out.println("Plantilla de Jugadores: [Sin jugadores]");
                            }
                        } else {
                            System.out.println("Plantilla de Jugadores: [Sin jugadores]");
                        }
                    }
                }
            }
        } catch(IOException e){
            System.out.println("Error al leer los equipos: " + e.getMessage());
        }
    }


    public static void searchTeam(String searchTerm) {
        File file = new File("teams.csv");
        if (!file.exists()) {
            System.out.println("No hay equipos registrados todavía.");
            return;
        }

        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String teamId = parts[0].trim();
                String teamName = parts[1].trim();
                String tecnicoName = parts[2].trim();
                String tecnicoClub = parts[3].trim();

                if (teamId.equalsIgnoreCase(searchTerm.trim()) || teamName.equalsIgnoreCase(searchTerm.trim())) {
                        System.out.println("\n          EQUIPO ENCONTRADO           ");
                        System.out.println("****************************************");
                        System.out.println("ID: " + teamId);
                        System.out.println("Equipo: " + teamName);
                        System.out.println("Tecnico: " + tecnicoName + " (" + tecnicoClub + ")");

                        mostrarJugadoresDeEquipo(teamId);
                        System.out.println("=================================================");
                        encontrado = true;
                        break;
                    }
                }


                if (!encontrado) {
                    System.out.println("No se encontró ningún equipo con el nombre o ID: " + searchTerm);
                }
            }

                } catch (IOException e) {
                System.out.println("Error al buscar el equipo: " + e.getMessage());
            }
        }

    }

