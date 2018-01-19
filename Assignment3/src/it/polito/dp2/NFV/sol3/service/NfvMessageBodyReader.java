package it.polito.dp2.NFV.sol3.service;
// This validator performs JAXB unmarshalling with validation
// against a schema
// This version is thread-safe when using the JAXB reference implementation
// (for which the documentation specifies JAXBContext is thread-safe while
// Marshaller and Unmarshaller are not)
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.eclipse.persistence.jaxb.JAXBIntrospector;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import it.polito.dp2.NFV.sol3.service.model.*;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class NfvMessageBodyReader implements MessageBodyReader<Object> {
	private final String jaxbPackage = "it.polito.dp2.NFV.sol3.service.model";
	private Logger logger;
	private JAXBContext jaxbContext;
	private Schema schema;
	
	public NfvMessageBodyReader() {
		logger = Logger.getLogger(NfvMessageBodyReader.class.getName());

		try {				
			InputStream schemaStream = NfvMessageBodyReader.class.getResourceAsStream("/NfvDeployer.xsd");
			if (schemaStream == null) {
				logger.log(Level.SEVERE, "xml schema file not found.");
				throw new IOException();
			}
            jaxbContext = JAXBContext.newInstance( jaxbPackage );
            SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
            schema = sf.newSchema(new StreamSource(schemaStream));

            logger.log(Level.INFO, "LinkMessageBodyReader initialized successfully");
		} catch (SAXException | JAXBException | IOException se) {
			logger.log(Level.SEVERE, "Error parsing xml directory file. Service will not work properly.", se);
		}
	}
	
    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
    	Class<?>[] types = {Link.class, NewNffg.class, Node.class};
        for (Class<?> c : types) {
            if (type == c) {
               return true;
            }
         }
         return false;
    }

    @Override
    public Object readFrom(Class type, Type genericType, Annotation[] annotations,
                         MediaType mediaType, MultivaluedMap httpHeaders,
                         InputStream entityStream) throws IOException, WebApplicationException {

    	try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(jaxbPackage).createUnmarshaller();
            unmarshaller.setSchema(schema);
			return JAXBIntrospector.getValue(unmarshaller.unmarshal(entityStream));
		} catch (JAXBException ex) {
			logger.log(Level.WARNING, "Request body validation error.", ex);
			Throwable linked = ex.getLinkedException();
			String validationErrorMessage = "Request body validation error";
			if (linked != null && linked instanceof SAXParseException)
				validationErrorMessage += ": " + linked.getMessage();
			BadRequestException bre = new BadRequestException("Request body validation error");
			Response response = Response.fromResponse(bre.getResponse()).entity(validationErrorMessage).build();
			throw new BadRequestException("Request body validation error", response);
		}
    }

}