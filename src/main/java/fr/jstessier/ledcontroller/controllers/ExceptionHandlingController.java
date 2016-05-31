package fr.jstessier.ledcontroller.controllers;

import fr.jstessier.ledcontroller.exceptions.ResourceAlreadyExistsException;
import fr.jstessier.ledcontroller.exceptions.ResourceException;
import fr.jstessier.ledcontroller.exceptions.ResourceIdMustBeEmptyException;
import fr.jstessier.ledcontroller.exceptions.ResourceNotFoundException;
import fr.jstessier.patch.exceptions.PatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public void conflict() {
        // Nothing to do
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public void notFound() {
        // Nothing to do
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class, PatchException.class, ResourceException.class})
    public void badRequest() {
        // Nothing to do
    }

}
