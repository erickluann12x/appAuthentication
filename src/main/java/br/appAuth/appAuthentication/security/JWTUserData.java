package br.appAuth.appAuthentication.security;

import lombok.Builder;

@Builder
public record JWTUserData(Long userid, String email) {
}
