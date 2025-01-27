package ch.admin.bit.jeap.testagent.api.act;

import lombok.*;

import java.util.Map;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ActionResultDto {

    @Singular("data")
    Map<String, String> data;

}
