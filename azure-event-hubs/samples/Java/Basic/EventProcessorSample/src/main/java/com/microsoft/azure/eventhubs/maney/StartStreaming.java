package com.microsoft.azure.eventhubs.maney;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartStreaming {

    public static void main(String args[]){
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(10);
            for(int i = 0; i < 2; i++){
                executorService.execute(new PublishWithBatchSettingsExample());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            while(!executorService.isTerminated()){
                executorService.shutdown();
            }
        }
    }
}
