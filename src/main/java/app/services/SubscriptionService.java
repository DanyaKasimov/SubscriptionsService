package app.services;

import app.dto.request.AddSubscriptionDto;
import app.dto.response.SubscriptionDto;
import app.model.Subscription;

import java.util.List;


public interface SubscriptionService {

    SubscriptionDto addSubscription(Long userId, AddSubscriptionDto dto);

    List<SubscriptionDto> getList(Long userId);

    void deleteSubscription(Long userId, Long subId);

    List<SubscriptionDto> getTopSubs();
}
