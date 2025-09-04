package service;

import model.Cliente;
import model.Cuenta;
import model.Transaccion;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Servicio que maneja todas las operaciones bancarias
 */
public class BancoService {
    private List<Cliente> clientes;
    private List<Cuenta> cuentas;
    private List<Transaccion> transacciones;
    private Random random;
    
    public BancoService() {
        this.clientes = new ArrayList<>();
        this.cuentas = new ArrayList<>();
        this.transacciones = new ArrayList<>();
        this.random = new Random();
    }
    
    // Métodos para clientes
    public boolean registrarCliente(String nombre, String apellido, String cedula, String telefono) {
        // Verificar si ya existe un cliente con esa cédula
        if (buscarClientePorCedula(cedula) != null) {
            return false;
        }
        
        String id = generarIdCliente();
        Cliente cliente = new Cliente(id, nombre, apellido, cedula, telefono);
        clientes.add(cliente);
        return true;
    }
    
    public Cliente buscarClientePorCedula(String cedula) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }
        return null;
    }
    
    public Cliente buscarClientePorId(String id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null;
    }
    
    public List<Cliente> obtenerTodosLosClientes() {
        return new ArrayList<>(clientes);
    }
    
    // Métodos para cuentas
    public String crearCuenta(String clienteId, String tipoCuenta, double saldoInicial) {
        Cliente cliente = buscarClientePorId(clienteId);
        if (cliente == null) {
            return null;
        }
        
        String numeroCuenta = generarNumeroCuenta();
        Cuenta cuenta = new Cuenta(numeroCuenta, clienteId, tipoCuenta, saldoInicial);
        cuentas.add(cuenta);
        
        // Registrar transacción de apertura si hay saldo inicial
        if (saldoInicial > 0) {
            String transaccionId = generarIdTransaccion();
            Transaccion transaccion = new Transaccion(transaccionId, numeroCuenta, "DEPOSITO", saldoInicial, "Saldo inicial de cuenta");
            transacciones.add(transaccion);
            cuenta.agregarTransaccion(transaccion);
        }
        
        return numeroCuenta;
    }
    
    public Cuenta buscarCuenta(String numeroCuenta) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null;
    }
    
    public List<Cuenta> obtenerCuentasPorCliente(String clienteId) {
        List<Cuenta> cuentasCliente = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getClienteId().equals(clienteId)) {
                cuentasCliente.add(cuenta);
            }
        }
        return cuentasCliente;
    }
    
    // Métodos para transacciones
    public boolean depositar(String numeroCuenta, double monto) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (cuenta == null || monto <= 0) {
            return false;
        }
        
        cuenta.depositar(monto);
        
        // Registrar transacción
        String transaccionId = generarIdTransaccion();
        Transaccion transaccion = new Transaccion(transaccionId, numeroCuenta, "DEPOSITO", monto, "Depósito en efectivo");
        transacciones.add(transaccion);
        cuenta.agregarTransaccion(transaccion);
        
        return true;
    }
    
    public boolean retirar(String numeroCuenta, double monto) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (cuenta == null || monto <= 0 || cuenta.getSaldo() < monto) {
            return false;
        }
        
        cuenta.retirar(monto);
        
        // Registrar transacción
        String transaccionId = generarIdTransaccion();
        Transaccion transaccion = new Transaccion(transaccionId, numeroCuenta, "RETIRO", monto, "Retiro en efectivo");
        transacciones.add(transaccion);
        cuenta.agregarTransaccion(transaccion);
        
        return true;
    }
    
    public boolean transferir(String cuentaOrigen, String cuentaDestino, double monto) {
        Cuenta origen = buscarCuenta(cuentaOrigen);
        Cuenta destino = buscarCuenta(cuentaDestino);
        
        if (origen == null || destino == null || monto <= 0 || origen.getSaldo() < monto) {
            return false;
        }
        
        // Realizar transferencia
        origen.retirar(monto);
        destino.depositar(monto);
        
        // Registrar transacciones
        String transaccionId1 = generarIdTransaccion();
        String transaccionId2 = generarIdTransaccion();
        
        Transaccion transaccionOrigen = new Transaccion(transaccionId1, cuentaOrigen, "TRANSFERENCIA", -monto, "Transferencia enviada", cuentaDestino);
        Transaccion transaccionDestino = new Transaccion(transaccionId2, cuentaDestino, "TRANSFERENCIA", monto, "Transferencia recibida", cuentaOrigen);
        
        transacciones.add(transaccionOrigen);
        transacciones.add(transaccionDestino);
        origen.agregarTransaccion(transaccionOrigen);
        destino.agregarTransaccion(transaccionDestino);
        
        return true;
    }
    
    public double consultarSaldo(String numeroCuenta) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        return cuenta != null ? cuenta.getSaldo() : -1;
    }
    
    public List<Transaccion> obtenerHistorialTransacciones(String numeroCuenta) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        return cuenta != null ? cuenta.getTransacciones() : new ArrayList<>();
    }
    
    // Métodos auxiliares para generar IDs
    private String generarIdCliente() {
        return "CLI" + String.format("%06d", random.nextInt(1000000));
    }
    
    private String generarNumeroCuenta() {
        return String.format("%010d", random.nextInt(1000000000));
    }
    
    private String generarIdTransaccion() {
        return "TXN" + System.currentTimeMillis() + random.nextInt(1000);
    }
}