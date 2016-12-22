package st.domain.quitanda.client.dao;

import android.content.Context;

import st.domain.support.android.sqlite.LiteDataBase;
import st.domain.quitanda.client.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import st.domain.quitanda.client.references.RData;

/**
 * Created by xdata on 7/26/16.
 */
public class DaoObject extends LiteDataBase
{
    private final Context context;

    public DaoObject(Context context)
    {
        super(context, RData.DATABASE_NAME, RData.DATABASE_VERSION);
        this.context = context;
    }

    public String getObjectId(final String name, final TypeObject typeObject)
    {

        DaoUser daoUser = new DaoUser(this.context);
        User user = daoUser.getLogedUser();
        if(user == null)
            throw new Error("Nehum utilizador na seção");

        if(name==null || typeObject == null)
            return null;

        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.T_OBJECT)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row)
                    {
                        return ((int) row.get(RData.OBJ_TOBJ_ID))== typeObject.getDataBaseType()
                                && row.get(RData.OBJ_DESC).toString().equalsIgnoreCase(name);
                    }
                })
                .limit(1);
        execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
        end();


        if(result.size()>0)
            return result.get(0).get(RData.OBJ_ID).toString();
        else
        {

            begin(Operaction.INSERT);

            insertInto(RData.T_OBJECT, RData.OBJ_PREVIEWID)
                    .columns(RData.OBJ_DESC, RData.OBJ_TOBJ_ID, RData.OBJ_USER_ID)
                    .values(name, typeObject.dataBaseType, user.getId())
                    .returning(null);
            execute();
            LinkedHashMap<CharSequence, Object> insertResult = getInserttResult();
            String id =  "~~"+insertResult.get(RData.OBJ_PREVIEWID);
            end();
            return id;
        }
    }

    public String valueOf(final String idObject)
    {
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.T_OBJECT)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row)
                    {
                        String id = row.get(RData.OBJ_ID).toString();
                        String previewId = row.get(RData.OBJ_PREVIEWID).toString();
                        String aux = idObject.replace("*", "**");
                        if(idObject.substring(0, 2).equals("~~")
                                || idObject.substring(0, 2).equals("**"))
                            return idObject.substring(2, idObject.length()).equals(previewId);
                        else return idObject.equals(id);
                    }
                });
        execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
        end();
        if(result.size()>0)
            return result.get(0).get(RData.OBJ_DESC).toString();
        else return null;
    }


    public enum TypeObject
    {
        RESIDENCIA(1),
        TYPE_DOCUMENT(3);

        private final int dataBaseType;

        TypeObject(int dataBaseType) {
            this.dataBaseType = dataBaseType;
        }

        public int getDataBaseType() {
            return dataBaseType;
        }
    }
}
