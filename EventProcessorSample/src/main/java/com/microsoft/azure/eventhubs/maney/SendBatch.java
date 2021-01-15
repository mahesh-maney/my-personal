package com.microsoft.azure.eventhubs.maney;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventDataBatch;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;

import java.io.IOException;
import java.net.Authenticator;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;

public class SendBatch implements Runnable {
    private static final String CONNECTION_STRING = "Endpoint=sb://c3m-event-hub.servicebus.windows.net/;SharedAccessKeyName=PreviewDataPolicy;SharedAccessKey=j5RjZEUu8GCzmMCahOiWTLepTYdbkQ/IHs5Uz9XTYOQ=;EntityPath=maney-event-hub";
    private static final String EVENT_HUB_NAME = "maney-event-hub";
    @Override
    public void run() {
        final Gson gson = new GsonBuilder().create();
        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(150);
        EventHubClient sender = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            sender = EventHubClient.createFromConnectionStringSync(CONNECTION_STRING, executorService);
            for (int batchNumber = 0; batchNumber < 150; batchNumber++) {
                final EventDataBatch events = sender.createBatch();
                EventData sendEvent;
                do {
                    AzureActivityLog azureActivityLog = getAzureActivityLogJSONStructure();
                    String contents = objectMapper.writeValueAsString(azureActivityLog);
                    String records = "{\"records\": [" + contents + "]} ";
                    byte[] bytes = records.getBytes(Charset.defaultCharset());
//                    System.out.println("Size " + bytes.length);
                    sendEvent = EventData.create(bytes);
                } while (events.tryAdd(sendEvent));
                long st = System.currentTimeMillis();
                sender.sendSync(events);
                long ed = System.currentTimeMillis();
                System.out.println("Time consumed to send batch #" + batchNumber + "# of message size #" + events.getSize() + "# is := #" + (ed - st) / 1000 + "# executing on thread := #" + Thread.currentThread().getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (sender != null) {
                    sender.closeSync();
                }
            } catch (EventHubException e) {
                e.printStackTrace();
            }
            ///executorService.shutdown();
        }
        System.out.println("******** All requests are processed now !!! ******** ");
    }

