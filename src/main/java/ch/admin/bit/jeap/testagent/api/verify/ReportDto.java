package ch.admin.bit.jeap.testagent.api.verify;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ReportDto {

    @NonNull
    String testcase;
    @NonNull
    String testId;
    @NonNull
    ZonedDateTime dateTime;

    @NonNull
    @Singular("result")
    List<ResultDto> results;

}
