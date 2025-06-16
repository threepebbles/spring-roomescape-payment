package roomescape.payment.domain;

public record PaymentApproveRequest(
        String paymentKey,
        String orderId,
        Long amount
) {

}
