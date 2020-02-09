package com.parfait.study.grpc.server.greeter;

import org.lognet.springboot.grpc.GRpcService;

import com.parfait.study.grpc.GreeterGrpc;
import com.parfait.study.grpc.HelloReply;
import com.parfait.study.grpc.HelloRequest;

import io.grpc.stub.StreamObserver;

@GRpcService
public class Greeter extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
                                     .setMessage("Hello, " + request.getName())
                                     .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
