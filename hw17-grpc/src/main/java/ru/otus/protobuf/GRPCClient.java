package ru.otus.protobuf;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.BoundsMessage;
import ru.otus.protobuf.generated.NumberMessage;
import ru.otus.protobuf.generated.RemoteNumberGeneratorGrpc;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int START = 0;
    private static final int END = 30;
    private static final AtomicInteger number = new AtomicInteger(0);
    private static long currentValue = 0;

    public static void main(String[] args) throws InterruptedException {

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var newStub = RemoteNumberGeneratorGrpc.newStub(channel);
        var boundsMessage = BoundsMessage.newBuilder()
                .setFirstValue(START)
                .setLastValue(END)
                .build();
        newStub.getSeries(boundsMessage, new StreamObserver<NumberMessage>() {
            @Override
            public void onNext(NumberMessage numberMessage) {
                System.out.printf("new value: %d%n", numberMessage.getValue());
                number.set(numberMessage.getValue());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {

            }
        });

        for (int i = 0; i <= 50; i++) {
            Thread.sleep(1000);
            currentValue += number.getAndSet(0) + 1;
            System.out.printf("currentValue: %d%n", currentValue);
        }

        channel.shutdown();
    }
}
