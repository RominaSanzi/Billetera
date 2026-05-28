package Billetera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {

    private String dni;
    private String nombre;
    private String telefono;
    private String email;
    private Map<String, Cuenta> cuentas;
    private double totalInvertido;

    public Usuario(String dni, String nombre, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.cuentas = new HashMap<>();
        this.totalInvertido = 0;
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.put(cuenta.getCvu(), cuenta);
    }

    public void actualizarTotalInvertido(double monto) {
        totalInvertido = monto;
    }

    public List<Cuenta> obtenerCuentasLista() {
        return new ArrayList<>(cuentas.values());
    }

    public String getDni() {
        return dni;
    }

    public double getTotalInvertido() {
        return totalInvertido;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario{dni=").append(dni);
        sb.append(", nombre=").append(nombre);
        sb.append(", cuentas=").append(cuentas.size());
        sb.append("}");
        return sb.toString();
    }
}
