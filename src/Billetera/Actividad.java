package Billetera;

import java.time.LocalDateTime;

/**
 * Actividad registrada en una cuenta (transferencias, inversiones, etc.).
 * Las subclases las completan Romina y Federico.
 */
public abstract class Actividad {

    private static int contadorIds = 1;

    protected int id;
    protected LocalDateTime fechaHora;
    protected Cuenta cuentaOrigen;
    protected boolean aprobada;

    public Actividad(Cuenta cuentaOrigen) {
        this.id = contadorIds++;
        this.fechaHora = LocalDateTime.now();
        this.cuentaOrigen = cuentaOrigen;
        this.aprobada = true;
    }

    public int getId() {
        return id;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    /** Para historiales (lo implementan las subclases). */
    public abstract String detalleActividad();
    /**
     * Monto asociado a la actividad. Lo define cada subclase:
     * la transferencia devuelve el monto transferido, la inversion el invertido.
     */
    public abstract double obtenerMonto();
}