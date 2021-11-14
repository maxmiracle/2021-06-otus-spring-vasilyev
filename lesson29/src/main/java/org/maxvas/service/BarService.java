package org.maxvas.service;

import org.maxvas.domain.Cooker;
import org.springframework.stereotype.Service;
import org.maxvas.domain.Food;
import org.maxvas.domain.OrderItem;

@Service
public class BarService {

    public Food cook(OrderItem orderItem) throws Exception {
        System.out.println("Bar cooking " + orderItem.getItemName());
        Thread.sleep(1000);
        System.out.println("Bar cooking " + orderItem.getItemName() + " done");
        return new Food(orderItem.getItemName(), Cooker.BAR);
    }
}
