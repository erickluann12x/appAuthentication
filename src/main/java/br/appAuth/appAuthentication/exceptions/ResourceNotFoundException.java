package br.appAuth.appAuthentication.exceptions;

public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
}
