package br.appAuth.appAuthentication.security;

import lombok.Builder;

import java.util.List;

@Builder
public record JWTUserData(Long userid, String email, List<String> roles) {
}
