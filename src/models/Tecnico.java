package models;

public class Tecnico extends Persona{
    public String club;

    @Override
    public String toString() {
        return "models.Tecnico{" +
                "club='" + club + '\'' +
                "} " + super.toString();
    }
}

