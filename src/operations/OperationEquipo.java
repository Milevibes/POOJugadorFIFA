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
        String listaJugadores = (jugadores != null && jugadores.length > 0) ? String.join("-", jugadores) : "SinJugadores";
        String teamData = id + "," + teamName + "," + tecnicoName + "," + tecnicoClub + listaJugadores +"\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(teamData);
            System.out.println("Equipo guardado con éxito! ID asignado: " + id);
        } catch (IOException e) {
            System.out.println("Error al guardar el equipo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void readTeams() {
        File file = new File("teams.csv");
        if (!file.exists()) {
            System.out.println("No hay equipos registrados todavía.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n========================================");
            System.out.println("          LISTA DE EQUIPOS              ");
            System.out.println("========================================");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String teamId = parts[0].trim();
                    String teamName = parts[1].trim();
                    String tecnicoName = parts[2].trim();

                    System.out.println("Id: " + teamId + " | Equipo: " + teamName);
                    System.out.println(" Tecnico: " + tecnicoName);

                    if (parts.length >= 4) {
                        String nombresJugadores = parts[3].trim();
                        if (!nombresJugadores.equalsIgnoreCase("SinJugadores") && !nombresJugadores.isEmpty()) {
                            String jugadoresFormateados = nombresJugadores.replace("-", ", ");
                            System.out.println("Plantilla de Jugadores: [" + jugadoresFormateados + "]");
                        } else {
                            System.out.println("Plantilla de Jugadores: [Sin jugadores]");
                        }
                    } else {
                        System.out.println("Plantilla de Jugadores: [Sin jugadores]");
                    }
                }
            }
        } catch (IOException e) {
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
                if (parts.length >= 3) {
                    String teamId = parts[0].trim();
                    String teamName = parts[1].trim();
                    String tecnicoName = parts[2].trim();

                    if (teamId.equalsIgnoreCase(searchTerm.trim()) || teamName.equalsIgnoreCase(searchTerm.trim())) {
                        System.out.println("\n******EQUIPO ENCONTRADO*****");
                        System.out.println("ID: " + teamId);
                        System.out.println("Equipo: " + teamName);
                        System.out.println("Tecnico: " + tecnicoName);

                        if (parts.length >= 4) {
                            String nombresJugadores = parts[3].trim();
                            if (!nombresJugadores.equalsIgnoreCase("SinJugadores") && !nombresJugadores.isEmpty()) {
                                String jugadoresFormateados = nombresJugadores.replace("-", ", ");
                                System.out.println("Plantilla de Jugadores: [" + jugadoresFormateados + "]");
                            } else {
                                System.out.println("Plantilla de Jugadores: [Sin jugadores]");
                            }
                        } else {
                            System.out.println("Plantilla de Jugadores: [Sin jugadores]");
                        }
                        encontrado = true;
                        break;
                    }
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró ningún equipo con el nombre o ID: " + searchTerm);
            }

        } catch (IOException e) {
            System.out.println("Error al buscar el equipo: " + e.getMessage());
        }
    }

}
