package ch.admin.bit.jeap.testagent.api.prepare;

import lombok.*;

import java.util.Map;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
public class PreparationDto {

    @Getter
    @NonNull
    String testCase;

    @Setter
    @Getter
    @Singular("data")
    Map<String, String> data;

    @Getter
    @NonNull
    String callbackBaseUrl;

}
