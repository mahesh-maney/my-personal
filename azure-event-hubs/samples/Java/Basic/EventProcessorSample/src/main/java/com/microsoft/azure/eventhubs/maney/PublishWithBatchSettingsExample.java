package com.microsoft.azure.eventhubs.maney;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Lists;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.gax.batching.BatchingSettings;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.paging.Page;
import com.google.api.services.storage.Storage;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.threeten.bp.Duration;

public class PublishWithBatchSettingsExample implements Runnable {

    @Override
    public void run() {
        String projectId = "mohan-test-gcp-project";
        String topicId = "maney-c3m-topic";
        try {
            PublishWithBatchSettingsExample publishWithBatchSettingsExample = new PublishWithBatchSettingsExample();
            publishWithBatchSettingsExample.publishWithBatchSettingsExample(projectId, topicId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) throws Exception {
        // TODO(developer): Replace these variables before running the sample.
        String projectId = "mohan-test-gcp-project";
        String topicId = "maney-c3m-topic";
        PublishWithBatchSettingsExample publishWithBatchSettingsExample = new PublishWithBatchSettingsExample();
        publishWithBatchSettingsExample.publishWithBatchSettingsExample(projectId, topicId);
    }

    public void publishWithBatchSettingsExample(String projectId, String topicId) throws IOException, ExecutionException, InterruptedException {

        TopicName topicName = TopicName.of(projectId, topicId);
        Publisher publisher = null;
        List<ApiFuture<String>> messageIdFutures = new ArrayList<>();
        try {

            URL resource = PublishWithBatchSettingsExample.class.getResource("/mohan-test-gcp-project.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(resource.getPath()));

            // Batch settings control how the publisher batches messages
            long requestBytesThreshold = 5000L; // default : 1 byte
            long messageCountBatchSize = 100L; // default : 1 message

            Duration publishDelayThreshold = Duration.ofMillis(100);

            // Publish request get triggered based on request size, messages count & time since last
            // publish, whichever condition is met first.
            BatchingSettings batchingSettings =
                    BatchingSettings.newBuilder()
                            .setElementCountThreshold(messageCountBatchSize)
                            .setRequestByteThreshold(requestBytesThreshold)
                            .setDelayThreshold(publishDelayThreshold)
                            .build();

            // Create a publisher instance with default settings bound to the topic
            publisher = Publisher.newBuilder(topicName)
                    .setBatchingSettings(batchingSettings)
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            // schedule publishing one message at a time : messages get automatically batched
            ObjectMapper objectMapper = new ObjectMapper();
            int messageCount = 200;
            for (int batch = 0; batch < 200; batch++) {
                int messages = 0;
                do {
                    String contents = objectMapper.writeValueAsString(getGCPJSON());
                    ByteString data = ByteString.copyFromUtf8(contents);
                    PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
                    // Once published, returns a server-assigned message id (unique within the topic)
                    ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
                    messageIdFutures.add(messageIdFuture);
                    messages++;
                } while(messages <= messageCount);
                long start = System.currentTimeMillis();
                List<String> messageIds = ApiFutures.allAsList(messageIdFutures).get();
                long end = System.currentTimeMillis();
                messageIdFutures.clear();
                System.out.println("Batch #" + batch + "# published #" + messageIds.size() + "# messages in #" + ((end-start)/1000) + "# seconds, with Thread #" + Thread.currentThread().getName());
            }
        } catch(Exception ex){
            ex.printStackTrace();
        } finally {
            // Wait on any pending publish requests.
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
            System.out.println("*** Completed ***");
        }
    }

    private GCPJSONStructure getGCPJSON(){
        GCPJSONStructure gcpjsonStructure = null;
        try {
            gcpjsonStructure = new GCPJSONStructure();
            gcpjsonStructure.setInsertId("9q23imdvt8e");
            gcpjsonStructure.setLogName("projects/mohan-test-gcp-project/logs/cloudaudit.googleapis.com%2Fdata_access");
                ProtoPayload protoPayload = new ProtoPayload();
                protoPayload.setType("type.googleapis.com/google.cloud.audit.AuditLog");
                    AuthenticationInfo authenticationInfo = new AuthenticationInfo();
                    //authenticationInfo.setPrincipalEmail( "c3m-release-test@mohan-test-gcp-project.iam.gserviceaccount.com");
                    authenticationInfo.setPrincipalEmail( "mahesh.maney@c3m.io");
                    authenticationInfo.setServiceAccountKeyName("//iam.googleapis.com/projects/mohan-test-gcp-project/serviceAccounts/c3m-release-test@mohan-test-gcp-project.iam.gserviceaccount.com/keys/623b5190780d41495273c6b02a85c2cb5b90b92f");
                protoPayload.setAuthenticationInfo(authenticationInfo);
                    AuthorizationInfo authorizationInfo = new AuthorizationInfo();
                    authorizationInfo.setGranted("true");
                    authorizationInfo.setPermission("dataproc.clusters.list");
                    authorizationInfo.setResourceAttributes(new ArrayList());
                protoPayload.setAuthorizationInfo(authorizationInfo);
                protoPayload.setMethodName("google.cloud.dataproc.v1.ClusterController.ListClusters");
                    Request request = new Request();
                    request.setPageSizze("100");
                    request.setProjectId("mohan-test-gcp-project");
                    request.setRegion("us-east1");
                    request.setType("type.googleapis.com/google.cloud.dataproc.v1.ListClustersRequest");
                protoPayload.setRequest(request);
                    RequestMetadata requestMetadata = new RequestMetadata();
                    requestMetadata.setCalleIP("52.22.233.43");
                    requestMetadata.setCallerSuppliedUserAgent("google-api-nodejs-client/4.4.3 (gzip),gzip(gfe)");
                    requestMetadata.setDestinationAttributes(new ArrayList());
                        RequestAttributes requestAttributes = new RequestAttributes();
                        requestAttributes.setAuth(new ArrayList());
                        requestAttributes.setTime(new Date());
                    requestMetadata.setRequestAttributes(requestAttributes);
                protoPayload.setRequestMetadata(requestMetadata);
                    ResourceLocation resourceLocation = new ResourceLocation();
                    resourceLocation.setCurrentLocations(Arrays.asList("us-east1"));
                protoPayload.setResourceLocation(resourceLocation);
                protoPayload.setResourceName("projects/mohan-test-gcp-project/regions/us-east1/clusters");
                protoPayload.setServiceName("dataproc.googleapis.com");
                protoPayload.setStatus(new ArrayList());
            gcpjsonStructure.setReceiveTimestamp(new Date());
                Resource resource = new Resource();
                    Labels labels = new Labels();
                    labels.setCluster_name("");
                    labels.setCluster_uuid("");
                    labels.setProject_id("mohan-test-gcp-project");
                    labels.setRegion("");
                resource.setLabels(labels);
                resource.setType("cloud_dataproc_cluster");
            gcpjsonStructure.setResource(resource);
            gcpjsonStructure.setSeverity("INFO");
            gcpjsonStructure.setTimestamp(new Date());
            gcpjsonStructure.setProtoPayload(protoPayload);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return gcpjsonStructure;
    }
}