/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.xtesoft.webservices.clientes.beans;

import com.xtesoft.webservices.clientes.Personas;
import com.xtesoft.webservices.clientes.PersonasDTO;
import com.xtesoft.webservices.clientes.PersonasWebService;
import com.xtesoft.webservices.clientes.PersonasWebService_Service;
import com.xtesoft.webservices.clientes.dtos.PersDTO;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author xtecuan
 */
@Named(value = "indexBean")
@ViewScoped
public class IndexBean implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/blitzthinkw2_8080/webservicepersonas/PersonasWebService.wsdl")
    private PersonasWebService_Service service;
    private PersonasWebService port;
    private PersDTO persona;
    private List<SelectItem> sexoOptions;
    private List<Personas> personas;
    private Personas selectedPersona;

    @PostConstruct
    public void init() {
        persona = new PersDTO();
        sexoOptions = Arrays.asList(new SelectItem("M", "Masculino"), new SelectItem("F", "Femenino"));
        try { // Call Web Service Operation
            port = service.getPersonasWebServicePort();
            personas = port.findAll();
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            System.err.println("Error inicializando el servlet: " + ex);
        }
        selectedPersona = new Personas();
    }

    /**
     * Creates a new instance of IndexBean
     */
    public IndexBean() {
    }

    public PersDTO getPersona() {
        return persona;
    }

    public void setPersona(PersDTO persona) {
        this.persona = persona;
    }

    public List<SelectItem> getSexoOptions() {
        return sexoOptions;
    }

    public void setSexoOptions(List<SelectItem> sexoOptions) {
        this.sexoOptions = sexoOptions;
    }

    public void guardarPersona() {
        if (this.getPersona() != null) {
            port.save(fromFormularioDTO());
            this.persona = new PersDTO();
            this.personas = port.findAll();
            FacesContext.getCurrentInstance().addMessage("Exito:", new FacesMessage("Se agrego una persona"));
        }
    }

    public PersonasDTO fromFormularioDTO() {
        PersonasDTO p = new PersonasDTO();
        p.setApellidos(this.persona.getApellidos());
        p.setNombres(this.persona.getNombres());
        p.setEmail(this.persona.getEmail());
        p.setSexo(this.persona.getSexo());
        p.setFechaNacimiento(fromDate2XMLGregCal(this.persona.getFechaNacimiento()));
        return p;
    }

    public XMLGregorianCalendar fromDate2XMLGregCal(Date d) {
        XMLGregorianCalendar xmlDate = null;
        gc.setTime(d);
        try {
            xmlDate = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlDate;
    }

    private static GregorianCalendar gc = new GregorianCalendar();

    public List<Personas> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Personas> personas) {
        this.personas = personas;
    }

    public Personas getSelectedPersona() {
        return selectedPersona;
    }

    public void setSelectedPersona(Personas selectedPersona) {
        this.selectedPersona = selectedPersona;
    }

    public void deletePersona() {
        if (this.selectedPersona != null) {
            port.delete(selectedPersona.getIdPersona());
            this.selectedPersona = null;
            this.personas = port.findAll();
            FacesContext.getCurrentInstance().addMessage("Exito:", new FacesMessage("Se borro una persona"));
        }
    }

}
