package sakhno.sfg.beer.order.service.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Класс необходим для Mapstruct. Методы используются для преобразования дат из Timestamp в OffsetDateTime и наоборот
 */
@Component
public class DateMapper {

    /**
     * Метод позволяет преобразовать Timestamp в OffsetDateTime
     * @param ts - дата Timestamp
     * @return - дата OffsetDateTime
     */
    public OffsetDateTime asOffsetDateTime(Timestamp ts){
        if (ts != null){
            return OffsetDateTime.of(ts.toLocalDateTime().getYear(), ts.toLocalDateTime().getMonthValue(),
                    ts.toLocalDateTime().getDayOfMonth(), ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(),
                    ts.toLocalDateTime().getSecond(), ts.toLocalDateTime().getNano(), ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    /**
     * Метод позволяет преобразовать OffsetDateTime в Timestamp
     * @param offsetDateTime - дата OffsetDateTime
     * @return - дата Timestamp
     */
    public Timestamp asTimestamp(OffsetDateTime offsetDateTime){
        if(offsetDateTime != null) {
            return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        } else {
            return null;
        }
    }

}
