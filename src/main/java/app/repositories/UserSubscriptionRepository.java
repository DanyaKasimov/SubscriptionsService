package app.repositories;

import app.model.Subscription;
import app.model.UserSubscription;
import app.model.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {

    boolean existsBySubscriptionId(Long subId);

    @Query("""
            SELECT us.subscription
            FROM UserSubscription us
            GROUP BY us.subscription
            ORDER BY COUNT(us.user) DESC
            LIMIT 3
            """)
    List<Subscription> findTopSubscriptions();

    @Query("SELECT us FROM UserSubscription us JOIN FETCH us.subscription WHERE us.user.id = :userId")
    List<UserSubscription> findByUserId(@Param("userId") Long userId);

    @Query("SELECT us.subscription.id FROM UserSubscription us WHERE us.user.id = :userId")
    List<Long> findSubscriptionIdsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM UserSubscription us WHERE us.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
