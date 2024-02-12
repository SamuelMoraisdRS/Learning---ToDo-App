package com.samuelmorais.todosimple.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.context.properties.bind.validation.ValidationErrors;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
// import lombok.launch.PatchFixesHider.Val;


/** *
 * Classe que representa a descricao do erro enviado na response HTTP
 * A response HTTP será formada pelas descricoes dos erros de validação encontrados. Para melhorar a modularidade
 * do código, criaremos uma subclasse aninhada static (pertencente a todas as instancias da classe)
*/
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ErrorResponse {
    private final int status;   /// Status do erro, código numérico associado ao erro
    private final String message;   /// Mensagem associada ao erro
    private String stackTrace;    /// StackTrace é apilha de erros mostrada na tela
    private List<ValidationError> errors;   ///
    // Teremos um objeto para todas as instancias


    // List é uma interface!!
    @RequiredArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode 
    @JsonInclude(JsonInclude.Include.NON_NULL)  // inserirá aos arquivos JSON apenas os valores não nulos
    private static class ValidationError {
        /// Campo que não passou na validação (atributo que não conforma com as regras de negócio)
        private final String field;
        /// A mensagem que deve ser mostrada no log do erro
        private final String message;
    }
    public void addValidationError(String field, String message) {
        if (Objects.isNull(errors)) {
            // A interface list é implementada na classe ArrayList
            errors = new ArrayList<>();
        }
        // a response será formada por vários erros de validação para cada field
        errors.add(new ValidationError(field, message));
    }
    public String toJson() {
        return "{\"status\": " + getStatus() + ", " +
                "\"message\": \"" + getMessage() + "\"}";
    }
}
