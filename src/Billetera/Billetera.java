package Billetera;

import java.util.*;

public class Billetera implements IBilletera {

    @Override
    public void registrarUsuario(String dni, String nombre, String telefono, String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String crearCuentaRegular(String dniUsuario, String alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> obtenerCuentas(String dniUsuario) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double obtenerSaldoDisponible(String cvu) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa, double tasa) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void precancelarInversion(String dni, String cvu, int idInversion) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String consultarCvu(String alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> consultarHistorialGlobal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> consultarHistorialCuenta(String cvu) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> consultarHistorialUsuario(String dniUsuario) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double obtenerTotalInvertido(String dniUsuario) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> cuentasConMayorVolumen(int cantidadTop) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email, String nombreContacto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
        throw new UnsupportedOperationException();
    }
}
