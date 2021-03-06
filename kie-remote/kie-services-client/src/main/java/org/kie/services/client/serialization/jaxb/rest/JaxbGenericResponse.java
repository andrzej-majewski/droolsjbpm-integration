package org.kie.services.client.serialization.jaxb.rest;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import org.jboss.resteasy.spi.BadRequestException;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbGenericResponse extends AbstractJaxbResponse {

    @XmlElement
    @XmlSchemaType(name="string")
    private String error;
    
    @XmlElement
    @XmlSchemaType(name="string")
    private String stackTrace;
    
    public JaxbGenericResponse() { 
       // Default constructor 
    }
    
    public JaxbGenericResponse(HttpServletRequest request ) { 
        super(request);
    }
    
    /**
     * Exception constructor
     * @param request
     * @param e
     */
    public JaxbGenericResponse(HttpServletRequest request, Exception e) {
        super(request);
        this.error = e.getMessage();
        if( ! (e instanceof BadRequestException) ) { 
            this.status = JaxbRequestStatus.FAILURE;
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            e.printStackTrace(writer);
            stackTrace = stringWriter.toString();
        } else { 
            this.status = JaxbRequestStatus.BAD_REQUEST;
        }
    }
    
    public static String convertStackTraceToString(Throwable t) { 
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        return stringWriter.toString();
    }
    
    public String prettyPrint() throws JAXBException {
        StringWriter writer = new StringWriter();

        JAXBContext jc = JAXBContext.newInstance(this.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(this, writer);
        return writer.toString();
    }
    
    public JaxbRequestStatus getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public void setStatus(JaxbRequestStatus status) {
        this.status = status;
    }

}
