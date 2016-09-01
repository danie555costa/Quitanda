package com.st.ggviario.client.model.builders;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */


import android.util.Log;

import com.thoughtworks.xstream.XStream;
/**
 *
 * @param <T> O tipo de instacia que sera criada pelo Builder
 */
public abstract class Builder  <T>
{
    private Class<T> rootClass;

    /**
     *
     * @param rootClass {T.class}
     */
    public Builder(Class<T> rootClass) {
        this.rootClass = rootClass;
    }

    /**
     * Build new Object of type
     * @return
     */
    public abstract T build();

    /**
     * Build new Object of type by any XML
     * @param xml
     * @return
     */
    public T buildFromXML(String xml)
    {
        Log.i("DBA:APP.TEST", xml);
        XStream xStream = new XStream();
        onPrepareXStream(xStream);
        return (T) xStream.fromXML(xml);
    }

    /**
     * Converte any instace of type in xml
     * @param instance
     * @return
     */
    public String toXml(T instance)
    {
        XStream xStream = new XStream();

        onPrepareXStream(xStream);
        String xml =  xStream.toXML(instance);
        Log.i("DBA:APP.TEST", xml);
        return xml;
    }

    protected void  onPrepareXStream(XStream xstream){
//        xstream.alias(this.rootClass.getSimpleName(), this.rootClass.getClass());
    }
}
