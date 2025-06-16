package roomescape.payment.event;

public record ReservationPaymentRequestEvent(
        Long reservationId,
        String paymentKey,
        String orderId,
        Long amount
) {

}
