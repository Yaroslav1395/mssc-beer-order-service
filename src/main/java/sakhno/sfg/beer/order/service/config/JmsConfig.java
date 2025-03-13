package sakhno.sfg.beer.order.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String MY_QUEUE = "MY_HELLO_WORLD";
    public static final String MY_SEND_RCV_QUEUE = "MY_SEND_RCV_QUEUE";

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
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
