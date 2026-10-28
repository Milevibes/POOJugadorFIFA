package operations;

import models.Jugador;

import models.Jugador;
import java.io.*;
import java.io.IOException;
import java.util.Locale;

public class Operation {
    public static void savePlayer(Jugador jugadorToSave) {
        try {
            FileWriter escribir = new FileWriter("players.csv", true);

            //escribir.write(jugadorToSave.playerToCSV());
            escribir.write(jugadorToSave.playerToCSV());
            escribir.write("\n");
            escribir.close();
            System.out.println("Player Saved");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readPlayers() {
        try {
            FileReader lector = new FileReader("players.csv");
            BufferedReader buffer = new BufferedReader(lector);
            String playerReader;

            while ((playerReader = buffer.readLine()) != null) {
                System.out.println(playerReader);
            }


            buffer.close();
            System.out.println("All players have been read");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readOnePlayer(String name) {
        try {
            FileReader lector = new FileReader("players.csv");
            BufferedReader buffer = new BufferedReader(lector);
            String playerReader;

            while ((playerReader = buffer.readLine()) != null) {

                String[] data_readed = playerReader.split(",");
                Jugador temp_player = new Jugador();
                temp_player.name = data_readed[0];
                temp_player.dorsal = Integer.parseInt(data_readed[1]);
                temp_player.is_alive = Boolean.parseBoolean(data_readed[3]);

                if (temp_player.name.equals(name)) {

                    System.out.println(playerReader);
                    //playerReader = null;
                }

            }


            buffer.close();
            System.out.println("All players have been read");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updatePlayer(String oldName, String newName, int year, int month, int day) {
        java.io.File inputFile = new java.io.File("players.csv");
        java.io.File tempFile = new java.io.File("players_temp.csv");

        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(inputFile));
             java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(tempFile))) {

            String currentLine;
            boolean updated = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split(",");
                if (data.length > 0 && data[0].equals(oldName)) {
                    String dorsal = data.length > 1 ? data[1] : "0";
                    String isAlive = data.length > 3 ? data[3] : "true";

                    writer.println(newName + "," + dorsal + "," + java.time.LocalDate.of(year, month, day) + "," + isAlive);
                    updated = true;
                } else {
                    writer.println(currentLine);
                }
            }

            if (updated) {
                System.out.println("Jugador actualizado con éxito en el archivo.");
            } else {
                System.out.println("No se encontró el jugador con ese nombre.");
            }

        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    public static void deletePlayer(String nameToDelete) {
        java.io.File inputFile = new java.io.File("players.csv");
        java.io.File tempFile = new java.io.File("players_temp.csv");

        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(inputFile));
             java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(tempFile))) {

            String currentLine;
            boolean deleted = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split(",");
                if (data.length > 0 && data[0].equals(nameToDelete)) {
                    deleted = true;
                } else {
                    writer.println(currentLine);
                }
            }

            if (deleted) {
                System.out.println("Jugador eliminado con éxito.");
            } else {
                System.out.println("No se encontró el jugador para eliminar.");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }

    }

    public static int generatePlayerId() {
        int id = 1;
        File file = new File("players.csv");
        if (!file.exists()) {
            return id;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                id++;
            }
        } catch (Exception e) {
            System.out.println("Error al generar el ID: " + e.getMessage());
        }
        return id;
    }

    //Operaciones con Equipos
    public static void createTeam(String teamName, String tecnicoName) {
        int id = Equipo.generateTeamId();

        // Estructura simple en CSV: ID, NombreEquipo, NombreTecnico
        String teamData = id + "," + teamName + "," + tecnicoName + "\n";

        File file = new File("teams.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(teamData);
            System.out.println("¡Equipo registrado con éxito! ID asignado: " + id);
        } catch (IOException e) {
            System.out.println("Error al guardar el equipo: " + e.getMessage());
            e.printStackTrace();
        }
    }



















}
