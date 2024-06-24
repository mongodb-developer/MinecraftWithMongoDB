package org.minecraft.playermanagement;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.InputStream;
import java.util.Properties;

public class MongoDBPlugin extends JavaPlugin {

    private MongoDatabase database;

    @Override
    public void onEnable() {
        getLogger().info("MongoDBTest plugin has been enabled");

        Properties properties = new Properties();
        String uri ;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                getLogger().severe("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);
            uri = properties.getProperty("mongo.uri");

            MongoClient mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("test");
            getLogger().info("Connected to the database successfully");

            // Example: Listing collections in the database
            for (String name : database.listCollectionNames()) {
                getLogger().info("Collection: " + name);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("MongoDBTest plugin has been disabled");
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
