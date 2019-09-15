package servidorbidireccional;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorBidireccional {

    public static void main(String[] args) {

        ServerSocket socketServidor = null;
        Socket socket = null;
        BufferedReader lector = null;
        PrintWriter escritor = null;
        String entrada = "";
        String salida;
        Scanner scanner = new Scanner(System.in);

        if (args.length == 1) {

            if (Metodos.Numerico(args[0])) {

                try {
                    socketServidor = new ServerSocket(Integer.parseInt(args[0]));
                } catch (IOException e) {
                    System.out.println("Error al crear el socket " + e);
                    System.exit(1);
                }
                System.out.println("Esperando Conexion con el cliente");

                try {
                    socket = socketServidor.accept();
                } catch (IOException e) {
                    System.out.println("Error al aceptar conexión " + e);
                    System.exit(2);
                }

                try {
                    lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (Exception e) {
                    System.out.println("Error al leer  " + e);
                    System.exit(3);
                }

                try {
                    escritor = new PrintWriter(socket.getOutputStream(), true);
                } catch (IOException e) {
                    System.out.println("Error al escribir " + e);
                    System.exit(4);
                }

                do {
                    try {
                        entrada = lector.readLine();
                    } catch (IOException e) {
                        System.out.println("Error al leer la linea de entrada " + e);
                        System.exit(5);
                    }

                    System.out.println(entrada);
                    if (entrada.equalsIgnoreCase("fin")) {
                        System.out.println("Conexión perdida");
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("Error al cerrar el  cliente " + e);
                            System.exit(6);
                        }
                        try {
                            socketServidor.close();
                        } catch (Exception e) {
                            System.out.println("Error al cerrar el  servidor " + e);
                            System.exit(7);
                        }
                        System.exit(0);
                    }
                    salida = scanner.nextLine();
                    escritor.println(salida);
                } while (!entrada.equalsIgnoreCase("fin"));

            } else {
                System.out.println("!!Error!!. El puerto debe ser un número");
            }

        } else {
            System.out.println("Ingrese un puerto válido");
        }
    }

}
