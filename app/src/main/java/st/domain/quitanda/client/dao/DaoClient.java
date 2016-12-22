package st.domain.quitanda.client.dao;

import android.content.Context;
import android.support.annotation.NonNull;

import st.domain.support.android.sqlite.LiteDataBase;
import st.domain.quitanda.client.references.RData;
import st.domain.quitanda.client.model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import st.domain.support.android.sqlite.LiteSelect;


/**
 * Created by xdata on 7/26/16.
 */
public class DaoClient extends LiteDataBase
{
    private Context context;
    private Client defaultClient;

    public DaoClient(Context context)
    {
        super(context, RData.DATABASE_NAME, RData.DATABASE_VERSION);
        this.context = context;
    }

    public static Client getAnonimeCLient()
    {
        return new Client(1, "Cliente", "Anonimo", "", "");
    }

    /**
     * Registar um novo client
     * @param client
     * @return
     */
    public synchronized long regClient(Client client)
    {
        DaoObject daoObject = new DaoObject(this.context);
        String idResidence = daoObject.getObjectId(client.getResidence(), DaoObject.TypeObject.RESIDENCIA);

        long id = -1;
        begin(Operaction.INSERT);
        insertInto(RData.T_CLIENT, RData.CLI_PREVIEWID)
                .columns(RData.CLI_NAME, RData.CLI_SURNAME, RData.CLI_CONTACT, RData.CLI_OBJ_RESID)
                .values(client.getName(), client.getSurname(), client.getContact(), idResidence);
        execute();
        id = (long) getResult();
        end();
        return id;
    }

    public ArrayList<Client> loadClientes()
    {
        ArrayList<Client> list = new ArrayList<>();
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.VER_CLIENTS);
        execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
        end();
        for(LinkedHashMap<CharSequence, Object> map: result)
        {
            Client client = DaoClient.mountClient(map);
            list.add(client);
        }

        return list;
    }

    @NonNull
    protected static Client mountClient(LinkedHashMap<CharSequence, Object> map) {
        Integer  id = (Integer) map.get(RData.CLI_ID);
        String name = map.get(RData.CLI_NAME).toString();
        String surname = (String) map.get(RData.CLI_SURNAME);
        String contact = (String) map.get(RData.CLI_CONTACT);
        String morada = (String) map.get(RData.OBJ_DESC);
        return new Client(id, name, surname, morada, contact);
    }


    public Client getDefaultCliente() {
        if(this.defaultClient != null) return this.defaultClient;
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.VER_CLIENTS)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {
                        return row.get(RData.CLI_ID) != null && Objects.equals(row.get(RData.CLI_ID), 1);
                    }
                })
                .limit(1)
                .execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = this.getSelectResult();
        if(result.size() > 0 )
            this.defaultClient = mountClient(result.get(0));
        return this.defaultClient;
    }

    public List<? extends Map<String, String>> loadClientesNews()
    {
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.VER_CLIEN_NEWS)
                .execute();

        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
        end();
        return LiteSelect.toNetIntentList(result);
    }
}
