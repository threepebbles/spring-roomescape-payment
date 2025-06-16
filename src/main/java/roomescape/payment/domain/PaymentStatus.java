package roomescape.payment.domain;

public enum PaymentStatus {

    PENDING("결제 대기 중"),
    APPROVED("결제 승인"),
    FAILED("결제 실패"),
    CANCELLED("결제 취소"),
    ;

    private final String description;

    PaymentStatus(final String description) {
        this.description = description;
    }
}
