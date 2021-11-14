package org.maxvas;

import org.junit.jupiter.api.Test;
import org.maxvas.domain.Cooker;
import org.maxvas.domain.Food;
import org.maxvas.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {App.class})
public class CafeFlowTest {

    @Autowired
    private Cafe cafe;


    @Test
    public void prepareColaCheesecakeCoffe() {
        List<String> order = List.of("cola", "cheesecake", "coffee");
        List<OrderItem> items = order.stream().map(name -> new OrderItem(name)).collect(Collectors.toList());
        Collection<Food> foods = cafe.process(items);
        assertEquals(List.of(new Food("cola", Cooker.BAR),
                new Food("cheesecake", Cooker.KITCHEN),
                new Food("coffee", Cooker.BAR)), foods);
    }
}
