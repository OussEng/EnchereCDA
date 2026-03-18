package fr.eni.enchere.record;

import java.time.LocalDateTime;

public record ServiceResponse<T>(int code, String message, T data) {
}
