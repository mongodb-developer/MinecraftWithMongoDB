package org.minecraft.playermanagement;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.InputStream;
import java.util.Properties;

public class MongoDBConnection {
    public void connectAndTest() {
        Properties properties = new Properties();
        String uri = null;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }

            // Load the properties file
            properties.load(input);
            uri = properties.getProperty("mongo.uri");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (uri != null) {
            try (MongoClient mongoClient = MongoClients.create(uri)) {
                MongoDatabase database = mongoClient.getDatabase("test");
                System.out.println("Connected to the database successfully");

                // Check the collections in the database
                for (String name : database.listCollectionNames()) {
                    System.out.println("Collection: " + name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Mongo URI is not defined in the properties file.");
        }
    }
}

