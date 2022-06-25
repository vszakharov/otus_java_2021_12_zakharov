package ru.otus.servlet;

import java.io.IOException;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;


public class ClientsApiServlet extends HttpServlet {

    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientsApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        var client = gson.fromJson(body, Client.class);
        dbServiceClient.saveClient(client);
    }

}
