package edu.sharif.webproject.rabbit;

import edu.sharif.webproject.config.RabbitMQConfig;
import edu.sharif.webproject.country.CountryService;
import edu.sharif.webproject.country.entity.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiRequestListener {

    private final CountryService countryService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public CountryDto handleApiRequest(String name) {
        return countryService.getCountryByName(name);
    }
}
