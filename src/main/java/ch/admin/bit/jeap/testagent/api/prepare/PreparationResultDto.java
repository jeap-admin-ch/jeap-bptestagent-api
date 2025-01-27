package ch.admin.bit.jeap.testagent.api.prepare;

import lombok.*;

import java.util.Map;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class PreparationResultDto {

    @Singular("data")
    Map<String, String> data;

}
