package Billetera;

public class CuentaPremium extends Cuenta {

    public static final double SALDO_MIN = 500_000;

    public CuentaPremium(String cvu, String alias, Usuario titular) {
        super(cvu, alias, titular);
    }

    @Override
    public void extraer(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a extraer debe ser positivo.");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        if (saldo - monto < SALDO_MIN) {
            throw new IllegalStateException("La cuenta premium debe mantener el saldo minimo.");
        }
        saldo -= monto;
    }

    @Override
    public double aplicarBeneficio(double monto, Actividad inversion) {
        return monto * 1.05;
    }

    @Override
    public String tipo() {
        return "Premium";
    }
}
