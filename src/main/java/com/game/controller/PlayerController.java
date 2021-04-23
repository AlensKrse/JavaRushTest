package com.game.controller;

import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

}
