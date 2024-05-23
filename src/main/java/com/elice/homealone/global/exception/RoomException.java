package com.elice.homealone.global.exception;

public class RoomException {
    public static class RoomNotFoundException extends RuntimeException{
        public RoomNotFoundException(String message){
            super(message);
        }
    }


}
