package com.microsoft.azure.eventhubs.samples.eventprocessorsample;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.messaging.eventhubs.*;
import com.azure.messaging.eventhubs.models.*;
import reactor.core.Disposable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ConsumeEventHub {

    private final String CONNECTION_STRING = "Endpoint=sb://c3m-event-hub.servicebus.windows.net/;SharedAccessKeyName=PreviewDataPolicy;SharedAccessKey=j5RjZEUu8GCzmMCahOiWTLepTYdbkQ/IHs5Uz9XTYOQ=;EntityPath=maney-event-hub";
    //private final String CONNECTION_STRING = "Endpoint=sb://c3m-event-hub-namespace.servicebus.windows.net/;SharedAccessKeyName=Shared-Access-Policies;SharedAccessKey=mp6DbnZuVysy4kQUxXCxNXEc+xygTMrVUToHV0E/u80=;EntityPath=c3m_event_hub";
    private final String EVENT_HUB_NAME = "maney-event-hub";
    //private final String EVENT_HUB_NAME = "c3m_event_hub";


    public static void main(String args[]){
        ConsumeEventHub consumeEventHub = new ConsumeEventHub();
        //consumeEventHub.processUsingProcessorClient();
        //consumeEventHub.anotherOption();
        consumeEventHub.sendEvents();
    }

    private void sendEvents(){

        /*EventHubProducerAsyncClient eventHubProducerAsyncClient = new EventHubProducerAsyncClient("","",null,null,null,null,null,true,null);
        eventHubProducerAsyncClient.createBatch().*/

        String event = "{\"records\": [{ \"time\": \"2021-01-05T11:40:23.9296886Z\", \"resourceId\": \"/SUBSCRIPTIONS/37CCA1BE-D0E3-42C7-AE26-908535A0ED21/RESOURCEGROUPS/SONI-TEST/PROVIDERS/MICROSOFT.STORAGE/STORAGEACCOUNTS/SONITESTACCESS\", \"operationName\": \"MICROSOFT.STORAGE/STORAGEACCOUNTS/WRITE\", \"category\": \"Administrative\", \"resultType\": \"Start\", \"resultSignature\": \"Started.\", \"durationMs\": \"0\", \"callerIpAddress\": \"43.229.90.80\", \"correlationId\": \"4498f501-6ae2-4fee-b247-b47602529c03\", \"identity\": {\"authorization\":{\"scope\":\"/subscriptions/37cca1be-d0e3-42c7-ae26-908535a0ed21/resourceGroups/soni-test/providers/Microsoft.Storage/storageAccounts/sonitestaccess\",\"action\":\"Microsoft.Storage/storageAccounts/write\",\"evidence\":{\"role\":\"Subscription Admin\"}},\"claims\":{\"aud\":\"https://management.core.windows.net/\",\"iss\":\"https://sts.windows.net/6e31e5da-0ac2-47ce-8189-c84c41fa12fe/\",\"iat\":\"1609843327\",\"nbf\":\"1609843327\",\"exp\":\"1609847227\",\"http://schemas.microsoft.com/claims/authnclassreference\":\"1\",\"aio\":\"ATQAy/8SAAAARpiFoC3tA9SkuwwChEYh0g6L8bseHDLMQde3sRqr5rLjOAbvcvB++HElNbuZ64o3\",\"http://schemas.microsoft.com/claims/authnmethodsreferences\":\"pwd\",\"appid\":\"c44b4083-3bb0-49c1-b47d-974e53cbdf3c\",\"appidacr\":\"2\",\"groups\":\"c692e3bf-dbbd-4605-a02e-0f97d88eefce,f925ba97-3ae9-48e0-87b8-fec744bdeca3\",\"ipaddr\":\"43.229.90.80\",\"name\":\"Soni Scariah\",\"http://schemas.microsoft.com/identity/claims/objectidentifier\":\"df24ac78-d9ab-4bee-9487-d10b3ba502b1\",\"puid\":\"1003200061307A99\",\"rh\":\"0.AS0A2uUxbsIKzkeBichMQfoS_oNAS8SwO8FJtH2XTlPL3zwtAP0.\",\"http://schemas.microsoft.com/identity/claims/scope\":\"user_impersonation\",\"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier\":\"tjV5vkIKwgOr_xZU2auFgtbjUyC6kuDcRgLrSiuuyw8\",\"http://schemas.microsoft.com/identity/claims/tenantid\":\"6e31e5da-0ac2-47ce-8189-c84c41fa12fe\",\"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name\":\"soni.scariah@c3m.io\",\"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/upn\":\"soni.scariah@c3m.io\",\"uti\":\"1_ZCFlPSdkqaVGljhLKwAQ\",\"ver\":\"1.0\",\"wids\":\"62e90394-69f5-4237-9190-012177145e10\",\"xms_tcdt\":\"1547697858\"}}, \"level\": \"Information\", \"properties\": {\"requestbody\":\"{\\\"sku\\\":{\\\"name\\\":\\\"Standard_RAGRS\\\",\\\"tier\\\":\\\"Standard\\\"},\\\"kind\\\":\\\"StorageV2\\\",\\\"id\\\":\\\"/subscriptions/37cca1be-d0e3-42c7-ae26-908535a0ed21/resourceGroups/soni-test/providers/Microsoft.Storage/storageAccounts/sonitestaccess\\\",\\\"name\\\":\\\"sonitestaccess\\\",\\\"type\\\":\\\"Microsoft.Storage/storageAccounts\\\",\\\"location\\\":\\\"centralus\\\",\\\"tags\\\":{},\\\"properties\\\":{\\\"privateEndpointConnections\\\":[],\\\"azureFilesIdentityBasedAuthentication\\\":{\\\"directoryServiceOptions\\\":\\\"None\\\"},\\\"minimumTlsVersion\\\":\\\"TLS1_2\\\",\\\"allowBlobPublicAccess\\\":true,\\\"networkAcls\\\":{\\\"bypass\\\":\\\"AzureServices\\\",\\\"virtualNetworkRules\\\":[],\\\"ipRules\\\":[],\\\"defaultAction\\\":\\\"Allow\\\"},\\\"supportsHttpsTrafficOnly\\\":true,\\\"encryption\\\":{\\\"services\\\":{\\\"file\\\":{\\\"keyType\\\":\\\"Account\\\",\\\"enabled\\\":true,\\\"lastEnabledTime\\\":\\\"2021-01-05T07:44:46.6064587Z\\\"},\\\"blob\\\":{\\\"keyType\\\":\\\"Account\\\",\\\"enabled\\\":true,\\\"lastEnabledTime\\\":\\\"2021-01-05T07:44:46.6064587Z\\\"}},\\\"keySource\\\":\\\"Microsoft.Storage\\\"},\\\"accessTier\\\":\\\"Hot\\\",\\\"provisioningState\\\":\\\"Succeeded\\\",\\\"creationTime\\\":\\\"2021-01-05T07:44:46.5127178Z\\\",\\\"primaryEndpoints\\\":{\\\"dfs\\\":\\\"https://sonitestaccess.dfs.core.windows.net/\\\",\\\"web\\\":\\\"https://sonitestaccess.z19.web.core.windows.net/\\\",\\\"blob\\\":\\\"https://sonitestaccess.blob.core.windows.net/\\\",\\\"queue\\\":\\\"https://sonitestaccess.queue.core.windows.net/\\\",\\\"table\\\":\\\"https://sonitestaccess.table.core.windows.net/\\\",\\\"file\\\":\\\"https://sonitestaccess.file.core.windows.net/\\\"},\\\"primaryLocation\\\":\\\"centralus\\\",\\\"statusOfPrimary\\\":\\\"available\\\",\\\"statusOfSecondary\\\":\\\"available\\\",\\\"secondaryEndpoints\\\":{\\\"dfs\\\":\\\"https://sonitestaccess-secondary.dfs.core.windows.net/\\\",\\\"web\\\":\\\"https://sonitestaccess-secondary.z19.web.core.windows.net/\\\",\\\"blob\\\":\\\"https://sonitestaccess-secondary.blob.core.windows.net/\\\",\\\"queue\\\":\\\"https://sonitestaccess-secondary.queue.core.windows.net/\\\",\\\"table\\\":\\\"https://sonitestaccess-secondary.table.core.windows.net/\\\"}}}\",\"eventCategory\":\"Administrative\",\"entity\":\"/subscriptions/37cca1be-d0e3-42c7-ae26-908535a0ed21/resourceGroups/soni-test/providers/Microsoft.Storage/storageAccounts/sonitestaccess\",\"message\":\"Microsoft.Storage/storageAccounts/write\",\"hierarchy\":\"6e31e5da-0ac2-47ce-8189-c84c41fa12fe/C3M/Development/37cca1be-d0e3-42c7-ae26-908535a0ed21\"}},{ \"time\": \"2021-01-05T11:40:26.1946826Z\", \"resourceId\": \"/SUBSCRIPTIONS/37CCA1BE-D0E3-42C7-AE26-908535A0ED21/RESOURCEGROUPS/SONI-TEST/PROVIDERS/MICROSOFT.STORAGE/STORAGEACCOUNTS/SONITESTACCESS\", \"operationName\": \"MICROSOFT.STORAGE/STORAGEACCOUNTS/WRITE\", \"category\": \"Administrative\", \"resultType\": \"Success\", \"resultSignature\": \"Succeeded.OK\", \"durationMs\": \"2265\", \"callerIpAddress\": \"43.229.90.80\", \"correlationId\": \"4498f501-6ae2-4fee-b247-b47602529c03\", \"identity\": {\"authorization\":{\"scope\":\"/subscriptions/37cca1be-d0e3-42c7-ae26-908535a0ed21/resourceGroups/soni-test/providers/Microsoft.Storage/storageAccounts/sonitestaccess\",\"action\":\"Microsoft.Storage/storageAccounts/write\",\"evidence\":{\"role\":\"Subscription Admin\"}},\"claims\":{\"aud\":\"https://management.core.windows.net/\",\"iss\":\"https://sts.windows.net/6e31e5da-0ac2-47ce-8189-c84c41fa12fe/\",\"iat\":\"1609843327\",\"nbf\":\"1609843327\",\"exp\":\"1609847227\",\"http://schemas.microsoft.com/claims/authnclassreference\":\"1\",\"aio\":\"ATQAy/8SAAAARpiFoC3tA9SkuwwChEYh0g6L8bseHDLMQde3sRqr5rLjOAbvcvB++HElNbuZ64o3\",\"http://schemas.microsoft.com/claims/authnmethodsreferences\":\"pwd\",\"appid\":\"c44b4083-3bb0-49c1-b47d-974e53cbdf3c\",\"appidacr\":\"2\",\"groups\":\"c692e3bf-dbbd-4605-a02e-0f97d88eefce,f925ba97-3ae9-48e0-87b8-fec744bdeca3\",\"ipaddr\":\"43.229.90.80\",\"name\":\"Soni Scariah\",\"http://schemas.microsoft.com/identity/claims/objectidentifier\":\"df24ac78-d9ab-4bee-9487-d10b3ba502b1\",\"puid\":\"1003200061307A99\",\"rh\":\"0.AS0A2uUxbsIKzkeBichMQfoS_oNAS8SwO8FJtH2XTlPL3zwtAP0.\",\"http://schemas.microsoft.com/identity/claims/scope\":\"user_impersonation\",\"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier\":\"tjV5vkIKwgOr_xZU2auFgtbjUyC6kuDcRgLrSiuuyw8\",\"http://schemas.microsoft.com/identity/claims/tenantid\":\"6e31e5da-0ac2-47ce-8189-c84c41fa12fe\",\"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name\":\"soni.scariah@c3m.io\",\"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/upn\":\"soni.scariah@c3m.io\",\"uti\":\"1_ZCFlPSdkqaVGljhLKwAQ\",\"ver\":\"1.0\",\"wids\":\"62e90394-69f5-4237-9190-012177145e10\",\"xms_tcdt\":\"1547697858\"}}, \"level\": \"Information\", \"properties\": {\"statusCode\":\"OK\",\"serviceRequestId\":\"1a32eb12-a49e-4cf7-9707-124f6d3cbc22\",\"responseBody\":\"{\\\"sku\\\":{\\\"name\\\":\\\"Standard_RAGRS\\\",\\\"tier\\\":\\\"Standard\\\"},\\\"kind\\\":\\\"StorageV2\\\",\\\"id\\\":\\\"/subscriptions/37cca1be-d0e3-42c7-ae26-908535a0ed21/resourceGroups/soni-test/providers/Microsoft.Storage/storageAccounts/sonitestaccess\\\",\\\"name\\\":\\\"sonitestaccess\\\",\\\"type\\\":\\\"Microsoft.Storage/storageAccounts\\\",\\\"location\\\":\\\"centralus\\\",\\\"tags\\\":{},\\\"properties\\\":{\\\"privateEndpointConnections\\\":[],\\\"azureFilesIdentityBasedAuthentication\\\":{\\\"directoryServiceOptions\\\":\\\"None\\\"},\\\"minimumTlsVersion\\\":\\\"TLS1_2\\\",\\\"allowBlobPublicAccess\\\":true,\\\"networkAcls\\\":{\\\"bypass\\\":\\\"AzureServices\\\",\\\"virtualNetworkRules\\\":[],\\\"ipRules\\\":[],\\\"defaultAction\\\":\\\"Allow\\\"},\\\"supportsHttpsTrafficOnly\\\":true,\\\"encryption\\\":{\\\"services\\\":{\\\"file\\\":{\\\"keyType\\\":\\\"Account\\\",\\\"enabled\\\":true,\\\"lastEnabledTime\\\":\\\"2021-01-05T07:44:46.6064587Z\\\"},\\\"blob\\\":{\\\"keyType\\\":\\\"Account\\\",\\\"enabled\\\":true,\\\"lastEnabledTime\\\":\\\"2021-01-05T07:44:46.6064587Z\\\"}},\\\"keySource\\\":\\\"Microsoft.Storage\\\"},\\\"accessTier\\\":\\\"Hot\\\",\\\"provisioningState\\\":\\\"Succeeded\\\",\\\"creationTime\\\":\\\"2021-01-05T07:44:46.5127178Z\\\",\\\"primaryEndpoints\\\":{\\\"dfs\\\":\\\"https://sonitestaccess.dfs.core.windows.net/\\\",\\\"web\\\":\\\"https://sonitestaccess.z19.web.core.windows.net/\\\",\\\"blob\\\":\\\"https://sonitestaccess.blob.core.windows.net/\\\",\\\"queue\\\":\\\"https://sonitestaccess.queue.core.windows.net/\\\",\\\"table\\\":\\\"https://sonitestaccess.table.core.windows.net/\\\",\\\"file\\\":\\\"https://sonitestaccess.file.core.windows.net/\\\"},\\\"primaryLocation\\\":\\\"centralus\\\",\\\"statusOfPrimary\\\":\\\"available\\\",\\\"secondaryLocation\\\":\\\"eastus2\\\",\\\"statusOfSecondary\\\":\\\"available\\\",\\\"secondaryEndpoints\\\":{\\\"dfs\\\":\\\"https://sonitestaccess-secondary.dfs.core.windows.net/\\\",\\\"web\\\":\\\"https://sonitestaccess-secondary.z19.web.core.windows.net/\\\",\\\"blob\\\":\\\"https://sonitestaccess-secondary.blob.core.windows.net/\\\",\\\"queue\\\":\\\"https://sonitestaccess-secondary.queue.core.windows.net/\\\",\\\"table\\\":\\\"https://sonitestaccess-secondary.table.core.windows.net/\\\"}}}\",\"eventCategory\":\"Administrative\",\"entity\":\"/subscriptions/37cca1be-d0e3-42c7-ae26-908535a0ed21/resourceGroups/soni-test/providers/Microsoft.Storage/storageAccounts/sonitestaccess\",\"message\":\"Microsoft.Storage/storageAccounts/write\",\"hierarchy\":\"6e31e5da-0ac2-47ce-8189-c84c41fa12fe/C3M/Development/37cca1be-d0e3-42c7-ae26-908535a0ed21\"}}]}";

        // create a producer using the namespace connection string and event hub name
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(CONNECTION_STRING, EVENT_HUB_NAME)
                .buildProducerClient();

        // prepare a batch of events to send to the event hub
        EventDataBatch batch = producer.createBatch();
        for(int i = 0; i < 10; i++) {
            batch.tryAdd(new EventData(event));
        }
        // send the batch of events to the event hub
        producer.send(batch);

        // close the producer
        producer.close();
    }



    private void processUsingProcessorClient(){
        List<Disposable> subscriptions = null;
        try {

            EventHubConsumerAsyncClient eventHubConsumerAsyncClient = new EventHubClientBuilder()
                    .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                    .connectionString(CONNECTION_STRING, EVENT_HUB_NAME)
                    .buildAsyncConsumerClient();

            ReceiveOptions receiveOptions = new ReceiveOptions().setTrackLastEnqueuedEventProperties(true);

            List<String> block = eventHubConsumerAsyncClient.getPartitionIds().collectList().block();
            // Get the total partitions in the hub.
            Iterator<String> iterator = block.stream().iterator();
            String partitionID = null;
            subscriptions = new ArrayList<>(block.size());
            while(iterator.hasNext()){
                partitionID = iterator.next();
                Disposable subscription = eventHubConsumerAsyncClient.receiveFromPartition(
                        partitionID,
                        EventPosition.fromOffset(0), receiveOptions).subscribe(PARTITION_PROCESSOR,ERROR_HANDLER);
                subscriptions.add(subscription);
            }
            System.out.println("*** Started **** ");
            System.in.read();
        }catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if(subscriptions != null){
                subscriptions.forEach( subscrip -> {
                    subscrip.dispose();
                });
            }
        }
    }

    private final Consumer<PartitionEvent> PARTITION_PROCESSOR = partitionEvent -> {
        EventData event = partitionEvent.getData();
        PartitionContext partitionContext = partitionEvent.getPartitionContext();
        String contents = new String(event.getBody(), UTF_8);

        //LastEnqueuedEventProperties properties = partitionEvent.getLastEnqueuedEventProperties();
        //System.out.printf("****** Information received at %s. Last successfully retrieved : sequence number: [%s%n] Offset : [%s%n]",properties.getRetrievalTime(), properties.getSequenceNumber(), properties.getOffset() + " ******");

        System.out.printf("Partition[%s] with Offset-[%s] and Sequence Number[%s] has contents: '%s'%n",
                partitionContext.getPartitionId(),
                event.getOffset(),
                event.getSequenceNumber(),
                contents);
    };

    private final Consumer<Throwable> ERROR_HANDLER = errorContext -> {
        System.out.printf("Error occurred in partition processor");
        errorContext.printStackTrace();
    };


    public ClientSecretCredential createClientSecretCredential() {
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId("18787d80-1b18-430d-b20a-47c14308f512")
                .clientSecret("OKO~Te07DYvwr8B_O4_PX694cq~2t_yrcf")
                .tenantId("6e31e5da-0ac2-47ce-8189-c84c41fa12fe")
                .build();
        return clientSecretCredential;
    }

    private void anotherOption() {
        EventProcessorClient processor = new EventProcessorClientBuilder()
                .connectionString(CONNECTION_STRING, EVENT_HUB_NAME)
                .consumerGroup("$DEFAULT")
                .checkpointStore(new C3MCheckPointStore())
                .processEvent(eventContext -> {
                    System.out.println("*****" + eventContext.getEventData().getBodyAsString());
                })
                .processError(errorContext -> {
                    System.err.println("EventProcessorClient Error: " + errorContext.getThrowable());
                }).buildEventProcessorClient();

        processor.start();
        System.out.println("Processing telemetry. Press any key to exit.");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping");
        processor.stop();
    }

}
