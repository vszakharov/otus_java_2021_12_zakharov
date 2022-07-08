package ru.otus.protobuf;


import java.io.IOException;

import io.grpc.ServerBuilder;
import ru.otus.protobuf.service.RemoteNumberGeneratorImpl;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var remoteNumberGenerator = new RemoteNumberGeneratorImpl();

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteNumberGenerator).build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
