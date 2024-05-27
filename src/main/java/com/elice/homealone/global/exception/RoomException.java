package com.elice.homealone.global.exception;

public class RoomException {
    public static class RoomNotFoundException extends RuntimeException{
        public RoomNotFoundException(String message){
            super(message);
        }
    }

    public static class UnauthorizedActionException  extends RuntimeException{
        public UnauthorizedActionException(String message){
            super(message);
        }
    }

}
