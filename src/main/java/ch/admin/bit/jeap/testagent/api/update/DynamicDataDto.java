package ch.admin.bit.jeap.testagent.api.update;

import lombok.*;

import java.util.Map;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class DynamicDataDto {

    @Singular("data")
    Map<String, String> data;


}
