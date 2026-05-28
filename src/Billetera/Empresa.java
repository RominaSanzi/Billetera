package Billetera;

import java.util.HashSet;
import java.util.Set;

public class Empresa {

    private String cuit;
    private String razonSocial;
    private String telefono;
    private String email;
    private String nombreContacto;
    private Set<String> dnisAutorizados;

    public Empresa(String cuit, String razonSocial, String telefono, String email, String nombreContacto) {
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.email = email;
        this.nombreContacto = nombreContacto;
        this.dnisAutorizados = new HashSet<>();
    }

    public void autorizar(String dni) {
        dnisAutorizados.add(dni);
    }

    public boolean estaAutorizado(String dni) {
        return dnisAutorizados.contains(dni);
    }

    public String getCuit() {
        return cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    @Override
    public String toString() {
        return "Empresa{cuit=" + cuit + ", razonSocial=" + razonSocial + "}";
    }
}
