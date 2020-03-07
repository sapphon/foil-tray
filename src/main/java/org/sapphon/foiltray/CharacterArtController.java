package org.sapphon.foiltray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CharacterArtController {
    @GetMapping
    public CharacterAnimation getAllCharacterArt() {
        return null;
    }
}
