/*package com.microservices.restaurantservice.service;

import com.microservices.restaurantservice.dao.RestaurantOrderDAO;
import com.microservices.restaurantservice.dto.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantOrderDAO orderDAO;

    public String greeting() {
        return "Welcome to Swiggy Restaurant service";
    }

    public OrderResponseDTO getOrder(String orderId) {
        return orderDAO.getOrders(orderId);
    }
} */

package com.microservices.restaurantservice.service;

import com.microservices.restaurantservice.dto.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class RestaurantService {

    @Autowired
    private RestTemplate restTemplate;

    public String greeting() {
        return "Welcome to Restaurant Service";
    }

    public OrderResponseDTO getOrder(String orderId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<OrderResponseDTO> response = restTemplate.exchange(
            "http://order-service/api/orders/" + orderId,
            HttpMethod.GET,
            entity,
            OrderResponseDTO.class
        );

        return response.getBody();
    }
}

