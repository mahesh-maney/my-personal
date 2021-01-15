package com.microsoft.azure.eventhubs.samples.eventprocessorsample;
import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.eventhubs.EventProcessorClientBuilder;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Receiver {

    //private static final String EH_NAMESPACE_CONNECTION_STRING = "Endpoint=sb://c3m-event-hub.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=ESukQ9Gf6t1TznT0QZ5wkkCVfC1lQEa8mlE/TnwFmnc=";
    private static final String EH_NAMESPACE_CONNECTION_STRING = "Endpoint=sb://c3m-event-hub.servicebus.windows.net/;SharedAccessKeyName=PreviewDataPolicy;SharedAccessKey=j5RjZEUu8GCzmMCahOiWTLepTYdbkQ/IHs5Uz9XTYOQ=;EntityPath=maney-event-hub";
    private static final String eventHubName = "maney-event-hub";
    //private static final String STORAGE_CONNECTION_STRING = "<AZURE STORAGE CONNECTION STRING>";
    //private static final String STORAGE_CONTAINER_NAME = "<AZURE STORAGE CONTAINER NAME>";

    public static final Consumer<EventContext> PARTITION_PROCESSOR = eventContext -> {
     System.out.printf("Processing event from partition %s with sequence number %d with body: %s %n",
             eventContext.getPartitionContext().getPartitionId(), eventContext.getEventData().getSequenceNumber(), eventContext.getEventData().getBodyAsString());

        if (eventContext.getEventData().getSequenceNumber() % 10 == 0) {
            eventContext.updateCheckpoint();
        }
    };

    public static final Consumer<ErrorContext> ERROR_HANDLER = errorContext -> {
        System.out.printf("Error occurred in partition processor for partition %s, %s.%n",
            errorContext.getPartitionContext().getPartitionId(),
            errorContext.getThrowable());
    };

    public static void main(String[] args) throws Exception {


        /*String s = "mahesh";
        System.out.println(s);

        String s1 = "mahesh";
        s1 = '"' + s1 + '"';
        System.out.println(s1.equals(s));
        System.out.println(s1);
        System.out.println(Pattern.quote(s));


        String clientid =  "18787d80-1b18-430d-b20a-47c14308f512";
        String domain = "37cca1be-d0e3-42c7-ae26-908535a0ed21";
        String secretid = "OKO~Te07DYvwr8B_O4_PX694cq~2t_yrcf";
        String fqdn = "C3M-Event-Hub.servicebus.windows.net";

        TokenCredential tokenCredential = new DefaultAzureCredentialBuilder().build();
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(domain)
                .clientSecret(secretid)
                .tenantId( "6e31e5da-0ac2-47ce-8189-c84c41fa12fe")
                .build();

        final ConnectionStringBuilder connStr = new ConnectionStringBuilder()
                .setNamespaceName("C3M-Event-Hub") // to target National clouds - use .setEndpoint(URI)
                .setEventHubName("maney-event-hub")
                .setSasKeyName("RootManageSharedAccessKey")
                .setSasKey("ESukQ9Gf6t1TznT0QZ5wkkCVfC1lQEa8mlE/TnwFmnc=");*/


        /*EventHubConsumerAsyncClient consumer = new EventHubClientBuilder()
                //.credential(fqdn,eventHubName,clientSecretCredential)
                .connectionString(connStr.toString(),"maney-event-hub")
                .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                .buildAsyncConsumerClient();

        consumer.receiveFromPartition("0", EventPosition.latest()).subscribe(event -> {
            System.out.printf("Processing event from partition %s with sequence number %d with body: %s %n",
                    event.getPartitionContext().getPartitionId(), event.getData().getSequenceNumber(), event.getData().getBodyAsString());
        });*/

        /*EventProcessorClientBuilder eventProcessorClientBuilder = new EventProcessorClientBuilder();



        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(domain)
                .clientSecret(secretid)
                .tenantId( "6e31e5da-0ac2-47ce-8189-c84c41fa12fe")
                .build();*/


        EventProcessorClientBuilder eventProcessorClientBuilder = new EventProcessorClientBuilder()
            //.credential(fqdn,eventHubName, clientSecretCredential)
                .connectionString(EH_NAMESPACE_CONNECTION_STRING,eventHubName)
            .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
            .processEvent(PARTITION_PROCESSOR)
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
    }

}