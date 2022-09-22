package com.pickemsystem.pickemsystembackend.exceptions;

import static com.pickemsystem.pickemsystembackend.utils.AppMessages.INTERNAL_SERVER_ERROR;

public class InternalServerErrorException extends RuntimeException{

    public InternalServerErrorException() {
        super(INTERNAL_SERVER_ERROR);
    }
}
