package fr.eni.enchere.exeception.advice;

import fr.eni.enchere.exeception.AlreadyExistsException;
import fr.eni.enchere.exeception.NotFoundException;
import fr.eni.enchere.exeception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    public String handleAlreadyExistsException(AlreadyExistsException ex,
                                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erreur", ex.getMessage());
        return "redirect:/auth";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erreur", ex.getMessage());
        System.out.println(ex.getMessage());
        return "redirect:/auth";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNoResourceFoundException(NotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erreur", "La page demandée n'existe pas.");
        return "404";
    }

    // attrape tout ce qui n'est pas géré
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erreur", "Une erreur inattendue s'est produite.");
        return "error";
    }
}
