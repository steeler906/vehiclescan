package mappers;

/**
 *
 * @author DarylD
 */
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;


/**
 *This class installs the Provider and "Provides" the ability for Jackson JSON parser
 * to use JAXB annotation. Without this class, for example, @XmlTransient was still
 * showing in the json. This is not affect XML only JSON. 
 * 
 * @author DarylD
 */
@Provider
public class JacksonJAXBAnnotationMapper implements ContextResolver<ObjectMapper> {

    //final ObjectMapper defaultObjectMapper;
    final ObjectMapper combinedObjectMapper;

    public JacksonJAXBAnnotationMapper() {
        //defaultObjectMapper = createDefaultMapper();
        combinedObjectMapper = createCombinedObjectMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {

        //if (type == CombinedAnnotationBean.class) {
            return combinedObjectMapper;
        //} else {
        //    return defaultObjectMapper;
        //}
    }

    private static ObjectMapper createCombinedObjectMapper() {

        AnnotationIntrospector combinedIntrospector = createJaxbJacksonAnnotationIntrospector();
        ObjectMapper result = new ObjectMapper();
        
        // Deserialization Config
        result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        result.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        
        //Serialization Config
        result.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
//        result.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        //install introspectors
        result.setAnnotationIntrospector(combinedIntrospector);
//        result.getDeserializationConfig().setAnnotationIntrospector(combinedIntrospector);
//        result.getSerializationConfig().setAnnotationIntrospector(combinedIntrospector);

        return result;
    }


//    private static ObjectMapper createDefaultMapper() {
//
//        ObjectMapper result = new ObjectMapper();
//        result.configure(Feature.INDENT_OUTPUT, true);
//
//        return result;
//    }


    private static AnnotationIntrospector createJaxbJacksonAnnotationIntrospector() {

        AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();

        return new AnnotationIntrospectorPair(jaxbIntrospector, jacksonIntrospector);
    }
}