package sakhno.sfg.beer.order.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import sakhno.sfg.beer.order.service.web.model.events.AllocateOrderRequest;
import sakhno.sfg.beer.order.service.web.model.events.AllocateOrderResult;
import sakhno.sfg.beer.order.service.web.model.events.ValidateOrderRequest;
import sakhno.sfg.beer.order.service.web.model.events.ValidateOrderResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmsConfig {

    public static final String VALIDATE_ORDER_QUEUE = "VALIDATE_ORDER_QUEUE";
    public static final String VALIDATE_ORDER_RESPONSE_QUEUE = "VALIDATE_ORDER_RESPONSE_QUEUE";
    public static final String ALLOCATION_ORDER_QUEUE = "ALLOCATION_ORDER_QUEUE";
    public static final String ALLOCATION_ORDER_RESPONSE_QUEUE = "ALLOCATION_ORDER_RESPONSE_QUEUE";
    public static final String ALLOCATION_FAILURE_QUEUE = "ALLOCATION_FAILURE_QUEUE";
    public static final String DEALLOCATE_ORDER_QUEUE = "DEALLOCATE_ORDER_QUEUE";

    /**
     * Данный метод создает бин, который будет использоваться как конвертер сообщений при работе с JMS.
     * MappingJackson2MessageConverter — это конвертер сообщений, который использует Jackson для преобразования
     * объектов в JSON и обратно.
     * setTargetType(MessageType.TEXT) — указывает, что сообщения будут передаваться в текстовом формате (обычно
     * это JSON).
     * setTypeIdPropertyName("_type") — добавляет в JSON-объект специальное свойство _type, которое используется для
     * определения типа объекта при десериализации.
     * @return - конвертер сообщений
     */
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("ValidateOrderRequest", ValidateOrderRequest.class);
        typeIdMappings.put("ValidateOrderResult", ValidateOrderResult.class);
        typeIdMappings.put("AllocateOrderRequest", AllocateOrderRequest.class);
        typeIdMappings.put("AllocateOrderResult", AllocateOrderResult.class);
        converter.setTypeIdMappings(typeIdMappings);
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
