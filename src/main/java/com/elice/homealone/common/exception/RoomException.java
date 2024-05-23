package com.elice.homealone.common.exception;

public class RoomException {
    public static class RoomNotFoundException extends RuntimeException{
        public RoomNotFoundException(String message){
            super(message);
        }
    }


}
