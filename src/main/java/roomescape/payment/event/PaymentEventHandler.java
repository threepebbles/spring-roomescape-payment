package roomescape.payment.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import roomescape.exception.payment.PaymentException;
import roomescape.log.Loggable;
import roomescape.payment.domain.PaymentApproveRequest;
import roomescape.payment.domain.PaymentClient;
import roomescape.reservation.event.ReservationPaymentCompletedEvent;
import roomescape.reservation.event.ReservationPaymentFailedEvent;

@Component
@RequiredArgsConstructor
public class PaymentEventHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final PaymentClient paymentClient;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Loggable
    public void handleReservationPaymentRequestEvent(final ReservationPaymentRequestEvent event) {
        try {
            paymentClient.approvePayment(
                    new PaymentApproveRequest(
                            event.paymentKey(),
                            event.orderId(),
                            event.amount()
                    )
            );
            eventPublisher.publishEvent(new ReservationPaymentCompletedEvent(event.reservationId()));
        } catch (PaymentException e) {
            eventPublisher.publishEvent(new ReservationPaymentFailedEvent(event.reservationId()));
            throw e;
        }
    }
}
