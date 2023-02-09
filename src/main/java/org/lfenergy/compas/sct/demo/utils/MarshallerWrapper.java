package org.lfenergy.compas.sct.demo.utils;

import jakarta.enterprise.context.ApplicationScoped;
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
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.stream.Stream;

@ApplicationScoped
public class MarshallerWrapper {

    private final Marshaller marshaller;

    MarshallerWrapper() throws JAXBException, ParserConfigurationException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance("org.lfenergy.compas.scl2007b4.model");
        this.marshaller = jaxbContext.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        this.marshaller.setSchema(loadSchema());
    }

    public String marshall(final Object obj) {
        try {
            StringWriter sw = new StringWriter();
            Result result = new StreamResult(sw);
            this.marshaller.marshal(obj, result);
            return sw.toString();
        } catch (JAXBException exp) {
            String message = String.format("Error marshalling the Class: %s", exp.getLinkedException());
            throw new CompasException(CompasErrorCode.MARSHAL_ERROR_CODE, message);
        }
    }

    private Schema loadSchema() throws SAXException, ParserConfigurationException {
        XMLReader xmlReader = SAXParserFactory.newDefaultNSInstance().newSAXParser().getXMLReader();
        Source[] schemaSources = Stream.of("/xsd/SCL2007B4/SCL.xsd", "/xsd/SCL_CoMPAS.xsd")
                .map(xsdPath -> toSchemaSource(xsdPath, xmlReader))
                .toArray(Source[]::new);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return schemaFactory.newSchema(schemaSources);
    }

    private Source toSchemaSource(String path, XMLReader xmlReader) {
        URL url;
        try {
            url = IOUtils.resourceToURL(path);
        } catch (IOException e) {
            var message = "Error loading XML schema : " + path;
            throw new CompasException(CompasErrorCode.CREATION_ERROR_CODE, message);
        }
        InputSource inputSource = new InputSource(url.toString());
        return new SAXSource(xmlReader, inputSource);
    }

}
