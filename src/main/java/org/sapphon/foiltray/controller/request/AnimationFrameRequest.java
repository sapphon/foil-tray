package org.sapphon.foiltray.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AnimationFrameRequest {
    String frameData;
    @Nullable
    Integer sequenceNumber = -1;
    @Nullable
    AnimationFrameRequestMode mode = AnimationFrameRequestMode.REPLACE;

    public enum AnimationFrameRequestMode{REPLACE, INSERT}
}
