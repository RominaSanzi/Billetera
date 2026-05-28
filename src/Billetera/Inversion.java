package Billetera;

import java.time.LocalDate;

public abstract class Inversion extends Actividad {

    private LocalDate fechaConstitucion;
    private int plazoDias;
    private double montoInvertido;
    private boolean precancelable;
    private boolean activa;        // true mientras no se haya precancelado ni vencido

    public Inversion(int id, Cuenta cuentaOrigen, int plazoDias,
                     double montoInvertido, boolean precancelable) {
        super(cuentaOrigen);            
        if (plazoDias <= 0) {
            throw new IllegalArgumentException("El plazo debe ser positivo");
        }
        if (montoInvertido <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        this.fechaConstitucion = Utilitarios.hoy();
        this.plazoDias = plazoDias;
        this.montoInvertido = montoInvertido;
        this.precancelable = precancelable;
        this.activa = true;
    }

   
    public abstract double calcularRentabilidadBase();

    public abstract String descripcion();

    public abstract String getActivo();

    public double calcularResultado() {
        double rentabilidad = calcularRentabilidadBase();
        if (!activa && precancelable) {
            return rentabilidad / 2.0;
        }
        return rentabilidad;
    }

    public void precancelar() {
        if (!precancelable) {
            throw new IllegalStateException("Esta inversion no es precancelable");
        }
        if (!activa) {
            throw new IllegalStateException("La inversion ya no esta activa");
        }
        this.activa = false;
    }

    public LocalDate fechaFin() {
        return fechaConstitucion.plusDays(plazoDias);
    }

    public boolean esPrecancelable() {
        return precancelable;
    }

    @Override
    public double obtenerMonto() {
        return montoInvertido;
    }
    
    protected double getMontoInvertido() {
        return montoInvertido;
    }

    protected int getPlazoDias() {
        return plazoDias;
    }

    public boolean estaActiva() {
        return activa;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fecha: ").append(fechaConstitucion).append("\n");
        sb.append("origen: ").append(getCuentaOrigen().getTitular().getDni()).append(" (").append(getCuentaOrigen().getCvu()).append(")\n");
        sb.append("desc: ").append(descripcion()).append("\n");
        sb.append("monto: ").append(montoInvertido).append("\n");
        sb.append("plazo: ").append(plazoDias).append("\n");
        sb.append(isAprobada() ? "Aprobado" : "Rechazado");
        return sb.toString();
    }
}