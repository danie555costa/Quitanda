package com.st.ggviario.client.dao;

import android.content.Context;
import android.support.annotation.NonNull;

import com.st.dbutil.android.sqlite.LiteDataBase;
import com.st.ggviario.client.references.RData;
import com.st.ggviario.client.model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.st.dbutil.android.sqlite.LiteSelect.toNetIntentList;
import static com.st.ggviario.client.references.RData.ALL;
import static com.st.ggviario.client.references.RData.CLI_CONTACT;
import static com.st.ggviario.client.references.RData.CLI_ID;
import static com.st.ggviario.client.references.RData.CLI_NAME;
import static com.st.ggviario.client.references.RData.CLI_OBJ_RESID;
import static com.st.ggviario.client.references.RData.CLI_PREVIEWID;
import static com.st.ggviario.client.references.RData.CLI_SURNAME;
import static com.st.ggviario.client.references.RData.DATABASE_NAME;
import static com.st.ggviario.client.references.RData.DATABASE_VERSION;
import static com.st.ggviario.client.references.RData.OBJ_DESC;
import static com.st.ggviario.client.references.RData.T_CLIENT;
import static com.st.ggviario.client.references.RData.VER_CLIENTS;
import static com.st.ggviario.client.references.RData.VER_CLIEN_NEWS;


/**
 * Created by xdata on 7/26/16.
 */
public class DaoClient extends LiteDataBase
{
    private Context context;
    private Client defaultClient;

    public DaoClient(Context context)
    {
        super(context, DATABASE_NAME, DATABASE_VERSION);
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
        insertInto(T_CLIENT, CLI_PREVIEWID)
                .columns(CLI_NAME, CLI_SURNAME, CLI_CONTACT, CLI_OBJ_RESID)
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
        select(ALL)
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
        Integer  id = (Integer) map.get(CLI_ID);
        String name = map.get(CLI_NAME).toString();
        String surname = (String) map.get(CLI_SURNAME);
        String contact = (String) map.get(CLI_CONTACT);
        String morada = (String) map.get(OBJ_DESC);
        return new Client(id, name, surname, morada, contact);
    }


    public Client getDefaultCliente() {
        if(this.defaultClient != null) return this.defaultClient;
        begin(Operaction.SELECT);
        select(ALL)
                .from(VER_CLIENTS)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {
                        return row.get(CLI_ID) != null && Objects.equals(row.get(CLI_ID), 1);
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
        select(ALL)
                .from(VER_CLIEN_NEWS)
                .execute();

        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
        end();
        return toNetIntentList(result);
    }
}
