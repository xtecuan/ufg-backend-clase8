/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xtesoft.webservices.clientes.beans.converters;

import com.xtesoft.webservices.clientes.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author xtecuan
 */
@Named(value = "dateConverter")
public class DateConverter implements Converter {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            return sdf.parse(string);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        XMLGregorianCalendar d = (XMLGregorianCalendar) t;
        return sdf.format(DateUtils.toDate(d));

    }

}
