import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Servidor de la calculadora.
 * Sirve index.html y resuelve sumas, restas, multiplicaciones y potencias.
 * Solo acepta números enteros de hasta 5 dígitos: [-99999 ... 99999].
 */
public class Calculadora {

    // Límites permitidos para los números de entrada
    private static final int MINIMO = -99999;
    private static final int MAXIMO = 99999;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // 1. Ruta principal: sirve index.html
        server.createContext("/", exchange -> {
            if ("/".equals(exchange.getRequestURI().getPath())) {
                servirArchivo(exchange, "index.html");
            } else {
                try (exchange) {
                    exchange.sendResponseHeaders(404, -1);
                }
            }
        });

        // 2. Ruta /calcular: recibe el formulario y devuelve el resultado
        server.createContext("/calcular", exchange -> {
            try (exchange) {
                if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    String cuerpo = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    Map<String, String> datos = parsearFormulario(cuerpo);

                    String numero1 = datos.getOrDefault("numero1", "");
                    String numero2 = datos.getOrDefault("numero2", "");
                    String operacion = datos.getOrDefault("operacion", "");

                    System.out.println("Operación recibida -> " + numero1 + " " + operacion + " " + numero2);

                    String resultado = calcular(numero1, numero2, operacion);
                    enviarTexto(exchange, resultado);
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Servidor iniciado en http://localhost:8080");
    }

    /**
     * Valida los números y realiza la operación pedida.
     * @return el resultado o un mensaje de error
     */
    private static String calcular(String texto1, String texto2, String operacion) {
        if (!esValido(texto1) || !esValido(texto2)) {
            return "Error: introduce enteros entre -99999 y 99999.";
        }
        int a = Integer.parseInt(texto1.trim());
        int b = Integer.parseInt(texto2.trim());

        switch (operacion) {
            case "suma":           return "Resultado: " + sumar(a, b);
            case "resta":          return "Resultado: " + restar(a, b);
            case "multiplicacion": return "Resultado: " + multiplicar(a, b);
            case "potencia":
                if (b < 0) {
                    return "Error: el exponente no puede ser negativo.";
                }
                return "Resultado: " + potencia(a, b);
            default:
                return "Error: operación no válida.";
        }
    }

    /** Comprueba que el texto es un entero dentro del rango permitido. */
    private static boolean esValido(String texto) {
        try {
            long numero = Long.parseLong(texto.trim());
            return numero >= MINIMO && numero <= MAXIMO;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Devuelve la suma de a y b. */
    private static long sumar(int a, int b) {
        return (long) a + b;
    }

    /** Devuelve la resta de a menos b. */
    private static long restar(int a, int b) {
        return (long) a - b;
    }

    /** Devuelve la multiplicación de a por b. */
    private static long multiplicar(int a, int b) {
        return (long) a * b;
    }

    /** Devuelve base elevado a exponente (BigInteger porque puede ser enorme). */
    private static BigInteger potencia(int base, int exponente) {
        return BigInteger.valueOf(base).pow(exponente);
    }

    // Método auxiliar para enviar texto como respuesta
    private static void enviarTexto(HttpExchange exchange, String texto) throws IOException {
        byte[] contenido = texto.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(200, contenido.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(contenido);
        }
    }

    // Método auxiliar para enviar archivos HTML
    private static void servirArchivo(HttpExchange exchange, String nombreArchivo) throws IOException {
        try (exchange) {
            Path archivo = Paths.get(nombreArchivo);
            if (Files.exists(archivo)) {
                byte[] contenido = Files.readAllBytes(archivo);
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, contenido.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(contenido);
                }
            } else {
                byte[] error = ("404 - No se encontró " + nombreArchivo).getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(404, error.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(error);
                }
            }
        }
    }

    // Convierte "numero1=2&numero2=3&operacion=suma" en un Map<String, String> decodificado
    private static Map<String, String> parsearFormulario(String cuerpo) {
        Map<String, String> parametros = new HashMap<>();
        if (cuerpo == null || cuerpo.isEmpty()) return parametros;

        for (String par : cuerpo.split("&")) {
            String[] kv = par.split("=", 2);
            String clave = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String valor = kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "";
            parametros.put(clave, valor);
        }
        return parametros;
    }
}