package Billetera;

public class VinculadaADivisa extends Inversion {

    private String divisa;
    private double tasaSobreDivisa;
    private double cotizacionInicial;

    public VinculadaADivisa(int id, Cuenta origen, int plazoDias,
                            double montoInvertido, String divisa, double tasaSobreDivisa) {
        super(id, origen, plazoDias, montoInvertido, true);
        if (divisa == null || divisa.isEmpty()) {
            throw new IllegalArgumentException("Divisa invalida");
        }
        this.divisa = divisa;
        this.tasaSobreDivisa = tasaSobreDivisa;
        this.cotizacionInicial = Utilitarios.consultarCotizacion(divisa);
    }

    @Override
    public double calcularRentabilidadBase() {
        double cotizacionActual = Utilitarios.consultarCotizacion(divisa);
        double divisas = getMontoInvertido() / cotizacionInicial;
        double exchangeGain = divisas * (cotizacionActual - cotizacionInicial);
        double interest = divisas * tasaSobreDivisa * getPlazoDias() / 365.0 * cotizacionActual;
        return exchangeGain + interest;
    }

    @Override
    protected double calcularResultadoPrecancelacion() {
        double cotizacionActual = Utilitarios.consultarCotizacion(divisa);
        double divisas = getMontoInvertido() / cotizacionInicial;
        long diasTranscurridos = obtenerDiasTranscurridos();
        double exchangeGain = divisas * (cotizacionActual - cotizacionInicial);
        double interest = divisas * tasaSobreDivisa * diasTranscurridos / 365.0 * cotizacionActual / 2.0;
        return exchangeGain + interest;
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