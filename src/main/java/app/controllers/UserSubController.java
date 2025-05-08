package app.controllers;

import app.api.UserSubscriptionApi;
import app.dto.request.AddSubscriptionDto;
import app.dto.response.SubscriptionDto;
import app.model.Subscription;
import app.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserSubController implements UserSubscriptionApi {

    private final SubscriptionService subscriptionService;

    @Override
    public SubscriptionDto addSubscription(final Long id, final AddSubscriptionDto dto) {
        log.info("Поступил запрос на добавление подписки. UserID: {}, Link: {}", id, dto.getLink());

        return subscriptionService.addSubscription(id, dto);
    }

    @Override
    public List<SubscriptionDto> getSubscriptions(final Long id) {
        log.info("Поступил запрос на получение списка подписок пользователя. UserID: {}", id);

        return subscriptionService.getList(id);
    }

    @Override
    public void deleteSubscriptions(final Long id, final Long subId) {
        log.info("Поступил запрос на удаление подписки у пользователя. UserID: {}, SubId: {}", id, subId);

        subscriptionService.deleteSubscription(id, subId);
    }
}
