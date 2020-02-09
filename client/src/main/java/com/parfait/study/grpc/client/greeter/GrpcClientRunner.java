package com.parfait.study.grpc.client.greeter;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.parfait.study.grpc.GreeterGrpc;
import com.parfait.study.grpc.GreeterGrpc.GreeterBlockingStub;
import com.parfait.study.grpc.HelloReply;
import com.parfait.study.grpc.HelloRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GrpcClientRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String name = Optional.ofNullable(args)
                              .map(Stream::of)
                              .flatMap(Stream::findFirst)
                              .orElse("Guest");

        HelloRequest request = HelloRequest.newBuilder()
                                           .setName(name)
                                           .build();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                                                      .usePlaintext()
                                                      .build();

        GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        HelloReply response;
        try {
            response = stub.sayHello(request);
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {0}", e.getStatus());
            return;
        }
        log.info("Greeting: {}", response.getMessage());
    }
}
