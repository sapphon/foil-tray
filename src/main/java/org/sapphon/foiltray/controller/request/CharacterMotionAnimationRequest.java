package org.sapphon.foiltray.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharacterMotionAnimationRequest {
    Integer gameId;
    Integer characterId;
    Integer motionId;
}
