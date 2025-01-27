package ch.admin.bit.jeap.testagent.api.verify;

import lombok.*;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ResultDto {

    @NonNull
    String name;

    @NonNull
    Conclusion conclusion;

    String detail;
}
