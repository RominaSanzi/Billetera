package Billetera;

public class RentaFija extends Inversion {
    private double tasa;

    public RentaFija(int id, Cuenta origen, int plazoDias, double montoInvertido, double tasa) {
        super(id, origen, plazoDias, montoInvertido, true);
        this.tasa = tasa;
    }

    @Override
    public double calcularRentabilidadBase() {
        return getMontoInvertido() * tasa * getPlazoDias() / 365.0;
    }

    @Override
    public String descripcion() {
        return "Renta Fija";
    }

    @Override
    public String getActivo() {
        // La renta fija no esta sujeta a ningun activo cotizable.
        return null;
    }

}