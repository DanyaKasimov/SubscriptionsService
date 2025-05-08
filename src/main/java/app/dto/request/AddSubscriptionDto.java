package app.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddSubscriptionDto {

    @Size(min = 2, max = 100, message = "Длина ссылки должна быть от 2 до 100 символов.")
    private String link;
}
