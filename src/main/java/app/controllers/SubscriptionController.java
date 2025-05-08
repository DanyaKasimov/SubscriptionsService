package app.controllers;

import app.api.SubscriptionApi;
import app.dto.response.SubscriptionDto;
import app.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionApi {

    private final SubscriptionService subscriptionService;

    @Override
    public List<SubscriptionDto> topSubs() {
        log.info("Поступил запрос на получение топ 3 подписок.");

        return subscriptionService.getTopSubs();
    }
}
