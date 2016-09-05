package com.st.ggviario.client.model.builders;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

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
     * @return Instance of T created
     */
    public abstract T build();

    /**
     * Build new Object of type by any XML
     * @param xml
     * @return
     */
    public @Nullable T buildFromXML(String xml)
    {
        XStream xStream = new XStream();
        onPrepareXStream(xStream);
        return (T) xStream.fromXML(xml);
    }

    /**
     * Converte any instace of type in xml
     * @param instance the instance
     * @return created xml
     */
    public String toXml(@NonNull T instance)
    {
        XStream xStream = new XStream();

        onPrepareXStream(xStream);
        String xml =  xStream.toXML(instance);
        return xml;
    }

    protected void  onPrepareXStream(XStream xstream){
        xstream.alias(this.rootClass.getSimpleName(), this.rootClass);
    }

    public  String listAsXml(ArrayList<T> list) {
        XStream xStream = new XStream();
        this.onPrepareXStream(xStream);
        String xml = xStream.toXML(list);
        return xml;
    }

    public List<T> listOfXml(String xml)
    {
        XStream xStream = new XStream();
        this.onPrepareXStream(xStream);
        List<T> listInstances = (List<T>) xStream.fromXML(xml);
        return listInstances;
    }
}
