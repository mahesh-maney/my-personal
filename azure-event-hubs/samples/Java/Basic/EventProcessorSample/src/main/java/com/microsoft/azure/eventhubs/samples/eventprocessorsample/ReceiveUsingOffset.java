package com.microsoft.azure.eventhubs.samples.eventprocessorsample;

import com.microsoft.azure.eventhubs.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;

public class ReceiveUsingOffset {

    public static void main(String[] args) throws EventHubException, ExecutionException, InterruptedException, IOException {

        final ConnectionStringBuilder connStr = new ConnectionStringBuilder()
                .setNamespaceName("C3M-Event-Hub") // to target National clouds - use .setEndpoint(URI)
                .setEventHubName("maney-event-hub")
        .setSasKeyName("PreviewDataPolicy")
        .setSasKey("j5RjZEUu8GCzmMCahOiWTLepTYdbkQ/IHs5Uz9XTYOQ=");
        //.setSasKeyName("RootManageSharedAccessKey")
                //.setSasKey("ESukQ9Gf6t1TznT0QZ5wkkCVfC1lQEa8mlE/TnwFmnc=");

        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        final EventHubClient ehClient = EventHubClient.createFromConnectionStringSync(connStr.toString(), executorService);

        final EventHubRuntimeInformation eventHubInfo = ehClient.getRuntimeInformation().get();
        PartitionReceiver receiver = null;
        try {
            // Read from first partition
            for (int i = 0; i < eventHubInfo.getPartitionCount(); i++) {
                final String partitionId = eventHubInfo.getPartitionIds()[i];
                // Specifying OFFSET - is the most performant way to create Receivers.
                receiver = ehClient.createEpochReceiverSync(
                        EventHubClient.DEFAULT_CONSUMER_GROUP_NAME,
                        partitionId,
                        EventPosition.fromOffset(String.valueOf(i),true),
                        5);


                //Iterable<EventData> receivedEvents = receiver.receiveSync(5);
                CompletableFuture<Iterable<EventData>> receive = receiver.receive(5);
                Iterable<EventData> receivedEvents = receive.get();

                //while (true) {
                //int batchSize = 0;
                if (receivedEvents != null) {
                    for (final EventData receivedEvent : receivedEvents) {
                        if (receivedEvent.getBytes() != null) {
                            System.out.println(String.format("Message Payload: %s", new String(receivedEvent.getBytes(), Charset.defaultCharset())));

                        }
                        System.out.println(String.format("Offset: %s, SeqNo: %s, EnqueueTime: %s",
                                receivedEvent.getSystemProperties().getOffset(),
                                receivedEvent.getSystemProperties().getSequenceNumber(),
                                receivedEvent.getSystemProperties().getEnqueuedTime()));
                        //batchSize++;
                    }
                }

                //System.out.println(String.format("ReceivedBatch Size: %s", batchSize));
                //receivedEvents = receiver.receiveSync(1);
                //System.out.println(receivedEvents);
                //}
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally{
            // cleaning up receivers is paramount;
            // Quota limitation on maximum number of concurrent receivers per consumergroup per partition is 5
            receiver.close()
                    .thenComposeAsync(aVoid -> ehClient.close(), executorService)
                    .whenCompleteAsync((t, u) -> {
                        if (u != null) {
                            // wire-up this error to diagnostics infrastructure
                            System.out.println(String.format("closing failed with error: %s", u.toString()));
                        }
                    }, executorService).get();

            executorService.shutdown();
        }
    }

    /*private static void consumeEvents() throws EventHubException, IOException, ExecutionException, InterruptedException {

        ConnectionStringBuilder connStr = new ConnectionStringBuilder().setNamespaceName("C3M-Event-Hub")
                .setEventHubName("maney-event-hub")
                .setSasKeyName("RootManageSharedAccessKey")
                .setSasKey("ESukQ9Gf6t1TznT0QZ5wkkCVfC1lQEa8mlE/TnwFmnc=");

        EventHubClient client = EventHubClient.createFromConnectionStringSync((connStr.toString(), Executors.newFixedThreadPool(10));

        EventHubRuntimeInformation runTimeInfo = client.getRuntimeInformation().get();
        int numPartitions = runTimeInfo.getPartitionCount();
        for (int partition = 0; partition < numPartitions; partition++) {
            PartitionReceiver receiver =
                    client.createReceiverSync(EventHubClient.DEFAULT_CONSUMER_GROUP_NAME, String.valueOf(partition),
                            EventPosition.fromOffset("1",true));
            receiver.receive(10).handle((records, throwable) -> handleComplete(receiver, records, throwable));
        }
    }

    private static Object handleComplete(PartitionReceiver receiver, Iterable<EventData> records, Throwable throwable) {
        receiver.setReceiveHandler(new PartitionReceiveHandler() {
            @Override
            public int getMaxEventCount() {
                return 1;
            }

            @Override
            public void onReceive(Iterable<EventData> iterable) {
                Iterator<EventData> iterator = iterable.iterator();
                while(iterator.hasNext()){
                    System.out.println(iterator.next());
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
        receiver.receive(1);
        return null;
    }*/


}
