public class Jugador extends Persona {
    public int dorsal;
    public Posicion posicion;

    @Override
    public String toString() {
        return "Jugador{" +
                "dorsal=" + dorsal +
                "} " + super.toString();
    }
}
