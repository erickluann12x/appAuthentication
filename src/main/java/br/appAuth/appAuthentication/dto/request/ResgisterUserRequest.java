package br.appAuth.appAuthentication.dto.request;

import br.appAuth.appAuthentication.entities.Role;
import jakarta.validation.constraints.NotEmpty;

public record ResgisterUserRequest(@NotEmpty(message = "Nome é obrigatório")String name,
                                   @NotEmpty(message = "E-mail é obrigatório") String email,
                                   @NotEmpty(message = "Senha é obrigatório")String password,
                                   Role role) {
}
