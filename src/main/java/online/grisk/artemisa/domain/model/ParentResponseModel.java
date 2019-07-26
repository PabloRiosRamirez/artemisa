package online.grisk.artemisa.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ParentResponseModel {

    private UUID uuid;
    private HttpStatus status;
    private String message;
    private Object response;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date date;

    public static Message<ParentResponseModel> toMessage(UUID uuid, HttpStatus status, String message, Object response, Date date){
        return MessageBuilder.withPayload(new ParentResponseModel(uuid, status, message, response, date)).build();
    }
    public static ResponseEntity<?> toResponseEntity(UUID uuid, HttpStatus status, String message, Object response, Date date, HttpStatus httpStatus){
        return new ResponseEntity<>(new ParentResponseModel(uuid, status, message, response, date), httpStatus);
    }
}