package com.st.ggviario.client.dao;

import android.content.Context;

import com.st.dbutil.android.sqlite.LiteDataBase;
import com.st.ggviario.client.model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.st.ggviario.client.controls.references.RData.ALL;
import static com.st.ggviario.client.controls.references.RData.DATABASE_NAME;
import static com.st.ggviario.client.controls.references.RData.DATABASE_VERSION;
import static com.st.ggviario.client.controls.references.RData.USER_ACCESSNAME;
import static com.st.ggviario.client.controls.references.RData.USER_ID;
import static com.st.ggviario.client.controls.references.RData.USER_NAME;
import static com.st.ggviario.client.controls.references.RData.USER_SURNAME;
import static com.st.ggviario.client.controls.references.RData.VER_CURRENT_LOGIN;

/**
 * Created by xdata on 7/26/16.
 */
public class DaoUser extends LiteDataBase
{
    public DaoUser(Context context)
    {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }

    /**
     * Obter o utilizador logado
     * @return
     */
    public User getLogedUser()
    {
        begin(Operaction.SELECT);
        select(ALL)
                .from(VER_CURRENT_LOGIN);
        execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
        end();
        if(result.size()>0)
        {
            int id = (int) result.get(0).get(USER_ID);
            String name = result.get(0).get(USER_NAME).toString();
            String surname = result.get(0).get(USER_SURNAME).toString();
            String accessName = result.get(0).get(USER_ACCESSNAME).toString();
            User logado = new User(id, name, surname, accessName);
            return  logado;
        }
        return null;
    }

    /**
     * Obter o utilizador logado
     * @return
     */
    public static User geUser(Context context)
    {
        DaoUser daoUser = new DaoUser(context);

        daoUser.begin(Operaction.SELECT);
        daoUser.select(ALL)
                .from(VER_CURRENT_LOGIN);
        daoUser.execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = daoUser.getSelectResult();
        daoUser.end();
        if(result.size()>0)
        {
            int id = (int) result.get(0).get(USER_ID);
            String name = result.get(0).get(USER_NAME).toString();
            String surname = result.get(0).get(USER_SURNAME).toString();
            String accessName = result.get(0).get(USER_ACCESSNAME).toString();
            User logado = new User(id, name, surname, accessName);
            return  logado;
        }

        return null;
    }
}
