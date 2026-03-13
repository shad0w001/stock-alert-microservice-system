package events;

import enums.AlertCondition;
import enums.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertCreatedEvent {
    private Long alertId;
    private UUID userId;
    private String symbol;
    private BigDecimal targetPrice;
    private AlertCondition conditionType;
    private AlertStatus status;
}