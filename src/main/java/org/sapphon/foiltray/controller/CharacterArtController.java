package org.sapphon.foiltray.controller;

import org.sapphon.foiltray.CharacterAnimation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CharacterArtController {
    @GetMapping
    public CharacterAnimation getAllCharacterArt() {
        return null;
    }
}
