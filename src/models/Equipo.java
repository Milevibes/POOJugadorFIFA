package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Equipo {
    private String teamName;
    private Tecnico teamTecnico;
    private Jugador[] plantilla;

    public Equipo(String teamName, Tecnico teamTecnico, Jugador[] plantilla) {
        this.teamName = teamName;
        this.teamTecnico = teamTecnico;
        this.plantilla = plantilla;
    }

    public String getTeamName() {
        return teamName;
    }

    public static int generateTeamId() {
        int id = 1;
        File file = new File("teams.csv");
        if (!file.exists()) {
            return id;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                id++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}

}
