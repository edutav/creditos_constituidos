package br.com.creditos.api.exception;

public class CreditoNaoEncontradoException extends Exception {
    public CreditoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
