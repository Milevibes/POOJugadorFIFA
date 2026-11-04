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
            int id = Equipo.generateTeamId();

            Tecnico tecnico = new Tecnico();
            tecnico.name = tecnicoName;
            tecnico.club = tecnicoClub;
            String listaJugadores = String.join("-", jugadores);

            String teamData = id + "," + teamName + "," + tecnicoName + "," + tecnico.club + "\n";

            File file = new File("teams.csv");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(teamData);
                System.out.println("Equipo guardado con éxito! ID asignado: " + id);
            } catch (IOException e) {
                System.out.println("Error al guardar el equipo: " + e.getMessage());
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
                if (parts.length >= 4) {
                    System.out.println("▪ ID: " + parts[0] + " | Equipo: " + parts[1]);
                    System.out.println("  DT: " + parts[2] + " (" + parts[3] + ")");

                    // Mostrar jugadores de forma limpia si existen
                    if (parts.length >= 5 && !parts[4].isEmpty()) {
                        String jugadoresFormateados = parts[4].replace("-", ", ");
                        System.out.println("  Plantilla: [ " + jugadoresFormateados + " ]");
                    } else {
                        System.out.println("  Plantilla: [ Sin jugadores ]");
                    }
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer los equipos: " + e.getMessage());
        }
    }


    }
