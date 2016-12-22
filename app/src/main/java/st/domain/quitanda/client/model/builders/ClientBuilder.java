package st.domain.quitanda.client.model.builders;

import st.domain.quitanda.client.model.Client;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class ClientBuilder extends Builder<Client>
{
    private Integer idClient;
    private String name;
    private String surname;
    private String residence;
    private String contact;
    private long previewId;


    public ClientBuilder() {
        super(Client.class);
    }


    public ClientBuilder idClient(Integer idClient) {
        this.idClient = idClient;
        return this;
    }

    public ClientBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ClientBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }

    public ClientBuilder residence(String residence) {
        this.residence = residence;
        return this;
    }

    public ClientBuilder contact(String contact) {
        this.contact = contact;
        return this;
    }

    public ClientBuilder previewId(long previewId) {
        this.previewId = previewId;
        return this;
    }

    @Override
    public Client build() {
        return new Client(idClient, name, surname, residence, contact);
    }
}