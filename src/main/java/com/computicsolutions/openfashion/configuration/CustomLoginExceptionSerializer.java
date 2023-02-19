package com.computicsolutions.openfashion.configuration;

import com.computicsolutions.openfashion.enums.ErrorResponseStatusType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This exception serializer will override bad credentials exception response with custom error response
 */
@Component
public class CustomLoginExceptionSerializer extends StdSerializer<CustomLoginException> {

    private static final String ERROR_CODE = "errorCode";
    private static final String STATUS = "status";
    private static final String ERROR_STATUS = "ERROR";
    private static final String MESSAGE = "message";
    private static final String DISPLAY_MESSAGE = "displayMessage";
    private static final String ERROR_MESSAGE = "Oops!! Something went wrong. Please try again.";

    public CustomLoginExceptionSerializer() {
        super(CustomLoginException.class);
    }

    @Override
    public void serialize(CustomLoginException exception, JsonGenerator jsonGenerator, SerializerProvider
            serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(STATUS, ERROR_STATUS);
        jsonGenerator.writeStringField(MESSAGE, ErrorResponseStatusType.INVALID_LOGIN.getMessage());
        jsonGenerator.writeStringField(DISPLAY_MESSAGE, ERROR_MESSAGE);
        jsonGenerator.writeNumberField(ERROR_CODE, ErrorResponseStatusType.INVALID_LOGIN.getCode());
        jsonGenerator.writeEndObject();
    }
}
