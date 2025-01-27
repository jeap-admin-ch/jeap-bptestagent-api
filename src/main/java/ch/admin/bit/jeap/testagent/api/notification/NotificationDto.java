package ch.admin.bit.jeap.testagent.api.notification;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class NotificationDto implements Serializable {

    @NotNull
    private String testId;

    @NotNull
    private String notification;

    @NotNull
    private String producer;

    @Singular("data")
    private Map<String, String> data;
}
