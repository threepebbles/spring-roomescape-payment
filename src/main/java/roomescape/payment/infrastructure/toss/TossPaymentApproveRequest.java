package roomescape.payment.infrastructure.toss;

import roomescape.payment.domain.PaymentApproveRequest;

public record TossPaymentApproveRequest(
        String paymentKey,
        String orderId,
        long amount
) {
    public static TossPaymentApproveRequest from(final PaymentApproveRequest paymentApproveRequest) {
        return new TossPaymentApproveRequest(
                paymentApproveRequest.paymentKey(),
                paymentApproveRequest.orderId(),
                paymentApproveRequest.amount()
        );
    }
}
