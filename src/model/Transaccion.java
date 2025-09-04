package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una transacción bancaria
 */
public class Transaccion {
    private String id;
    private String numeroCuenta;
    private String tipo; // "DEPOSITO", "RETIRO", "TRANSFERENCIA"
    private double monto;
    private LocalDateTime fecha;
    private String descripcion;
    private String cuentaDestino; // Para transferencias
    
    // Constructor
    public Transaccion(String id, String numeroCuenta, String tipo, double monto, String descripcion) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
    }
    
    // Constructor para transferencias
    public Transaccion(String id, String numeroCuenta, String tipo, double monto, String descripcion, String cuentaDestino) {
        this(id, numeroCuenta, tipo, monto, descripcion);
        this.cuentaDestino = cuentaDestino;
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getCuentaDestino() {
        return cuentaDestino;
    }
    
    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }
    
    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fecha.format(formatter);
    }
    
    @Override
    public String toString() {
        return "Transaccion{" +
                "id='" + id + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", fecha=" + getFechaFormateada() +
                ", descripcion='" + descripcion + '\'' +
                ", cuentaDestino='" + cuentaDestino + '\'' +
                '}';
    }
}