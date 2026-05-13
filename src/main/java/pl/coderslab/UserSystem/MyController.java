package pl.coderslab.UserSystem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MyController {

    @Autowired
    private DataProducer producer;

    @PostMapping("/send-user-data")
    public ResponseEntity<String> sendData(@RequestBody UserData userData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonData = mapper.writeValueAsString(userData);
            producer.sendMessage(jsonData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Dane zostały wysłane");
    }

}