    private AzureActivityLog getAzureActivityLogJSONStructure() {
        AzureActivityLog azureActivityLog = new AzureActivityLog();
        azureActivityLog.setCallerIpAddress("157.45.192.30");
        azureActivityLog.setCategory("Administrative");
        azureActivityLog.setCorrelationId(UUID.randomUUID().toString());
        azureActivityLog.setDurationMs("4491");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String format1 = simpleDateFormat.format(new Date());
        try {
            Date parse = simpleDateFormat.parse(format1);
            azureActivityLog.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        azureActivityLog.setResourceId("/SUBSCRIPTIONS/37CCA1BE-D0E3-42C7-AE26-908535A0ED21/RESOURCEGROUPS/MANEY_RESOURCE_GROUP/PROVIDERS/MICROSOFT.RESOURCES/DEPLOYMENTS/MICROSOFT.STORAGEACCOUNT-20210111101020");
        azureActivityLog.setOperationName("MICROSOFT.RESOURCES/DEPLOYMENTS/WRITE");
        azureActivityLog.setResultType("Accept");
        azureActivityLog.setResultSignature("Accepted.Created");
        azureActivityLog.setLevel("information");

        Identity identity = new Identity();

        Authorization authorization = new Authorization();
        authorization.setAction("Microsoft.Resources/deployments/write");
        authorization.setScope("/subscriptions/37cca1be-d0e3-42c7-ae26-908535a0ed21/resourceGroups/maney_resource_group/providers/Microsoft.Resources/deployments/Microsoft.StorageAccount-20210111101020");
        Evidence evidence = new Evidence();
        evidence.setPrincipalId("f925ba973ae948e087b8fec744bdeca3");
        evidence.setPrincipalType("Group");
        evidence.setRole("Contributor");
        evidence.setRoleAssignmentId("9b52d41383f240a586dbb8fe641b486a");
        evidence.setRoleAssignmentScope("/subscriptions/37cca1be-d0e3-42c7-ae26-908535a0ed21");
        evidence.setRoleDefinitionId("b24988ac618042a0ab8820f7382dd24c");
        authorization.setEvidence(evidence);
        identity.setAuthorization(authorization);

        Claims claims = new Claims();
        claims.setAio("ATQAy/8SAAAAkza4ZjNiYUCtNLXt438APxUDztm6/h8xpOCIW895SfuxAWLfDrIpN3MMbOL94EfU");
        claims.setAppid("c44b4083-3bb0-49c1-b47d-974e53cbdf3c");
        claims.setAppidacr("2");
        claims.setAud("https://management.core.windows.net/");
        claims.setExp("1610343597");
        claims.setGroups("f925ba97-3ae9-48e0-87b8-fec744bdeca3");
        claims.setHttpSchemasMicrosoftComClaimsAuthnclassreference("1");
        claims.setHttpSchemasMicrosoftComClaimsAuthnmethodsreferences("pwd");
        claims.setHttpSchemasMicrosoftComIdentityClaimsObjectidentifier("b19eb4fb-927b-4663-b7d3-18ee60c393d6");
        claims.setHttpSchemasMicrosoftComIdentityClaimsScope("user_impersonation");
        claims.setHttpSchemasMicrosoftComIdentityClaimsTenantid("6e31e5da-0ac2-47ce-8189-c84c41fa12fe");
        claims.setHttpSchemasXmlsoapOrgWs200505IdentityClaimsGivenname("Mahesh");
        claims.setHttpSchemasXmlsoapOrgWs200505IdentityClaimsNameidentifier("gDWSO6dVRDctvyhFyGb-cHgmRRNnHba290c47ugRgFM");
        claims.setHttpSchemasXmlsoapOrgWs200505IdentityClaimsSurname("Maney");
        claims.setHttpSchemasXmlsoapOrgWs200505IdentityClaimsUpn("mahesh.maney@gmail.com");
        claims.setHttpSchemasXmlsoapOrgWs200505IdentityClaimsName("mahesh.maney@gmail.com");
        claims.setIat("1610339697");
        claims.setIpaddr("157.45.192.30");
        claims.setIss("https://sts.windows.net/6e31e5da-0ac2-47ce-8189-c84c41fa12fe/");
        claims.setName("Mahesh Maney");
        claims.setNbf("1610339697");
        claims.setPuid("10032000FE0A33E4");
        claims.setRh("0.AS0A2uUxbsIKzkeBichMQfoS_oNAS8SwO8FJtH2XTlPL3zwtABA.");
        claims.setUti("jX53m7z21U2BttZTtGyFAA");
        claims.setVer("1.0");
        claims.setXmsTcdt("1547697858");
        identity.setClaims(claims);
        azureActivityLog.setIdentity(identity);

        Properties properties = new Properties();
        properties.setEntity("/subscriptions/37cca1be-d0e3-42c7-ae26-908535a0ed21/resourceGroups/maney_resource_group/providers/Microsoft.Resources/deployments/Microsoft.StorageAccount-20210111101020");
        properties.setEventCategory("Administrative");
        properties.setHierarchy("6e31e5da-0ac2-47ce-8189-c84c41fa12fe/C3M/Development/37cca1be-d0e3-42c7-ae26-908535a0ed21");
        properties.setMessage("Microsoft.Resources/deployments/write");
        properties.setStatusCode("Created");
        properties.setServiceRequestId(null);
        azureActivityLog.setProperties(properties);
        return azureActivityLog;
    }

    /*public static void main(String[] args){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String format1 = simpleDateFormat.format(new Date());
        try {
            Date parse = simpleDateFormat.parse(format1);
            System.out.println(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SendBatch sendBatch = new SendBatch();
        AzureActivityLog azureActivityLogJSONStructure = sendBatch.getAzureActivityLogJSONStructure();
        try {

            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String format = sdf.format(date);
            System.out.println(format);
            Date parse = sdf.parse(format);
//            System.out.println(parse.);

            System.out.println(format);
            ObjectMapper objectMapper = new ObjectMapper();
            String contents = objectMapper.writeValueAsString(azureActivityLogJSONStructure);
            String records = "{\"records\": [" + contents + "]} ";
            System.out.println(records);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }*/


}