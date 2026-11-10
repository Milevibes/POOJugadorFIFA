package operations;

import models.Equipo;
import models.Tecnico;

import java.io.*;
import java.util.Scanner;

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
        String teamData = id + "," + teamName + "," + tecnicoName + "," + tecnicoClub + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(teamData);
            System.out.println("Equipo guardado con éxito! ID asignado: " + id);
        } catch (IOException e) {
            System.out.println("Error al guardar el equipo: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        if (jugadores != null && jugadores.length > 0) {
            updateJugadoresConEquipo(jugadores, id);
        }else {
            System.out.println("Aviso: No se recibieron jugadores para asociar a este equipo.");
        }
    }
    public static String verificarEstadoJugador(String entrada, String[] jugadoresEquipoActual) {
        if (entrada == null || entrada.trim().isEmpty()) {
            return "La entrada no puede estar vacía.";
        }

        // 1. Verificar si ya fue ingresado previamente en este mismo registro de equipo actual
        for (String jActual : jugadoresEquipoActual) {
            if (jActual != null && jActual.trim().equalsIgnoreCase(entrada.trim())) {
                return "Ya ingresaste a este jugador en la plantilla actual.";
            }
        }

        File file = new File("players.csv");
        if (!file.exists()) {
            return "El archivo players.csv no existe.";
        }

        boolean jugadorEncontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int numeroDeLinea = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                numeroDeLinea++;

                String[] data = line.split(",");
                if (data.length >= 3) {
                    String nombreJugador = data[0].trim();
                    // Asumiendo que el ID/dorsal es la columna 1 o puedes adaptarlo según tu estructura
                    String idDorsal = data[1].trim();

                    // Comprobamos si coincide por ID o por Nombre
                    boolean coincide = idDorsal.equalsIgnoreCase(entrada.trim()) ||
                            nombreJugador.equalsIgnoreCase(entrada.trim());

                    if (coincide) {
                        jugadorEncontrado = true;

                        // Verificamos si ya tiene equipo asignado
                        // (Asumiendo que el ID del equipo está en la última columna, ej: data.length >= 5)
                        boolean yaTieneEquipo = data.length >= 5 && !data[data.length - 1].trim().isEmpty();

                        if (yaTieneEquipo) {
                            return "El jugador " + nombreJugador + " ya pertenece a otro equipo. Use la opción de transferir.";
                        }

                        // Si pasó todas las validaciones del archivo, está OK
                        return "OK";
                    }
                }
            }
        } catch (IOException e) {
            return "Error al leer el archivo: " + e.getMessage();
        }

        if (!jugadorEncontrado) {
            return "El jugador '" + entrada + "' no existe.";
        }

        return "OK";
    }

    private static void updateJugadoresConEquipo(String[] nombresJugadores, int idEquipo) {
        File inputFile = new File("players.csv");
        if (!inputFile.exists()) {
            System.out.println("Error: El archivo players.csv no existe.");
            return;
        }

        File tempFile = new File("players_temp.csv");
        boolean actualizadoAlguno = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String currentLine;
            int numeroDeLinea = 0;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().isEmpty()) continue;
                numeroDeLinea++;

                String[] data = currentLine.split(",");
                if (data.length >= 3) {
                    String nombreJugador = data[0].trim();
                    String dorsal = data[1].trim();
                    String edad = data[2].trim();
                    String estado = (data.length > 3) ? data[3].trim() : "true";

                    boolean yaTieneEquipo = (data.length >= 5 && !data[data.length - 1].trim().isEmpty());

                    boolean estaSeleccionado = false;
                    for (String entrada : nombresJugadores) {
                        if (entrada != null && !entrada.trim().isEmpty()) {
                            try {
                                int idBuscado = Integer.parseInt(entrada.trim());
                                if (idBuscado == numeroDeLinea) {
                                    estaSeleccionado = true;
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                if (entrada.trim().equalsIgnoreCase(nombreJugador)) {
                                    estaSeleccionado = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (estaSeleccionado) {
                        if (yaTieneEquipo) {
                            System.out.println("El jugador " + nombreJugador + " ya pertenece a otro equipo. Use la opción de transferir al jugador.");
                            writer.println(currentLine);
                        } else {
                            writer.println(nombreJugador + "," + dorsal + "," + edad + "," + estado + "," + idEquipo);
                            System.out.println("Jugador vinculado con éxito al equipo ID " + idEquipo + ": " + nombreJugador + "!");
                            actualizadoAlguno = true;
                        }
                    } else {
                        writer.println(currentLine);
                    }
                } else {
                    writer.println(currentLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar la relación de los jugadores: " + e.getMessage());
        }
        if (!actualizadoAlguno) {
            System.out.println("Ninguno de los nombres ingresados coincidió con los jugadores en players.csv.");
        }

        if (inputFile.exists()) {
            inputFile.delete();
        }
        tempFile.renameTo(inputFile);
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
                if (parts.length >= 5) {
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
        boolean hayEquipos = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n          LISTA DE EQUIPOS              ");
            System.out.println("******************************************");

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    hayEquipos = true;
                    String teamId = parts[0].trim();
                    String teamName = parts[1].trim();
                    String tecnicoName = parts[2].trim();
                    String tecnicoClub = parts[3].trim();

                    System.out.println("Id: " + teamId + " | Equipo: " + teamName);
                    System.out.println(" Tecnico: " + tecnicoName + " (" + tecnicoClub + ")");

                    mostrarJugadoresDeEquipo(teamId);
                    System.out.println("*******************************************");
                }
            }
            if (!hayEquipos) {
                System.out.println("No hay equipos registrados todavía.");
            }

        } catch (IOException e) {
            System.out.println("Error al leer los equipos: " + e.getMessage());
        }
    }


    public static void searchTeam(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) return;


        File file = new File("teams.csv");
        if (!file.exists()) {
            System.out.println("No hay equipos registrados todavía.");
            return;
        }

        boolean encontrado = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

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
                        System.out.println("****************************************");
                        encontrado = true;
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al buscar el equipo: " + e.getMessage());
        }
        if (!encontrado) {
            System.out.println("No se encontró ningún equipo con el nombre o ID: " + searchTerm);
        }
    }
    public static void updateTeam(String searchTerm) {
        File inputFile = new File("teams.csv");
        if (!inputFile.exists()) {
            System.out.println("No hay equipos registrados.");
            return;
        }

        File tempFile = new File("teams_temp.csv");
        boolean actualizado = false;
        Scanner leer = new Scanner(System.in);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();

                    if (id.equalsIgnoreCase(searchTerm.trim()) || name.equalsIgnoreCase(searchTerm.trim())) {
                        System.out.println("Equipo encontrado: " + name + " (ID: " + id + ")");
                        System.out.println("Ingrese el nuevo nombre del equipo:");
                        String nuevoNombre = leer.nextLine();
                        System.out.println("Ingrese el nuevo nombre del técnico:");
                        String nuevoTecnico = leer.nextLine();
                        System.out.println("Ingrese el nuevo club del técnico:");
                        String nuevoClub = leer.nextLine();

                        writer.println(id + "," + nuevoNombre.trim() + "," + nuevoTecnico.trim() + "," + nuevoClub.trim());
                        System.out.println("¡Equipo actualizado con éxito!");
                        actualizado = true;
                    } else {
                        writer.println(line);
                    }
                } else {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar equipo: " + e.getMessage());
        }

        if (inputFile.exists()) inputFile.delete();
        tempFile.renameTo(inputFile);

        if (!actualizado) {
            System.out.println("No se encontró ningún equipo para actualizar con: " + searchTerm);
        }
    }

    public static void deleteTeam(String searchTerm) {
        File inputFile = new File("teams.csv");
        if (!inputFile.exists()) {
            System.out.println("No hay equipos registrados.");
            return;
        }

        File tempFile = new File("teams_temp.csv");
        boolean borrado = false;
        String idEquipoBorrado = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();

                    if (id.equalsIgnoreCase(searchTerm.trim()) || name.equalsIgnoreCase(searchTerm.trim())) {
                        idEquipoBorrado = id;
                        borrado = true;
                        System.out.println("¡Equipo '" + name + "' (ID: " + id + ") eliminado con éxito!");
                    } else {
                        writer.println(line);
                    }
                } else {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al borrar el equipo: " + e.getMessage());
        }

        if (inputFile.exists()) inputFile.delete();
        tempFile.renameTo(inputFile);

        if (borrado && !idEquipoBorrado.isEmpty()) {
            liberarJugadoresDeEquipo(idEquipoBorrado);
        } else {
            System.out.println("No se encontró ningún equipo para borrar con: " + searchTerm);
        }
    }
        private static void liberarJugadoresDeEquipo(String idEquipo) {
            File inputFile = new File("players.csv");
            if (!inputFile.exists()) return;

            File tempFile = new File("players_temp.csv");

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        String equipoJugador = parts[parts.length - 1].trim();
                        if (equipoJugador.equalsIgnoreCase(idEquipo)) {

                            writer.println(parts[0].trim() + "," + parts[1].trim() + "," + parts[2].trim() + "," + parts[3].trim());
                        } else {
                            writer.println(line);
                        }
                    } else {
                        writer.println(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al liberar jugadores: " + e.getMessage());
            }

            if (inputFile.exists()) inputFile.delete();
            tempFile.renameTo(inputFile);
        }

        public static void transferPlayer(String playerIdentifier, String nuevoTeamSearch) {
            File teamFile = new File("teams.csv");
            if (!teamFile.exists()) {
                System.out.println("No hay equipos registrados.");
                return;
            }

            String nuevoTeamId = null;
            try (BufferedReader reader = new BufferedReader(new FileReader(teamFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        if (parts[0].trim().equalsIgnoreCase(nuevoTeamSearch.trim()) || parts[1].trim().equalsIgnoreCase(nuevoTeamSearch.trim())) {
                            nuevoTeamId = parts[0].trim();
                            break;
                        }
                    }
                }
            } catch (IOException e) {}

            if (nuevoTeamId == null) {
                System.out.println("No se encontró el equipo destino: " + nuevoTeamSearch);
                return;
            }

            File inputFile = new File("players.csv");
            if (!inputFile.exists()) {
                System.out.println("No hay jugadores registrados.");
                return;
            }

            File tempFile = new File("players_temp.csv");
            boolean transferido = false;
            int numeroDeLinea = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    numeroDeLinea++;
                    String[] data = line.split(",");

                    if (data.length >= 3) {
                        String nombre = data[0].trim();
                        boolean coincide = false;

                        try {
                            int idBuscado = Integer.parseInt(playerIdentifier.trim());
                            if (idBuscado == numeroDeLinea) coincide = true;
                        } catch (NumberFormatException e) {}

                        if (playerIdentifier.trim().equalsIgnoreCase(nombre)) {
                            coincide = true;
                        }

                        if (coincide) {
                            writer.println(nombre + "," + data[1].trim() + "," + data[2].trim() + "," + data[3].trim() + "," + nuevoTeamId);
                            System.out.println("--> ¡Jugador " + nombre + " transferido al equipo ID " + nuevoTeamId + " con éxito!");
                            transferido = true;
                        } else {
                            writer.println(line);
                        }
                    } else {
                        writer.println(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error en la transferencia: " + e.getMessage());
            }

            if (inputFile.exists()) inputFile.delete();
            tempFile.renameTo(inputFile);

            if (!transferido) {
                System.out.println("No se encontró ningún jugador con el nombre o ID: " + playerIdentifier);
            }
        }
    }


