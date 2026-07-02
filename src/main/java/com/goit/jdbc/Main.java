package com.goit.jdbc;

public class Main {
    public static void main(String[] args) {
        DatabaseInitService.main(args);

        ClientService clientService = new ClientService();

        long id = clientService.create("Test Client");
        System.out.println("Created client id = " + id);

        String name = clientService.getById(id);
        System.out.println("Client name = " + name);

        clientService.setName(id, "Updated Client");
        System.out.println("Updated name = " + clientService.getById(id));

        clientService.deleteById(id);
        System.out.println("Client deleted");

        System.out.println("All clients:");
        for (Client client : clientService.listAll()) {
            System.out.println(client.getId() + " - " + client.getName());
        }
    }
}