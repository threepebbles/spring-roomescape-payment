package roomescape.reservation.domain;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    PENDING_PAYMENT("결제 대기"),
    PAID("결제 완료"),
    ;

    private final String description;

    ReservationStatus(final String description) {
        this.description = description;
    }
}
