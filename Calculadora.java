import java.math.BigInteger;
import java.util.Scanner;

/**
 * Calculadora que resuelve sumas, restas, multiplicaciones y potencias.
 * Solo acepta números enteros de hasta 5 dígitos: [-99999 ... 99999].
 */
public class Calculadora {

    // Límites permitidos para los números de entrada
    static final int MINIMO = -99999;
    static final int MAXIMO = 99999;

    /**
     * Comprueba que un número está dentro del rango permitido.
     * @param numero número a comprobar
     * @return true si es válido, false si no
     */
    public static boolean esValido(long numero) {
        return numero >= MINIMO && numero <= MAXIMO;
    }

    /** Devuelve la suma de a y b. */
    public static long sumar(int a, int b) {
        return (long) a + b;
    }

    /** Devuelve la resta de a menos b. */
    public static long restar(int a, int b) {
        return (long) a - b;
    }

    /** Devuelve la multiplicación de a por b. */
    public static long multiplicar(int a, int b) {
        return (long) a * b;
    }

    /**
     * Devuelve base elevado a exponente.
     * Se usa BigInteger porque el resultado puede ser enorme.
     * El exponente no puede ser negativo (el resultado no sería entero).
     */
    public static BigInteger potencia(int base, int exponente) {
        if (exponente < 0) {
            throw new IllegalArgumentException("El exponente no puede ser negativo");
        }
        return BigInteger.valueOf(base).pow(exponente);
    }

    /** Pide un número por teclado hasta que sea un entero válido. */
    public static int leerNumero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            try {
                long numero = Long.parseLong(texto);
                if (esValido(numero)) {
                    return (int) numero;
                }
                System.out.println("Error: el número debe estar entre -99999 y 99999.");
            } catch (NumberFormatException e) {
                System.out.println("Error: introduce un número entero.");
            }
        }
    }

    /** Programa principal: muestra un menú y realiza la operación elegida. */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== CALCULADORA ===");
        System.out.println("1. Suma");
        System.out.println("2. Resta");
        System.out.println("3. Multiplicación");
        System.out.println("4. Potencia");
        System.out.print("Elige una opción: ");
        String opcion = sc.nextLine().trim();

        int a = leerNumero(sc, "Primer número: ");
        int b = leerNumero(sc, "Segundo número: ");

        switch (opcion) {
            case "1": System.out.println("Resultado: " + sumar(a, b)); break;
            case "2": System.out.println("Resultado: " + restar(a, b)); break;
            case "3": System.out.println("Resultado: " + multiplicar(a, b)); break;
            case "4":
                try {
                    System.out.println("Resultado: " + potencia(a, b));
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            default: System.out.println("Opción no válida.");
        }
        sc.close();
    }
}