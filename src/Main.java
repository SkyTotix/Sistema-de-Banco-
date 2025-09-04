import model.Cliente;
import model.Cuenta;
import model.Transaccion;
import service.BancoService;
import util.InputUtil;
import java.util.List;

/**
 * Sistema Bancario Simple - Aplicación de Consola
 */
public class Main {
    private static BancoService bancoService = new BancoService();
    
    public static void main(String[] args) {
        InputUtil.mostrarEncabezado("SISTEMA BANCARIO SIMPLE");
        System.out.println("¡Bienvenido al Sistema Bancario!");
        
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = InputUtil.leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    crearCuenta();
                    break;
                case 3:
                    realizarDeposito();
                    break;
                case 4:
                    realizarRetiro();
                    break;
                case 5:
                    realizarTransferencia();
                    break;
                case 6:
                    consultarSaldo();
                    break;
                case 7:
                    verHistorialTransacciones();
                    break;
                case 8:
                    listarClientes();
                    break;
                case 9:
                    salir = true;
                    System.out.println("\n¡Gracias por usar el Sistema Bancario!");
                    break;
                default:
                    InputUtil.mostrarError("Opción no válida. Intente nuevamente.");
            }
            
            if (!salir) {
                InputUtil.pausar();
            }
        }
    }
    
    private static void mostrarMenuPrincipal() {
        InputUtil.limpiarPantalla();
        InputUtil.mostrarEncabezado("MENÚ PRINCIPAL");
        System.out.println("1. Registrar Cliente");
        System.out.println("2. Crear Cuenta");
        System.out.println("3. Realizar Depósito");
        System.out.println("4. Realizar Retiro");
        System.out.println("5. Realizar Transferencia");
        System.out.println("6. Consultar Saldo");
        System.out.println("7. Ver Historial de Transacciones");
        System.out.println("8. Listar Clientes");
        System.out.println("9. Salir");
        InputUtil.mostrarSeparador();
    }
    
    private static void registrarCliente() {
        InputUtil.mostrarEncabezado("REGISTRAR CLIENTE");
        
        String nombre = InputUtil.leerTextoNoVacio("Ingrese el nombre: ");
        String apellido = InputUtil.leerTextoNoVacio("Ingrese el apellido: ");
        String cedula = InputUtil.leerTextoNoVacio("Ingrese la cédula: ");
        String telefono = InputUtil.leerTextoNoVacio("Ingrese el teléfono: ");
        
        if (bancoService.registrarCliente(nombre, apellido, cedula, telefono)) {
            InputUtil.mostrarExito("Cliente registrado exitosamente.");
        } else {
            InputUtil.mostrarError("Ya existe un cliente con esa cédula.");
        }
    }
    
    private static void crearCuenta() {
        InputUtil.mostrarEncabezado("CREAR CUENTA");
        
        String cedula = InputUtil.leerTextoNoVacio("Ingrese la cédula del cliente: ");
        Cliente cliente = bancoService.buscarClientePorCedula(cedula);
        
        if (cliente == null) {
            InputUtil.mostrarError("Cliente no encontrado.");
            return;
        }
        
        System.out.println("Cliente encontrado: " + cliente.getNombreCompleto());
        System.out.println("\nTipos de cuenta disponibles:");
        System.out.println("1. AHORROS");
        System.out.println("2. CORRIENTE");
        
        int tipoOpcion = InputUtil.leerEntero("Seleccione el tipo de cuenta (1-2): ");
        String tipoCuenta = (tipoOpcion == 1) ? "AHORROS" : "CORRIENTE";
        
        double saldoInicial = InputUtil.leerDecimal("Ingrese el saldo inicial: $");
        
        String numeroCuenta = bancoService.crearCuenta(cliente.getId(), tipoCuenta, saldoInicial);
        
        if (numeroCuenta != null) {
            InputUtil.mostrarExito("Cuenta creada exitosamente.");
            System.out.println("Número de cuenta: " + numeroCuenta);
        } else {
            InputUtil.mostrarError("No se pudo crear la cuenta.");
        }
    }
    
    private static void realizarDeposito() {
        InputUtil.mostrarEncabezado("REALIZAR DEPÓSITO");
        
        String numeroCuenta = InputUtil.leerTextoNoVacio("Ingrese el número de cuenta: ");
        Cuenta cuenta = bancoService.buscarCuenta(numeroCuenta);
        
        if (cuenta == null) {
            InputUtil.mostrarError("Cuenta no encontrada.");
            return;
        }
        
        System.out.println("Saldo actual: $" + cuenta.getSaldo());
        double monto = InputUtil.leerDecimal("Ingrese el monto a depositar: $");
        
        if (bancoService.depositar(numeroCuenta, monto)) {
            InputUtil.mostrarExito("Depósito realizado exitosamente.");
            System.out.println("Nuevo saldo: $" + cuenta.getSaldo());
        } else {
            InputUtil.mostrarError("No se pudo realizar el depósito.");
        }
    }
    
    private static void realizarRetiro() {
        InputUtil.mostrarEncabezado("REALIZAR RETIRO");
        
        String numeroCuenta = InputUtil.leerTextoNoVacio("Ingrese el número de cuenta: ");
        Cuenta cuenta = bancoService.buscarCuenta(numeroCuenta);
        
        if (cuenta == null) {
            InputUtil.mostrarError("Cuenta no encontrada.");
            return;
        }
        
        System.out.println("Saldo actual: $" + cuenta.getSaldo());
        double monto = InputUtil.leerDecimal("Ingrese el monto a retirar: $");
        
        if (bancoService.retirar(numeroCuenta, monto)) {
            InputUtil.mostrarExito("Retiro realizado exitosamente.");
            System.out.println("Nuevo saldo: $" + cuenta.getSaldo());
        } else {
            InputUtil.mostrarError("No se pudo realizar el retiro. Verifique el saldo disponible.");
        }
    }
    
    private static void realizarTransferencia() {
        InputUtil.mostrarEncabezado("REALIZAR TRANSFERENCIA");
        
        String cuentaOrigen = InputUtil.leerTextoNoVacio("Ingrese la cuenta origen: ");
        Cuenta origen = bancoService.buscarCuenta(cuentaOrigen);
        
        if (origen == null) {
            InputUtil.mostrarError("Cuenta origen no encontrada.");
            return;
        }
        
        String cuentaDestino = InputUtil.leerTextoNoVacio("Ingrese la cuenta destino: ");
        Cuenta destino = bancoService.buscarCuenta(cuentaDestino);
        
        if (destino == null) {
            InputUtil.mostrarError("Cuenta destino no encontrada.");
            return;
        }
        
        if (cuentaOrigen.equals(cuentaDestino)) {
            InputUtil.mostrarError("No se puede transferir a la misma cuenta.");
            return;
        }
        
        System.out.println("\n=== INFORMACIÓN DE CUENTAS ===");
        System.out.println("Cuenta origen (" + cuentaOrigen + "): $" + origen.getSaldo());
        System.out.println("Cuenta destino (" + cuentaDestino + "): $" + destino.getSaldo());
        
        double monto = InputUtil.leerDecimal("\nIngrese el monto a transferir: $");
        
        if (bancoService.transferir(cuentaOrigen, cuentaDestino, monto)) {
            InputUtil.mostrarExito("Transferencia realizada exitosamente.");
            
            // Actualizar las referencias de las cuentas para mostrar saldos actualizados
            origen = bancoService.buscarCuenta(cuentaOrigen);
            destino = bancoService.buscarCuenta(cuentaDestino);
            
            System.out.println("\n=== SALDOS ACTUALIZADOS ===");
            System.out.println("Cuenta origen (" + cuentaOrigen + "): $" + origen.getSaldo());
            System.out.println("Cuenta destino (" + cuentaDestino + "): $" + destino.getSaldo());
        } else {
            InputUtil.mostrarError("No se pudo realizar la transferencia. Verifique el saldo disponible.");
        }
    }
    
    private static void consultarSaldo() {
        InputUtil.mostrarEncabezado("CONSULTAR SALDO");
        
        String numeroCuenta = InputUtil.leerTextoNoVacio("Ingrese el número de cuenta: ");
        double saldo = bancoService.consultarSaldo(numeroCuenta);
        
        if (saldo >= 0) {
            System.out.println("\nSaldo actual: $" + saldo);
        } else {
            InputUtil.mostrarError("Cuenta no encontrada.");
        }
    }
    
    private static void verHistorialTransacciones() {
        InputUtil.mostrarEncabezado("HISTORIAL DE TRANSACCIONES");
        
        String numeroCuenta = InputUtil.leerTextoNoVacio("Ingrese el número de cuenta: ");
        List<Transaccion> transacciones = bancoService.obtenerHistorialTransacciones(numeroCuenta);
        
        if (transacciones.isEmpty()) {
            InputUtil.mostrarInfo("No hay transacciones para esta cuenta.");
        } else {
            System.out.println("\nHistorial de transacciones:");
            System.out.println("-".repeat(80));
            for (Transaccion t : transacciones) {
                System.out.printf("%-15s %-12s $%-10.2f %s\n", 
                    t.getFechaFormateada(), t.getTipo(), t.getMonto(), t.getDescripcion());
            }
        }
    }
    
    private static void listarClientes() {
        InputUtil.mostrarEncabezado("LISTA DE CLIENTES");
        
        List<Cliente> clientes = bancoService.obtenerTodosLosClientes();
        
        if (clientes.isEmpty()) {
            InputUtil.mostrarInfo("No hay clientes registrados.");
        } else {
            System.out.println("\nClientes registrados:");
            System.out.println("-".repeat(60));
            for (Cliente cliente : clientes) {
                System.out.printf("%-15s %-20s %-15s\n", 
                    cliente.getCedula(), cliente.getNombreCompleto(), cliente.getTelefono());
            }
        }
    }


    
}