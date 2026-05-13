package pl.coderslab.JMSProjectMini;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    MessageSender sender;

    @GetMapping("/send")
    public String index(){
        MessageRepresentation message = new MessageRepresentation("Hello, world!", "John", 1);
        sender.sendJsonMessage(message);
        return "Wiadomość została wysłana do kolejki";
    }

}
