package com.manage_system.Events.Infrastucture.exception;

// ... (Otras importaciones)
import com.manage_system.Events.Domain.exception.DomainValidationException;
import com.manage_system.Events.application.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC; // ¡NUEVA IMPORTACIÓN!
// Necesitarás una referencia a las claves definidas en tu Interceptor
// Asumimos que la clase interceptora está en un paquete accesible, por ejemplo:
// import com.manage_system.Events.Infrastucture.config.MDCInterceptor;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // URI base para identificar tipos de errores (cambiar por tu dominio real)
    private static final String BASE_URI = "https://yourdomain.com/errors/";

    /**
     * MÉTODO UNIFICADO: Obtiene el ID de correlación del MDC (establecido por el Interceptor).
     * Si el MDC está vacío (la excepción ocurrió muy pronto), genera uno nuevo.
     */
    private String getCorrelationId() {
        // Asumiendo que definiste la clave 'traceId' en tu MDCInterceptor.
        String traceId = MDC.get("traceId");

        // Si el traceId no está en el MDC (p. ej., excepción fuera de la cadena de request), genera uno de respaldo
        return traceId != null ? traceId : "N/A-" + UUID.randomUUID().toString();
    }


    // --- 1. Excepciones de Dominio, Aplicación, Infraestructura ---

    @ExceptionHandler(DomainValidationException.class)
    public ProblemDetail handleDomainException(DomainValidationException ex, WebRequest request) {
        log.error("Validation error - traceId={}", getCorrelationId(), ex);
        // Status 400 (BAD REQUEST)
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("Domain Validation Failed");
        pd.setType(URI.create(BASE_URI + "domain-validation"));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("traceId", getCorrelationId()); // Llama al método unificado
        return pd;
    }

    @ExceptionHandler(ApplicationException.class)
    public ProblemDetail handleApplicationException(ApplicationException ex, WebRequest request) {
        log.error("Validation error - traceId={}", getCorrelationId(), ex);
        // Status 422 (UNPROCESSABLE ENTITY)
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        pd.setTitle("Application Business Rule Failed");
        pd.setType(URI.create(BASE_URI + "application-rule"));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("traceId", getCorrelationId()); // Llama al método unificado
        return pd;
    }

    @ExceptionHandler(InfrastructureException.class)
    public ProblemDetail handleInfrastructureException(InfrastructureException ex, WebRequest request) {
        log.error("Validation error - traceId={}", getCorrelationId(), ex);
        // Status 500 (INTERNAL SERVER ERROR)
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        pd.setTitle("Infrastructure Error");
        pd.setType(URI.create(BASE_URI + "infrastructure-error"));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("traceId", getCorrelationId()); // Llama al método unificado
        return pd;
    }

    // --- 2. Excepciones de Framework/Validación ---

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Validation error - traceId={}", getCorrelationId(), ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle("Validation Failed");
        pd.setType(URI.create(BASE_URI + "invalid-arguments"));

        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (existing, replacement) -> existing
                ));

        pd.setDetail("The request body contains invalid data fields.");
        pd.setProperty("validationErrors", errors);
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("traceId", getCorrelationId()); // Llama al método unificado

        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintValidation(ConstraintViolationException ex, WebRequest request) {
        log.error("Validation error - traceId={}", getCorrelationId(), ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle("Constraint Violation");
        pd.setType(URI.create(BASE_URI + "constraint-violation"));

        Map<String, String> violations = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage(),
                        (existing, replacement) -> existing
                ));

        pd.setDetail("One or more request parameters/variables failed validation.");
        pd.setProperty("violations", violations);
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("traceId", getCorrelationId()); // Llama al método unificado

        return pd;
    }

    // --- 3. Excepciones Comunes de Persistencia/API ---

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        log.error("Validation error - traceId={}", getCorrelationId(), ex);
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        pd.setTitle("Resource Not Found");
        pd.setType(URI.create(BASE_URI + "resource-not-found"));
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("traceId", getCorrelationId()); // Llama al método unificado
        return pd;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        log.error("Validation error - traceId={}", getCorrelationId(), ex);
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle("Data Conflict");
        pd.setType(URI.create(BASE_URI + "data-integrity-conflict"));

        String rootCause = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "Database integrity constraint failed.";

        pd.setDetail("A database constraint violation occurred.");
        pd.setProperty("rootCause", rootCause);
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("traceId", getCorrelationId()); // Llama al método unificado
        return pd;
    }

    // --- 4. Fallback Genérico ---

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, WebRequest request) {
        log.error("Validation error - traceId={}", getCorrelationId(), ex);
        ex.printStackTrace(); // Es bueno mantener el log en consola/archivos
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, "An unexpected internal error occurred. Please contact support.");
        pd.setTitle("Internal Server Error");
        pd.setType(URI.create(BASE_URI + "generic-internal-error"));

        pd.setProperty("exceptionType", ex.getClass().getSimpleName());
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("traceId", getCorrelationId()); // Llama al método unificado
        return pd;
    }
}