package com.microsoft.azure.eventhubs.maney;

import com.azure.core.util.IterableStream;
import com.azure.messaging.eventhubs.*;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.messaging.eventhubs.models.PartitionEvent;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.samples.eventprocessorsample.C3MCheckPointStore;
import com.microsoft.azure.eventhubs.samples.eventprocessorsample.EventHubConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class Receiver {

    private static final String EH_NAMESPACE_CONNECTION_STRING = "<EVENT HUBS NAMESPACE CONNECTION STRING>";
    private static final String eventHubName = "<EVENT HUB NAME>";
    private static final String STORAGE_CONNECTION_STRING = "<AZURE STORAGE CONNECTION STRING>";
    private static final String STORAGE_CONTAINER_NAME = "<AZURE STORAGE CONTAINER NAME>";

    private static final String CONNECTION_STRING = "Endpoint=sb://c3m-event-hub.servicebus.windows.net/;SharedAccessKeyName=PreviewDataPolicy;SharedAccessKey=j5RjZEUu8GCzmMCahOiWTLepTYdbkQ/IHs5Uz9XTYOQ=;EntityPath=maney-event-hub";
    private static final String EVENT_HUB_NAME = "maney-event-hub";

    public static final Consumer<EventContext> PARTITION_PROCESSOR = eventContext -> {
        if(eventContext.getEventData() != null ){
            System.out.printf(new Date().toGMTString() + "Processing event from partition %s with sequence number %d with body: %s %n",
                    eventContext.getPartitionContext().getPartitionId(), eventContext.getEventData().getSequenceNumber(), eventContext.getEventData().getBodyAsString());

            if (eventContext.getEventData().getSequenceNumber() % 10 == 0) {
                eventContext.updateCheckpoint();
            }
        }
    };

    public static final Consumer<ErrorContext> ERROR_HANDLER = errorContext -> {
        System.out.printf("Error occurred in partition processor for partition %s, %s.%n",
            errorContext.getPartitionContext().getPartitionId(),
            errorContext.getThrowable());
    };

    public static void main(String[] args) throws Exception {

        /*StringBuilder jassConfigBuilder = new StringBuilder("org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=");
        jassConfigBuilder.append('"');
        jassConfigBuilder.append("maney");
        jassConfigBuilder.append('"');
        System.out.println(jassConfigBuilder);*/


/*        BlobContainerAsyncClient blobContainerAsyncClient = new BlobContainerClientBuilder()
            .connectionString(STORAGE_CONNECTION_STRING)
            .containerName(STORAGE_CONTAINER_NAME)
            .buildAsyncClient();*/

       /* ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EventHubConsumerAsyncClient eventHubConsumerClient = new EventHubClientBuilder()
                            .connectionString(CONNECTION_STRING, EVENT_HUB_NAME)
                            .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                            .buildAsyncConsumerClient();


                    List<String> block = eventHubConsumerClient.getPartitionIds().collectList().block();
                    Iterator<String> iterator = block.iterator();
                    String partitionID = null;
                    while(iterator.hasNext()){
                        partitionID = iterator.next();
                        System.out.println("*****" + partitionID + "*****" );
                        eventHubConsumerClient.receiveFromPartition(partitionID, EventPosition.fromOffset(0)).subscribe(events -> {
                            System.out.println(events.getData().getBodyAsString());
                        });
                    }
                    System.in.read();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });*/



        EventProcessorClientBuilder eventProcessorClientBuilder = new EventProcessorClientBuilder()
            .connectionString(CONNECTION_STRING,EVENT_HUB_NAME)
            .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
            .processEvent(PARTITION_PROCESSOR, Duration.ofMillis(10000))
            .processError(ERROR_HANDLER)
            .checkpointStore(new C3MCheckPointStore());

        EventProcessorClient eventProcessorClient = eventProcessorClientBuilder.buildEventProcessorClient();
        System.out.println("Starting event processor");
        eventProcessorClient.start();
        System.out.println("Press enter to stop.");
        System.in.read();
        System.out.println("Stopping event processor");
        eventProcessorClient.stop();
        System.out.println("Event processor stopped.");
        System.out.println("Exiting process");

        //executorService.shutdownNow();

    }

}