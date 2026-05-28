package Billetera;

import java.util.*;

public class Billetera implements IBilletera {

    private Map<String, Usuario> usuarios;
    private Map<String, Empresa> empresas;
    private Map<String, Cuenta> cuentasPorCvu;
    private Map<String, String> cvuPorAlias;

    public Billetera() {
        usuarios = new HashMap<>();
        empresas = new HashMap<>();
        cuentasPorCvu = new HashMap<>();
        cvuPorAlias = new HashMap<>();
    }

    // --- metodos de Tomas (cuentas, usuarios, empresas) ---

    @Override
    public void registrarUsuario(String dni, String nombre, String telefono, String email) {
        validarTexto(dni, "dni");
        validarTexto(nombre, "nombre");
        validarTexto(telefono, "telefono");
        validarTexto(email, "email");
        if (usuarios.containsKey(dni)) {
            throw new IllegalArgumentException("El usuario ya esta registrado.");
        }
        usuarios.put(dni, new Usuario(dni, nombre, telefono, email));
    }

    @Override
    public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email,
            String nombreContacto) {
        validarTexto(cuit, "cuit");
        validarTexto(nombreFantasia, "nombreFantasia");
        validarTexto(telefono, "telefono");
        validarTexto(email, "email");
        validarTexto(nombreContacto, "nombreContacto");
        if (empresas.containsKey(cuit)) {
            throw new IllegalArgumentException("La empresa ya esta registrada.");
        }
        empresas.put(cuit, new Empresa(cuit, nombreFantasia, telefono, email, nombreContacto));
    }

    @Override
    public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
        validarTexto(cuitEmpresa, "cuitEmpresa");
        validarTexto(dniAutorizado, "dniAutorizado");
        Empresa empresa = buscarEmpresa(cuitEmpresa);
        if (empresa.estaAutorizado(dniAutorizado)) {
            throw new IllegalArgumentException("La persona ya esta autorizada.");
        }
        empresa.autorizar(dniAutorizado);
    }

    @Override
    public String crearCuentaRegular(String dniUsuario, String alias) {
        Usuario usuario = buscarUsuario(dniUsuario);
        validarAliasNuevo(alias);
        String cvu = Utilitarios.generarSiguienteCvu();
        CuentaRegular cuenta = new CuentaRegular(cvu, alias, usuario);
        registrarCuenta(cuenta);
        return cvu;
    }

    @Override
    public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
        if (depositoInicial < CuentaPremium.SALDO_MIN) {
            throw new IllegalArgumentException(
                    "El deposito inicial no alcanza el minimo de la cuenta premium.");
        }
        Usuario usuario = buscarUsuario(dniUsuario);
        validarAliasNuevo(alias);
        String cvu = Utilitarios.generarSiguienteCvu();
        CuentaPremium cuenta = new CuentaPremium(cvu, alias, usuario);
        cuenta.depositar(depositoInicial);
        registrarCuenta(cuenta);
        return cvu;
    }

    @Override
    public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
        Usuario usuario = buscarUsuario(dniUsuario);
        Empresa empresa = buscarEmpresa(cuitEmpresa);
        if (!empresa.estaAutorizado(dniUsuario)) {
            throw new IllegalArgumentException("El usuario no esta autorizado para la empresa.");
        }
        validarAliasNuevo(alias);
        String cvu = Utilitarios.generarSiguienteCvu();
        CuentaCorporativa cuenta = new CuentaCorporativa(cvu, alias, usuario, empresa);
        registrarCuenta(cuenta);
        return cvu;
    }

    @Override
    public List<String> obtenerCuentas(String dniUsuario) {
        Usuario usuario = buscarUsuario(dniUsuario);
        List<String> resultado = new ArrayList<>();
        for (Cuenta cuenta : usuario.obtenerCuentasLista()) {
            resultado.add(cuenta.descripcionCuenta());
        }
        return resultado;
    }

    @Override
    public double obtenerSaldoDisponible(String cvu) {
        return buscarCuenta(cvu).getSaldo();
    }

    @Override
    public String consultarCvu(String alias) {
        validarTexto(alias, "alias");
        if (!cvuPorAlias.containsKey(alias)) {
            throw new IllegalArgumentException("El alias no esta registrado.");
        }
        return cvuPorAlias.get(alias);
    }

    // --- resto del equipo (por ahora sin implementar) ---

    @Override
    public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
            double tasa) {
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

    // --- helpers para el grupo ---

    void registrarCuenta(Cuenta cuenta) {
        cuentasPorCvu.put(cuenta.getCvu(), cuenta);
        cvuPorAlias.put(cuenta.getAlias(), cuenta.getCvu());
        cuenta.getTitular().agregarCuenta(cuenta);
    }

    Usuario buscarUsuario(String dni) {
        if (!usuarios.containsKey(dni)) {
            throw new IllegalArgumentException("El usuario no existe.");
        }
        return usuarios.get(dni);
    }

    Empresa buscarEmpresa(String cuit) {
        if (!empresas.containsKey(cuit)) {
            throw new IllegalArgumentException("La empresa no existe.");
        }
        return empresas.get(cuit);
    }

    Cuenta buscarCuenta(String cvu) {
        if (!cuentasPorCvu.containsKey(cvu)) {
            throw new IllegalArgumentException("La cuenta no existe.");
        }
        return cuentasPorCvu.get(cvu);
    }

    private void validarTexto(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Campo invalido: " + nombreCampo);
        }
    }

    private void validarAliasNuevo(String alias) {
        validarTexto(alias, "alias");
        if (cvuPorAlias.containsKey(alias)) {
            throw new IllegalArgumentException("El alias ya esta registrado.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Billetera{usuarios=").append(usuarios.size());
        sb.append(", empresas=").append(empresas.size());
        sb.append(", cuentas=").append(cuentasPorCvu.size());
        sb.append("}\n");
        for (Usuario u : usuarios.values()) {
            sb.append("  ").append(u).append("\n");
        }
        for (Empresa e : empresas.values()) {
            sb.append("  ").append(e).append("\n");
        }
        for (Cuenta c : cuentasPorCvu.values()) {
            sb.append("  ").append(c).append("\n");
        }
        return sb.toString();
    }
}
