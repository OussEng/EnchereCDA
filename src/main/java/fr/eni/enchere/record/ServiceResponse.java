package fr.eni.enchere.record;

public record ServiceResponse<T>(int code, String message, T data) {
}
