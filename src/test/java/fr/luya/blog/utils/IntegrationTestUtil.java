package fr.luya.blog.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.MediaType;

public class IntegrationTestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static byte[] convertObjectToJsonBytes(Object object, JsonSerialize.Inclusion inclusion) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(inclusion);
        return mapper.writeValueAsBytes(object);
    }

    public static <T> Object convertJsonBytesToObject(byte[] src, Class<T> classe, JsonSerialize.Inclusion inclusion) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(inclusion);
        return mapper.readValue(src, classe);
    }
}