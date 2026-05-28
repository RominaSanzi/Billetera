package Billetera;

public class FondoLiquidezEmpresarial extends Inversion {

    private static final String ACTIVO = "FLE";
    private static final double TASA = 0.08;
    private static final double MIN_INVERSION = 20_000_000;

    public FondoLiquidezEmpresarial(int id, Cuenta origen, int plazoDias, double montoInvertido) {
        super(id, origen, plazoDias, montoInvertido, false);

        // Reglas de la consigna:
        if (!(origen instanceof CuentaCorporativa)) {
            throw new IllegalArgumentException(
                "El Fondo de Liquidez solo se invierte desde una cuenta corporativa");
        }
        if (montoInvertido < MIN_INVERSION) {
            throw new IllegalArgumentException(
                "El monto minimo para el Fondo de Liquidez es " + MIN_INVERSION);
        }
    }

    @Override
    public double calcularRentabilidadBase() {
        double cotizacion = Utilitarios.consultarCotizacion(ACTIVO);
        return getMontoInvertido() * TASA * getPlazoDias() / 365.0 * cotizacion;
    }

    @Override
    public String descripcion() {
        return "Fondo de Liquidez Empresarial";
    }

    @Override
    public String getActivo() {
        return ACTIVO;
    }

    @Override
    public boolean esPrecancelable() {
        return false;
    }

}