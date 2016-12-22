package st.domain.quitanda.client.dao;

import android.content.Context;

import st.domain.support.android.sqlite.LiteDataBase;
import st.domain.quitanda.client.model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import st.domain.quitanda.client.references.RData;

/**
 * Created by xdata on 7/26/16.
 */
public class DaoUser extends LiteDataBase
{
    public DaoUser(Context context)
    {
        super(context, RData.DATABASE_NAME, RData.DATABASE_VERSION);
    }

    /**
     * Obter o utilizador logado
     * @return
     */
    public User getLogedUser()
    {
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.VER_CURRENT_LOGIN);
        execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
        end();
        if(result.size()>0)
        {
            int id = (int) result.get(0).get(RData.USER_ID);
            String name = result.get(0).get(RData.USER_NAME).toString();
            String surname = result.get(0).get(RData.USER_SURNAME).toString();
            String accessName = result.get(0).get(RData.USER_ACCESSNAME).toString();
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
        daoUser.select(RData.ALL)
                .from(RData.VER_CURRENT_LOGIN);
        daoUser.execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = daoUser.getSelectResult();
        daoUser.end();
        if(result.size()>0)
        {
            int id = (int) result.get(0).get(RData.USER_ID);
            String name = result.get(0).get(RData.USER_NAME).toString();
            String surname = result.get(0).get(RData.USER_SURNAME).toString();
            String accessName = result.get(0).get(RData.USER_ACCESSNAME).toString();
            User logado = new User(id, name, surname, accessName);
            return  logado;
        }

        return null;
    }
}
