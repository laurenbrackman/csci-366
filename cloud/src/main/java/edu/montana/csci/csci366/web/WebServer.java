package edu.montana.csci.csci366.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;

public class WebServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class);

    private final int _port;
    public WebServer(int port) {
        _port = port;
    }

    private void run() throws IOException {
        ServerSocket socket = new ServerSocket(_port);
        while (true) {
            Socket connection = socket.accept();
            handleConnection(connection);
        }
    }

    private void handleConnection(Socket connection) throws IOException {
        try (
                connection;
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = connection.getOutputStream();
                PrintWriter outputWriter = new PrintWriter(outputStream);
                InputStreamReader in = new InputStreamReader(inputStream);
                BufferedReader inputReader = new BufferedReader(in);
        ) {
            String firstLine = inputReader.readLine();
            LOGGER.info(firstLine);
            StringBuilder rest = new StringBuilder();
            String line = null;
            do {
                line = inputReader.readLine();
                rest.append(line);
                LOGGER.info(firstLine);
            } while (line.isEmpty());

            outputWriter.println(index());
            outputWriter.println("\n\n");
            outputWriter.flush();
        }
    }

    public String index() {
        return responseHeader("200 OK") +
                "<html><body><h1>Welcome to the MSU Webserver!</h1><form action='/' method='post'>" +
                "<input name='input1'/><button>Submit</button></form></body></html>";
    }

    private String responseHeader(String responseVals) {
        return "HTTP/1.1 "+ responseVals + "\n" +
                "MSU WebServer : 1.0\n" +
                "Date : " + new Date() + "\n" +
                "\n";
    }

    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer(8000);
        server.run();
    }
}
