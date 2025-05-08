package app.api;

import app.dto.request.AddSubscriptionDto;
import app.dto.response.SubscriptionDto;
import app.dto.response.ApiErrorResponse;
import app.model.Subscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/users/{id}/subscriptions")
@Tag(name = "Управление подписками пользователей", description = "Методы для управления подписками пользователей.")
public interface UserSubscriptionApi {

    @Operation(description = "Добавить подписку.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Подписка добавлена.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            ),
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    SubscriptionDto addSubscription(@PathVariable @Valid final Long id, @RequestBody @Valid final AddSubscriptionDto dto);

    @Operation(description = "Получение списка подписок пользователя.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список получен.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            ),
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<SubscriptionDto> getSubscriptions(@PathVariable @Valid final Long id);

    @Operation(description = "Получение списка подписок пользователя.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список получен."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь или подписка не найдены.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            ),
    })
    @DeleteMapping("/{sub_id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSubscriptions(@PathVariable @Valid final Long id, @PathVariable("sub_id") @Valid final Long subId);

}
