package com.microsoft.azure.eventhubs.samples.eventprocessorsample;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.azure.messaging.eventhubs.models.PartitionContext;
import reactor.core.Disposable;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Sample demonstrates how to receive events from an Azure Event Hub instance.
 */
public class EventHubConsumer {

    private static final Duration OPERATION_TIMEOUT = Duration.ofSeconds(30);
    private static final int NUMBER_OF_EVENTS = 10;
    private static final String CONNECTION_STRING = "Endpoint=sb://c3m-event-hub.servicebus.windows.net/;SharedAccessKeyName=PreviewDataPolicy;SharedAccessKey=j5RjZEUu8GCzmMCahOiWTLepTYdbkQ/IHs5Uz9XTYOQ=;EntityPath=maney-event-hub";


    public static void main(String[] args) throws InterruptedException {


        /*String domain = "37cca1be-d0e3-42c7-ae26-908535a0ed21";
        String secretid = "OKO~Te07DYvwr8B_O4_PX694cq~2t_yrcf";
        String fqdn = "C3M-Event-Hub.servicebus.windows.net";
        String eventHubName = "C3M-Event-Hub";

        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(domain)
                .clientSecret(secretid)
                .tenantId("6e31e5da-0ac2-47ce-8189-c84c41fa12fe")
                .build();*/

        EventHubConsumerAsyncClient consumer = new EventHubClientBuilder()
//                .credential(fqdn, eventHubName, clientSecretCredential)
                .connectionString(CONNECTION_STRING)
                .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                .buildAsyncConsumerClient();

        CountDownLatch countDownLatch = new CountDownLatch(NUMBER_OF_EVENTS);

        Disposable subscription = consumer.receive()
                .subscribe(partitionEvent -> {
                            EventData event = partitionEvent.getData();
                            PartitionContext partitionContext = partitionEvent.getPartitionContext();
                            String contents = new String(event.getBody(), UTF_8);
                            System.out.printf("[#%s] Partition id: %s. Sequence Number: %s. Contents: '%s'%n", countDownLatch.getCount(), partitionContext.getPartitionId(), event.getSequenceNumber(), contents);
                            countDownLatch.countDown();
                        },
                        error -> {
                            System.err.println("Error occurred while consuming events: " + error);
                            while (countDownLatch.getCount() > 0) {
                                countDownLatch.countDown();
                            }
                        }, () -> {
                            System.out.println("Finished reading events.");
                        });

        try {
            boolean isSuccessful = countDownLatch.await(OPERATION_TIMEOUT.getSeconds(), TimeUnit.SECONDS);
            if (!isSuccessful) {
                System.err.printf("Did not complete successfully. There are: %s events left.%n", countDownLatch.getCount());
            }
        } finally {
            subscription.dispose();
            consumer.close();
        }
    }
}