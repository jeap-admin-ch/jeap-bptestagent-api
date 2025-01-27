package ch.admin.bit.jeap.testagent.api.notification;

import lombok.*;
import org.springframework.boot.logging.LogLevel;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class LogDto {

    @NonNull
    String logMessage;

    @NonNull
    LogLevel logLevel;

    @NonNull
    String source;
}


