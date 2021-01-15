package com.microsoft.azure.eventhubs.samples.eventprocessorsample;

import com.azure.core.util.IterableStream;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.messaging.eventhubs.*;
import com.azure.messaging.eventhubs.models.*;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Sample demonstrates how to receive events from an Azure Event Hub instance.
 */
public class POCEventHubConsumer {

    private final String CONNECTION_STRING = "Endpoint=sb://c3m-event-hub.servicebus.windows.net/;SharedAccessKeyName=PreviewDataPolicy;SharedAccessKey=j5RjZEUu8GCzmMCahOiWTLepTYdbkQ/IHs5Uz9XTYOQ=;EntityPath=maney-event-hub";

    public final Consumer<PartitionEvent> PARTITION_PROCESSOR = partitionEvent -> {
        EventData event = partitionEvent.getData();
        PartitionContext partitionContext = partitionEvent.getPartitionContext();
        String contents = new String(event.getBody(), UTF_8);
        System.out.printf("Partition id: %s. Sequence Number: %s. Contents: '%s'%n", partitionContext.getPartitionId(), event.getSequenceNumber(), contents);
    };

    public final Consumer<Throwable> ERROR_HANDLER = error -> {
        System.err.println("Error occurred while consuming events: " + error);
    };

    public static void main(String[] args) throws InterruptedException {
        POCEventHubConsumer pocEventHubConsumer = new POCEventHubConsumer();
        //pocEventHubConsumer.process();
        pocEventHubConsumer.processUsingProcessorClient();
    }

    private void process(){
        Disposable subscription = null;
        EventHubConsumerAsyncClient consumer = null;
        try {
            consumer = new EventHubClientBuilder()
                                .connectionString(CONNECTION_STRING)
                                    .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                                        .buildAsyncConsumerClient();


            subscription = consumer.receive().subscribe(PARTITION_PROCESSOR, ERROR_HANDLER , () -> {
                System.out.println("Finished reading events.");
            });
            boolean run = true;
            while(run){

            }
            System.out.println("Press enter to stop.");
            System.in.read();


        } catch(Exception ex){
            ex.printStackTrace();
        } finally {
            subscription.dispose();
            consumer.close();
        }

    }

    private void processUsingProcessorClient(){
        try {

            /*String domain = "37cca1be-d0e3-42c7-ae26-908535a0ed21";
            String secretid = "OKO~Te07DYvwr8B_O4_PX694cq~2t_yrcf";
            String fqdn = "C3M-Event-Hub.servicebus.windows.net";
            String eventHubName = "C3M-Event-Hub";

            ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                    .clientId(domain)
                    .clientSecret(secretid)
                    .tenantId("6e31e5da-0ac2-47ce-8189-c84c41fa12fe")
                    .build();*/

            EventHubConsumerAsyncClient eventHubConsumerAsyncClient = new EventHubClientBuilder()
                    .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                    .connectionString("Endpoint=sb://c3m-event-hub.servicebus.windows.net/;SharedAccessKeyName=PreviewDataPolicy;SharedAccessKey=j5RjZEUu8GCzmMCahOiWTLepTYdbkQ/IHs5Uz9XTYOQ=;EntityPath=maney-event-hub", "maney-event-hub")
                    .buildAsyncConsumerClient();

            List<String> block = eventHubConsumerAsyncClient.getPartitionIds().collectList().block();
            // Get the total partitions in the hub.
            Iterator<String> iterator = block.stream().iterator();
            String partitionID = null;
            while(iterator.hasNext()){
                partitionID = iterator.next();
                eventHubConsumerAsyncClient.receiveFromPartition(partitionID,EventPosition.fromOffset(0)).subscribe(partitionEvent -> {
                EventData event = partitionEvent.getData();
                PartitionContext partitionContext = partitionEvent.getPartitionContext();
                String contents = new String(event.getBody(), UTF_8);
                System.out.printf("Partition id: %s. Sequence Number: %s. Contents: '%s'%n", partitionContext.getPartitionId(), event.getSequenceNumber(), contents);
                });
            }

            /*eventHubConsumerAsyncClient.receiveFromPartition("0",EventPosition.fromOffset(1)).subscribe(partitionEvent -> {
                EventData event = partitionEvent.getData();
                PartitionContext partitionContext = partitionEvent.getPartitionContext();
                String contents = new String(event.getBody(), UTF_8);
                System.out.printf("Partition id: %s. Sequence Number: %s. Contents: '%s'%n", partitionContext.getPartitionId(), event.getSequenceNumber(), contents);
            });*/


            /*eventHubConsumerAsyncClient.receive().subscribe(partitionEvent -> {
                        EventData event = partitionEvent.getData();
                        PartitionContext partitionContext = partitionEvent.getPartitionContext();
                        String contents = new String(event.getBody(), UTF_8);
                        System.out.printf("Partition id: %s. Sequence Number: %s. Contents: '%s'%n", partitionContext.getPartitionId(), event.getSequenceNumber(), contents);
                    });*/


            /*IterableStream<PartitionEvent> partitionEvents = eventHubConsumerClient.receiveFromPartition("0", 10, EventPosition.fromOffset(0));
            Iterator<PartitionEvent> iterator = partitionEvents.iterator();
            while(iterator.hasNext()){
                System.out.println("====> " + iterator.next().getData().getBodyAsString());
            }
            System.out.println(partitionEvents.stream().findAny().get().getData().getBodyAsString());*/

            System.in.read();


            /*EventProcessorClient eventProcessorClient = new EventProcessorClientBuilder()
                    .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                    .connectionString(CONNECTION_STRING)
                    .credential(fqdn, eventHubName,clientSecretCredential)
                    .checkpointStore(new C3MCheckPointStore())
                    .processEvent(eventContext -> {
                        System.out.println("Partition id = " + eventContext.getPartitionContext().getPartitionId() + " and "
                                + "sequence number of event = " + eventContext.getEventData().getSequenceNumber());
                    })
                    .processError(errorContext -> {
                        System.out
                                .println("Error occurred while processing events " + errorContext.getThrowable().getMessage());
                    })
                    .buildEventProcessorClient();

            eventProcessorClient.start();
            TimeUnit.SECONDS.sleep(10);
            eventProcessorClient.stop();*/
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}