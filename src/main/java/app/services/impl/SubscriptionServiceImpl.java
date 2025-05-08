package app.services.impl;

import app.dto.request.AddSubscriptionDto;
import app.dto.response.SubscriptionDto;
import app.exceptions.InvalidDataException;
import app.mappers.SubscriptionMapper;
import app.model.Subscription;
import app.model.User;
import app.model.UserSubscription;
import app.model.UserSubscriptionId;
import app.repositories.SubscriptionRepository;
import app.repositories.UserSubscriptionRepository;
import app.services.SubscriptionService;
import app.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserService userService;

    private final SubscriptionRepository subscriptionRepository;

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionDto addSubscription(final Long userId, final AddSubscriptionDto dto) {
        User user = userService.findById(userId);

        Subscription subscription = subscriptionRepository.findByLink(dto.getLink())
                .orElseGet(() -> {
                    Subscription newSub = Subscription.builder()
                            .link(dto.getLink())
                            .build();
                    return subscriptionRepository.save(newSub);
                });

        UserSubscriptionId id = new UserSubscriptionId(user.getId(), subscription.getId());
        Optional<UserSubscription> existing = userSubscriptionRepository.findById(id);
        if (existing.isPresent()) {
            throw new InvalidDataException("Пользователь уже подписан на данный ресурс.");
        }

        UserSubscription userSubscription = UserSubscription.builder()
                .id(id)
                .user(user)
                .subscription(subscription)
                .build();

        userSubscriptionRepository.save(userSubscription);

        return subscriptionMapper.toDto(subscription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionDto> getList(final Long userId) {
        userService.getUser(userId);

        return userSubscriptionRepository.findByUserId(userId).stream()
                .map(userSubscription -> {
                    Subscription sub = userSubscription.getSubscription();
                    return new SubscriptionDto(sub.getId(), sub.getLink());
                })
                .toList();
    }

    @Override
    @Transactional
    public void deleteSubscription(final Long userId, final Long subId) {
        userService.getUser(userId);

        UserSubscriptionId id = new UserSubscriptionId(userId, subId);
        UserSubscription subscription = userSubscriptionRepository.findById(id)
                .orElseThrow(() -> new InvalidDataException("Подписка у пользователя не найдена."));

        userSubscriptionRepository.delete(subscription);

        boolean hasOtherLinks = userSubscriptionRepository.existsBySubscriptionId(subId);
        if (!hasOtherLinks) {
            subscriptionRepository.deleteById(subId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionDto> getTopSubs() {
        List<Subscription> subscriptions = userSubscriptionRepository.findTopSubscriptions();
        return subscriptionMapper.toDtoList(subscriptions);
    }
}
