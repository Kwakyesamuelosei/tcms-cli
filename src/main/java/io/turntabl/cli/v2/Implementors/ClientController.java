package io.turntabl.cli.v2.Implementors;

import io.turntabl.Utilities.ConsoleTable;
import io.turntabl.cli.v2.Transfers.ClientTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientController {

    RestTemplate restTemplate = new RestTemplate();

    public void addClient(Map<String,String> requestData) {
        final String uri = "https://customer-mgt-app.herokuapp.com/v1/api/client";
        restTemplate.postForObject(uri, requestData, ResponseEntity.class);
        System.out.println("Client Added Successfully");
    }


    public void deleteClient(Integer clientID) {
        final String uri = "https://customer-mgt-app.herokuapp.com/v1/api/client/" + clientID;
        restTemplate.delete(uri);
        System.out.println("Client Deleted Successfully");

    }

    public ClientTO[] getAllClients() {
        final String uri = "https://customer-mgt-app.herokuapp.com/v1/api/clients";
        ResponseEntity<ClientTO[]> response = restTemplate.getForEntity(uri, ClientTO[].class);
        ClientTO[] result = response.getBody();
        return result;
    }

    public ClientTO[] searchClientByName(String name) {
        final String uri = "https://customer-mgt-app.herokuapp.com/v1/api/client/search/" + name;
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        //request entity is created with request headers
        HttpEntity<ClientTO[]> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<ClientTO[]> response = restTemplate. getForEntity(uri,ClientTO[].class);
        ResponseEntity<ClientTO[]> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                ClientTO[].class
        );

        ClientTO[] result = responseEntity.getBody();
        return result;
    }

    public void updateClient(Map<String, String> requestData) {
        final String uri = "https://customer-mgt-app.herokuapp.com/v1/api/client";
        restTemplate.put(uri, requestData, ResponseEntity.class);
        System.out.println("Client Details Updated Successfully");
    }

    public void printFormat(List<ClientTO> clientList){

        ConsoleTable st = new ConsoleTable();
        st.setShowVerticalLines(false);//if false (default) then no vertical lines are shown
        st.setHeaders("Client ID", "Name", "Address","Telephone Number","Email");
        for (ClientTO client: clientList){
            st.addRow(String.valueOf(client.getClient_id()), client.getClient_name(), client.getClient_address(),client.getClient_telephone(),client.getClient_email());
        }
        st.print();
    }
}
