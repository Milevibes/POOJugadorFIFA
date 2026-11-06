package operations;

import models.Equipo;
import models.Jugador;

import models.Jugador;
import java.io.*;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.sql.SQLOutput;
import java.util.Locale;

public class Operation {
    public static void savePlayer(Jugador jugadorToSave) {
        try {
            File archivo = new File("players.csv");
            FileWriter escribir = new FileWriter(archivo, true);

            escribir.write(jugadorToSave.playerToCSV() + "\n");
            escribir.close();
            System.out.println("Player Saved");

        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
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
            boolean encontrado = false;

            while ((playerReader = buffer.readLine()) != null) {
                String[] data_readed = playerReader.split(",");
                if (data_readed.length > 0 && data_readed[0].trim().equalsIgnoreCase(name.trim())){
                    System.out.println(playerReader);
                    encontrado = true;
                }

            }
            buffer.close();
            if (!encontrado){
                System.out.println("No se encontró el jugador con ese nombre");
            }
            System.out.println("All players have been read");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updatePlayer(String oldName, String newName, int year, int month, int day) {
        java.io.File inputFile = new java.io.File("players.csv");
        java.io.File tempFile = new java.io.File("players_temp.csv");

        boolean updated = false;

        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(inputFile));
             java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split(",");
                if (data.length > 0 && data[0].trim().equalsIgnoreCase(oldName.trim())) {
                    java.time.LocalDate fechaNacimiento = java.time.LocalDate.of(year, month, day);
                    int edadCalculada = java.time.Period.between(fechaNacimiento, java.time.LocalDate.now()).getYears();
                    String dorsal = data.length > 1 ? data[1] : "0";
                    String isAlive = data.length > 0 ? data[data.length-1].trim() : "true";

                    writer.println(newName + "," + dorsal + "," + edadCalculada + "," + isAlive);
                    updated = true;
                } else {
                    writer.println(currentLine);
                }
            }
            reader.close();
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
            return;
        }

            if (updated) {
                try {
                    if (inputFile.exists()) {
                        inputFile.delete();
                    }
                    java.nio.file.Files.move(tempFile.toPath(), inputFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Jugador actualizado con éxito en el archivo.");
                } catch (Exception e) {
                    System.out.println("Error al guardar los cambios: " + e.getMessage());
                }
            } else {
                    tempFile.delete();
                    System.out.println("No se encontró el jugador con ese nombre.");
            }
        }

    public static void deletePlayer(String nameToDelete) {
        java.io.File inputFile = new java.io.File("players.csv");
        java.io.File tempFile = new java.io.File("players_temp.csv");

        boolean deleted = false;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(inputFile));
             java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] data = currentLine.split(",");
                if (data.length > 0 && data[0].equals(nameToDelete.trim())) {
                    deleted = true;
                } else {
                    writer.println(currentLine);
                }
            }


        } catch (Exception e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
            return;
        }
        if (deleted) {
            try {
                java.nio.file.Files.move(tempFile.toPath(), inputFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Jugador eliminado con éxito.");}
                catch (Exception e){
                System.out.println("Error al actualizar el archivo: "+ e.getMessage());
            }
        } else {
            tempFile.delete();
            System.out.println("No sé encontró un jugador con ese nombre para eliminar");
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





















}
