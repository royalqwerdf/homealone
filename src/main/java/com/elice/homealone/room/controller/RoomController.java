package com.elice.homealone.room.controller;

import com.elice.homealone.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RoomController {
    private RoomService roomService;
}
