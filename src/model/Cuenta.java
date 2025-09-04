package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una cuenta bancaria
 */
public class Cuenta {
    private String numeroCuenta;
    private String clienteId;
    private double saldo;
    private String tipoCuenta; // "AHORROS" o "CORRIENTE"
    private List<Transaccion> transacciones;
    
    // Constructor
    public Cuenta(String numeroCuenta, String clienteId, String tipoCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.clienteId = clienteId;
        this.tipoCuenta = tipoCuenta;
        this.saldo = 0.0;
        this.transacciones = new ArrayList<>();
    }
    
    // Constructor con saldo inicial
    public Cuenta(String numeroCuenta, String clienteId, String tipoCuenta, double saldoInicial) {
        this(numeroCuenta, clienteId, tipoCuenta);
        this.saldo = saldoInicial;
    }
    
    // Getters y Setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    public String getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public String getTipoCuenta() {
        return tipoCuenta;
    }
    
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    
    public List<Transaccion> getTransacciones() {
        return transacciones;
    }
    
    // Métodos para operaciones bancarias
    public boolean depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            return true;
        }
        return false;
    }
    
    public boolean retirar(double monto) {
        if (monto > 0 && this.saldo >= monto) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }
    
    public void agregarTransaccion(Transaccion transaccion) {
        this.transacciones.add(transaccion);
    }
    
    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", clienteId='" + clienteId + '\'' +
                ", saldo=" + saldo +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                '}';
    }
}