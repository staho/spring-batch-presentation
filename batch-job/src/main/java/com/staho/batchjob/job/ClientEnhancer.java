package com.staho.batchjob.job;

import com.staho.batchjob.BadUuidException;
import com.staho.batchjob.entity.Client;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.web.client.RestTemplate;

public class ClientEnhancer implements ItemProcessor<Client, Client> {

    public Client process(Client client) throws BadUuidException {
        RestTemplate restTemplate = new RestTemplate();
        Client clRecv = restTemplate.getForObject("http://localhost:8080/enhance?uuid=" + client.getUuid(), Client.class);

        client.setFirstName(clRecv.getFirstName());
        client.setName(clRecv.getName());

        if(!client.getUuid().equals(clRecv.getUuid())) {
            System.out.println("ERROR BAD UUID RECEIVED");
            throw new BadUuidException("Bad uuid received");
        }

        return  client;//todo: rest api enhancer
    }

}
