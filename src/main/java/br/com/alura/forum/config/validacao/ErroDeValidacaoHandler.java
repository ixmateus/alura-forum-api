package br.com.alura.forum.config.validacao;

import com.zaxxer.hikari.util.FastList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormularioDto> handler(MethodArgumentNotValidException exception) {
        List<ErroDeFormularioDto> dto = new ArrayList<>();
        List<FieldError> fieldError = exception.getBindingResult().getFieldErrors();
        fieldError.forEach(e -> {
            String mensagemDeErro = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagemDeErro);
            dto.add(erro);
        });

                return dto;
    }

}
