package util;

import java.util.Scanner;

/**
 * Clase de utilidad para manejar entradas del usuario
 */
public class InputUtil {
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Lee una línea de texto del usuario
     */
    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
    
    /**
     * Lee un número entero del usuario
     */
    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }
    
    /**
     * Lee un número decimal del usuario
     */
    public static double leerDecimal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine().trim();
                double valor = Double.parseDouble(input);
                if (valor < 0) {
                    System.out.println("El valor no puede ser negativo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número decimal válido.");
            }
        }
    }
    
    /**
     * Lee un texto no vacío del usuario
     */
    public static String leerTextoNoVacio(String mensaje) {
        String texto;
        do {
            texto = leerTexto(mensaje);
            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }
    
    /**
     * Pausa la ejecución hasta que el usuario presione Enter
     */
    public static void pausar() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Limpia la pantalla (simulado con líneas en blanco)
     */
    public static void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    /**
     * Muestra un mensaje de error
     */
    public static void mostrarError(String mensaje) {
        System.out.println("\nERROR: " + mensaje);
    }
    
    /**
     * Muestra un mensaje de éxito
     */
    public static void mostrarExito(String mensaje) {
        System.out.println("\nEXITO: " + mensaje);
    }
    
    /**
     * Muestra un mensaje de información
     */
    public static void mostrarInfo(String mensaje) {
        System.out.println("\nINFO: " + mensaje);
    }
    
    /**
     * Muestra una línea separadora
     */
    public static void mostrarSeparador() {
        System.out.println("\n" + "=".repeat(50));
    }
    
    /**
     * Muestra un encabezado con formato
     */
    public static void mostrarEncabezado(String titulo) {
        mostrarSeparador();
        System.out.println("\t\t" + titulo.toUpperCase());
        mostrarSeparador();
    }
}