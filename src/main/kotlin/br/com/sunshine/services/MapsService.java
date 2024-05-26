package br.com.sunshine.services;

import br.com.sunshine.model.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapsService {

    @Value("${google.maps.key}")
    private String key;

    private Double unit = 3.0;

    private WebClient http = WebClient.create();

    public Map<String, Object> findDistaceToAddress(String origin, String destination) {
        String url = String.format("https://maps.googleapis.com/maps/api/directions/xml?origin=%s&destination=%s&key=%s", origin, destination, key);
        String res = http.get().uri(url).retrieve().bodyToMono(String.class).block();

        XmlMapper mapper = new XmlMapper();
        Map<String, Object> map = new HashMap<>();

        try {

            var leg = mapper.readTree(res).path("route").path("leg");

            map.put("distance", leg.path("distance").path("value").asInt());
            map.put("distance_text", leg.path("distance").path("text").asText());
            map.put("end_address", leg.path("end_address"));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    public Map<String, Object> getInformationDelivery(Address address_, Address address) {
        var resultInformation = new HashMap<String, Object>();

        var result = findDistaceToAddress(address_.getStreet(), address.getZipCode());

        var value = ((int) result.get("distance") > 2000) ? -1 : ((( (int) result.get("distance")) * unit) / 1000);
//        var value = switch (establishment.getDeliveryCharge().getTypeDelivery()) {
//            case FIXA -> {
//                Taxa taxaMaior = establishment.getDeliveryCharge().getTaxas().get(0);
//
//                if (taxaMaior != null) {
//                    for (Taxa taxa : establishment.getDeliveryCharge().getTaxas()) {
//                        if (taxa.getDistance() > taxaMaior.getDistance()) {
//                            taxaMaior = taxa;
//                        }
//                    }
//                }
//
//                if (taxaMaior == null) {
//                    yield -1;
//                } else {
//                    if (establishment.getDeliveryCharge().getDistanceLimit() <= (int) result.get("distance")) {
//                        yield taxaMaior.getPrice();
//                    } else {
//                        yield -1;
//                    }
//
//                }
//            }
//            case QUILOMETRAGEM -> {
//                if ((int) result.get("distance") > establishment.getDeliveryCharge().getDistanceLimit()) {
//                    yield -1;
//                } else {
//                    yield (((int) result.get("distance")) * establishment.getDeliveryCharge().getUnit()) / 1000;
//                }
//            }
//            case SEDEX -> {
//                yield 0;
//            }
//        };

        System.out.println(result);
        resultInformation.put("valueNotFormatted", value);
        resultInformation.put("value", String.format("R$ %.2f", value));
        resultInformation.put("result", result);


        return resultInformation;
    }
}
