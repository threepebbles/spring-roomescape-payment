package roomescape.reservation.domain;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    PENDING_PAYMENT("결제 대기"),
    PAYMENT_FAILED("결제 실패"),
    PAYMENT_COMPLETED("결제 완료"),
    ;

    private final String description;

    ReservationStatus(final String description) {
        this.description = description;
    }
}
