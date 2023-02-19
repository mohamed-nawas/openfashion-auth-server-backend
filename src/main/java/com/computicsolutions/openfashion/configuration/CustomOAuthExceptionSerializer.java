package com.computicsolutions.openfashion.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * This exception serializer will override the oauth exception responses with custom error responses
 */
@Component
public class CustomOAuthExceptionSerializer extends StdSerializer<CustomOAuthException> {

    private static final String ERROR_CODE = "errorCode";
    private static final String STATUS = "status";
    private static final String ERROR_STATUS = "ERROR";
    private static final String MESSAGE = "message";
    private static final String DISPLAY_MESSAGE = "displayMessage";
    private static final String ERROR_MESSAGE = "Oops!! Something went wrong. Please try again.";

    public CustomOAuthExceptionSerializer() {
        super(CustomOAuthException.class);
    }

    @Override
    public void serialize(CustomOAuthException exception, JsonGenerator jsonGenerator, SerializerProvider
            serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(STATUS, ERROR_STATUS);
        jsonGenerator.writeStringField(MESSAGE, exception.getMessage());
        jsonGenerator.writeStringField(DISPLAY_MESSAGE, ERROR_MESSAGE);
        jsonGenerator.writeNumberField(ERROR_CODE, 4999);
        if (exception.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : exception.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                jsonGenerator.writeStringField(key, value);
            }
        }
        jsonGenerator.writeEndObject();
    }
}
