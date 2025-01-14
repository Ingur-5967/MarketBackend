package ru.solomka.market.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditBioRequest {

    @Size(min = 5, max = 20)
    private final String username;

    @Email
    private final String email;
}
