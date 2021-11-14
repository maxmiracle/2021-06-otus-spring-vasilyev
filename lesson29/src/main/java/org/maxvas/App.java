package org.maxvas;

import org.apache.commons.lang3.RandomUtils;
import org.maxvas.domain.Food;
import org.maxvas.domain.OrderItem;
import org.maxvas.service.KitchenService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@IntegrationComponentScan
@SuppressWarnings({"resource", "Duplicates", "InfiniteLoopStatement"})
@ComponentScan
@Configuration
@EnableIntegration
public class App {
    private static final String[] BAR_MENU = {"coffee", "tea", "smoothie", "whiskey", "beer", "cola", "water"};
    private static final String[] KITCHEN_MENU = {"salad", "soup", "pizza", "pasta", "borsch", "cheesecake", "tiramisu"};

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        // here we works with cafe using interface
        Cafe cafe = ctx.getBean(Cafe.class);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        while (true) {
            Thread.sleep(7000);

            pool.execute(() -> {
                Collection<OrderItem> items = generateOrderItems();
                System.out.println("+++New orderItems+++: " +
                        items.stream().map(OrderItem::getItemName)
                                .collect(Collectors.joining(",")));
                Collection<Food> food = cafe.process(items);
                System.out.println("!!!Ready food!!!: " + food.stream()
                        .map(Food::getName)
                        .collect(Collectors.joining(",")));
            });
        }
    }

    private static OrderItem generateOrderItem() {
        if (RandomUtils.nextBoolean()) {
            return new OrderItem(BAR_MENU[RandomUtils.nextInt(0, BAR_MENU.length)]);
        } else {
            return new OrderItem(KITCHEN_MENU[RandomUtils.nextInt(0, KITCHEN_MENU.length)]);
        }
    }

    private static Collection<OrderItem> generateOrderItems() {
        List<OrderItem> items = IntStream.range(0, RandomUtils.nextInt(1, 5))
                .mapToObj(i -> generateOrderItem()).collect(Collectors.toList());
        return items;
    }

    @Bean
    public QueueChannel itemsChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel foodChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public IntegrationFlow cafeFlow(KitchenService kitchenService) {
        return IntegrationFlows.from("itemsChannel")
                .split()
                .<OrderItem, Boolean>route(orderItem -> kitchenService.isKitchenItem(orderItem),
                        mapping -> mapping
                                .subFlowMapping(true, sf -> sf
                                        .channel("kitchenOrders")
                                        .handle("kitchenService", "cook"))
                                .subFlowMapping(false, sf -> sf
                                        .channel("BarOrders")
                                        .handle("barService", "cook")))
                .aggregate()
                .channel("foodChannel")
                .get();
    }
}
