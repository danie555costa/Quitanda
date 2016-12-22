package st.domain.quitanda.client.model;

import st.domain.quitanda.client.util.BaseCharacter;

/**
 * Created by Daniel Costa on 7/26/16.
 * User computer: xdata
 */
public class Client extends BaseCharacter
{
    private final Integer idClient;
    private String name;
    private String surname;
    private String residence;
    private String contact;
    private long previewId;

    public Client(Integer idClient, String name, String surname, String residence, String contact)
    {
        this.name = name == null || name.trim().length() == 0? null: name;
        this.surname = surname == null || surname.trim().length() == 0? null: surname;
        this.residence = residence == null || residence.trim().length() == 0? null: residence;
        this.contact = contact == null || contact.trim().length() == 0? null: contact;
        this.idClient = idClient;
    }


    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getResidence() {
        return residence;
    }

    public String getContact() {
        return contact;
    }

    public boolean validateAndClean()
    {
        if(this.name == null
                || name.trim().length() == 0)
            return false;
        if(surname != null && surname.trim().length() == 0) surname = null;
        if(contact != null && contact.trim().length() == 0) contact = null;
        if(residence != null && residence.trim().length() == 0) residence = null;
        return true;
    }

    public void setPreviewId(long previewId)
    {
        this.previewId = previewId;
    }

    @Override
    public String toString() {
        return name+" "+((surname!=null)?(surname.trim()): "");
    }

    public String getId()
    {
        return ((this.idClient == null) ? "~~" + previewId : this.idClient + "");
    }

    public int getRealId() {
        return  (this.idClient != null)? this.idClient: -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (previewId != client.previewId) return false;
        return idClient != null ? idClient.equals(client.idClient)
                : client.idClient == null;

    }

    @Override
    public int hashCode() {
        int result = idClient != null ? idClient.hashCode() : 0;
        result = 31 * result + (int) (previewId ^ (previewId >>> 32));
        return result;
    }
}
