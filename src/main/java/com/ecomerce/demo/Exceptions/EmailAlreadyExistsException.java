package com.ecomerce.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El correo electrónico ya está en uso.")
public class EmailAlreadyExistsException extends Exception{
    
}
