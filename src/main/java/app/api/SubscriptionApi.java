package app.api;

import app.dto.response.SubscriptionDto;
import app.model.Subscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/subscriptions")
@Tag(name = "Управление данными о подписках", description = "Методы для управления данными о подписках.")
public interface SubscriptionApi {

    @Operation(description = "Получение ТОП-3 популярных подписок.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Подписки получены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionDto.class)
                    )
            ),
    })
    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    List<SubscriptionDto> topSubs();
}