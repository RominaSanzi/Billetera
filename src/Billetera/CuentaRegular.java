package Billetera;

public class CuentaRegular extends Cuenta {

    public static final double SALDO_MAX = 5_000_000;

    public CuentaRegular(String cvu, String alias, Usuario titular) {
        super(cvu, alias, titular);
    }

    @Override
    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser positivo.");
        }
        if (saldo + monto > SALDO_MAX) {
            throw new IllegalStateException("La cuenta regular no puede superar el saldo maximo permitido.");
        }
        saldo += monto;
    }

    @Override
    public String tipo() {
        return "Regular";
    }
}
