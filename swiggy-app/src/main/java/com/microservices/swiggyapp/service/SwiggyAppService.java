/*package com.microservices.swiggyapp.service;

import com.microservices.swiggyapp.client.RestaurantServiceClient;
import com.microservices.swiggyapp.dto.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwiggyAppService {

    @Autowired
    private RestaurantServiceClient restaurantServiceClient;

    public String greeting() {
        return "Welcome to Swiggy App Service";
    }

    public OrderResponseDTO checkOrderStatus(String orderId) {
        return restaurantServiceClient.fetchOrderStatus(orderId);
    }
} */

package com.microservices.swiggyapp.service;

import com.microservices.swiggyapp.client.RestaurantServiceClient;
import com.microservices.swiggyapp.dto.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SwiggyAppService {

    @Autowired
    private RestaurantServiceClient restaurantServiceClient;

    public String greeting() {
        return "Welcome to Swiggy App Service";
    }

    public OrderResponseDTO checkOrderStatus(String orderId, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return restaurantServiceClient.fetchOrderStatus(orderId, token);
    }
}

