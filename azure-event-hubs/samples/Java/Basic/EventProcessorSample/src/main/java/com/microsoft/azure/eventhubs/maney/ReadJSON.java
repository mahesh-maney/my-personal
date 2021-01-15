package com.microsoft.azure.eventhubs.maney;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class ReadJSON {

    public static void main(String[] args){
        try {

            JSONParser jsonParser = new JSONParser();
            FileReader fileReader = new FileReader("c:\\temp\\azure.json");
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            JSONObject identity = (JSONObject)jsonObject.get("identity");
            //JSONObject authorization = ((JSONObject)((JSONObject)identity).get("authorization"));

            //JSONObject evidence = ((JSONObject)((JSONObject)authorization).get("evidence"));
            //System.out.println(evidence.keySet());




            JSONObject claims = ((JSONObject)((JSONObject)identity).get("claims"));
            System.out.println(claims.keySet());

            //System.out.println(authorization.keySet());
            //System.out.println(claims.keySet());


        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
