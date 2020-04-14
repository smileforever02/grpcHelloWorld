import com.john.grpc.GreeterGrpc;
import com.john.grpc.HelloReply;
import com.john.grpc.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class HelloWorldClient {

    ManagedChannel channel;
    GreeterGrpc.GreeterBlockingStub blockingStub;

    public HelloWorldClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext().build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).setSex("Female").build();
        HelloReply response = blockingStub.sayHello(request);
        System.out.println(response.getMessage());
    }

    public static void main(String[] args) {
        HelloWorldClient client = new HelloWorldClient("127.0.0.1", 50051);
        for (int i = 0; i < 5; i++) {
            client.greet("john" + i);
        }

    }
}
