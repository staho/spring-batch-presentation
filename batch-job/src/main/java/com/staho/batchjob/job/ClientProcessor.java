package com.staho.batchjob.job;

import com.staho.batchjob.entity.Client;
import com.staho.batchjob.entity.ClientRepository;
import org.springframework.batch.item.ItemProcessor;

public class ClientProcessor implements ItemProcessor<Client, Client> {

    public Client process(Client client) throws Exception {
        System.out.println("Processing " + client);

        String uuid = client.getUuid();

        client.setUuid("staho-app-uuid-" + uuid.toUpperCase());

        return client;

        }
}
