package br.com.tiviati.sct.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Getter
@JsonTypeIdResolver(ApiError.LowerCaseClassNameResolver.class)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
public class ApiError {
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(final HttpStatus status, final String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ApiError(final HttpStatus status, final Throwable ex) {
        this();
        this.status = status;
        this.message = ex.getLocalizedMessage();
    }

    public ApiError(final HttpStatus status, final String message, final Throwable ex) {
        this();
        this.status = status;
        this.message = ex.getLocalizedMessage();
    }

    static class LowerCaseClassNameResolver extends TypeIdResolverBase {
        @Override
        public String idFromValue(Object value) {
            return value.getClass().getSimpleName().toLowerCase();
        }

        @Override
        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            return idFromValue(value);
        }

        @Override
        public JsonTypeInfo.Id getMechanism() {
            return JsonTypeInfo.Id.CUSTOM;
        }
    }
}
