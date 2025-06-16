package roomescape.reservation.event;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import roomescape.log.Loggable;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.repository.ReservationRepository;

@Component
@RequiredArgsConstructor
public class ReservationEventHandler {

    private final ReservationRepository reservationRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Loggable
    public void handleReservationPaymentCompletedEvent(final ReservationPaymentCompletedEvent event) {
        final Reservation reservation = reservationRepository.getById(event.reservationId());
        reservation.completePayment();
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Loggable
    public void handleReservationPaymentFailedEvent(final ReservationPaymentFailedEvent event) {
        final Reservation reservation = reservationRepository.getById(event.reservationId());
        reservation.failPayment();
    }
}
