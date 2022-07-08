package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.BoundsMessage;
import ru.otus.protobuf.generated.NumberMessage;
import ru.otus.protobuf.generated.RemoteNumberGeneratorGrpc;

public class RemoteNumberGeneratorImpl extends RemoteNumberGeneratorGrpc.RemoteNumberGeneratorImplBase {

    @Override
    public void getSeries(BoundsMessage request, StreamObserver<NumberMessage> responseObserver) {

        for (int current = request.getFirstValue() + 1; current <= request.getLastValue(); current++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(number2NumberMessage(current));
        }
        responseObserver.onCompleted();
    }

    private NumberMessage number2NumberMessage(int number) {
        return NumberMessage.newBuilder()
                .setValue(number)
                .build();
    }
}
