package org.lfenergy.compas.sct.demo.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.apache.commons.io.IOUtils;
import org.lfenergy.compas.core.commons.exception.CompasErrorCode;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

@Singleton
public class JaxbProducer {

    @Produces
    @ApplicationScoped
    public Unmarshaller unmarshaller() throws JAXBException, ParserConfigurationException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance("org.lfenergy.compas.scl2007b4.model");
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        // Setup schema validation
        Schema schema = loadSchema();
        jaxbUnmarshaller.setSchema(schema);
        return jaxbUnmarshaller ;
    }

    @Produces
    @ApplicationScoped
    public Marshaller marshaller() throws JAXBException, ParserConfigurationException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance("org.lfenergy.compas.scl2007b4.model");
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        // Setup schema validation
        Schema schema = loadSchema();
        jaxbMarshaller.setSchema(schema);
        return  jaxbMarshaller ;
    }

    private static Schema loadSchema() throws SAXException, ParserConfigurationException {
        XMLReader xmlReader = SAXParserFactory.newDefaultNSInstance().newSAXParser().getXMLReader();
        Source[] schemaSources = Stream.of("/xsd/SCL2007B4/SCL.xsd", "/xsd/SCL_CoMPAS.xsd")
                .map(xsdPath -> toSchemaSource(xsdPath, xmlReader))
                .toArray(Source[]::new);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return schemaFactory.newSchema(schemaSources);
    }

    private static Source toSchemaSource(String path, XMLReader xmlReader) {
        URL url;
        try {
            url = IOUtils.resourceToURL(path);
        } catch (IOException e) {
            var message = "Error loading XML schema : " + path;
//            log.error(message, e);
            throw new CompasException(CompasErrorCode.CREATION_ERROR_CODE, message);
        }
        InputSource inputSource = new InputSource(url.toString());
        return new SAXSource(xmlReader, inputSource);
    }

}
