package Billetera;

import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta {

    protected String cvu;
    protected String alias;
    protected double saldo;
    protected Usuario titular;
    protected List<Actividad> actividades;

    public Cuenta(String cvu, String alias, Usuario titular) {
        this.cvu = cvu;
        this.alias = alias;
        this.saldo = 0;
        this.titular = titular;
        this.actividades = new ArrayList<>();
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser positivo.");
        }
        saldo += monto;
    }

    public void extraer(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a extraer debe ser positivo.");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        saldo -= monto;
    }

    public void registrarActividad(Actividad actividad) {
        actividades.add(actividad);
    }

    /** Por defecto no modifica el monto (cuentas comunes). */
    public double aplicarBeneficio(double monto, Actividad inversion) {
        return monto;
    }

    public int volumenDeTransacciones() {
        return actividades.size();
    }

    public String resumenDeTransacciones() {
        return "CVU " + cvu + " - actividades: " + actividades.size();
    }

    public double getSaldo() {
        return saldo;
    }

    public String getCvu() {
        return cvu;
    }

    public String getAlias() {
        return alias;
    }

    public Usuario getTitular() {
        return titular;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public abstract String tipo();

    /** Formato pedido en el enunciado: Tipo: alias (cvu) */
    public String descripcionCuenta() {
        return tipo() + ": " + alias + " (" + cvu + ")";
    }

    @Override
    public String toString() {
        return descripcionCuenta() + " saldo=" + saldo;
    }
}
