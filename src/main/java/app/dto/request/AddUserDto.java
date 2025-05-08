package app.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddUserDto {

    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов.")
    private String name;

    @Size(min = 2, max = 100, message = "Фамилия должна содержать от 2 до 100 символов.")
    private String lastname;

    @Email(message = "Неверный формат электронной почты.")
    @Size(max = 150, message = "Email не должен превышать 150 символов.")
    private String email;

    @NotNull(message = "Возраст обязателен.")
    @Min(value = 6, message = "Минимальный возраст — 6 лет.")
    @Max(value = 100, message = "Максимальный возраст — 100 лет.")
    private Integer age;
}
