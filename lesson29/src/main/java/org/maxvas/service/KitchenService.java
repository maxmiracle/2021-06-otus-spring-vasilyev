package org.maxvas.service;

import org.maxvas.domain.Cooker;
import org.springframework.stereotype.Service;
import org.maxvas.domain.Food;
import org.maxvas.domain.OrderItem;

import java.util.Arrays;

@Service
public class KitchenService {

    private static final String[] KITCHEN_MENU = {"salad", "soup", "pizza", "pasta", "borsch", "cheesecake", "tiramisu"};

    public Boolean isKitchenItem(OrderItem item)
    {
        return Arrays.stream(KITCHEN_MENU).anyMatch(kitchenItem -> kitchenItem.equals(item.getItemName()));
    }


    public Food cook(OrderItem orderItem) throws Exception {
        System.out.println("Kitchen cooking " + orderItem.getItemName());
        Thread.sleep(3000);
        System.out.println("Kitchen cooking " + orderItem.getItemName() + " done");
        return new Food(orderItem.getItemName(), Cooker.KITCHEN);
    }
}
