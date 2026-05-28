package Billetera;

public class VinculadaADivisa extends Inversion {

    private String divisa;
    private double tasaSobreDivisa;

    public VinculadaADivisa(int id, Cuenta origen, int plazoDias,
                            double montoInvertido, String divisa, double tasaSobreDivisa) {
        super(id, origen, plazoDias, montoInvertido, true);
        if (divisa == null || divisa.isEmpty()) {
            throw new IllegalArgumentException("Divisa invalida");
        }
        this.divisa = divisa;
        this.tasaSobreDivisa = tasaSobreDivisa;
    }

    @Override
    public double calcularRentabilidadBase() {
        double cotizacion = Utilitarios.consultarCotizacion(divisa);
        return getMontoInvertido() * tasaSobreDivisa * getPlazoDias() / 365.0 * cotizacion;
    }

    @Override
    public String descripcion() {
        return "Vinculada a Divisa (" + divisa + ")";
    }

    @Override
    public String getActivo() {
        return divisa;
    }

}