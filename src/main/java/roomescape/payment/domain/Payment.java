package roomescape.payment.domain;

import static roomescape.payment.domain.PaymentStatus.APPROVED;
import static roomescape.payment.domain.PaymentStatus.FAILED;
import static roomescape.payment.domain.PaymentStatus.PENDING;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentKey;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private Payment(
            final String paymentKey,
            final String orderId,
            final Long amount,
            final PaymentStatus status
    ) {
        validatePaymentKey(paymentKey);
        validateOrderId(orderId);
        validateAmount(amount);
        validateStatus(status);

        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    public static Payment ofOfflinePayment() {
        return new Payment("offline-payment-key", "offline-order-id", 0L, APPROVED);
    }

    public static Payment ofPendingPayment(
            final String paymentKey,
            final String orderId,
            final Long amount
    ) {
        return new Payment(paymentKey, orderId, amount, PENDING);
    }

    public void complete() {
        if (this.status != PENDING) {
            throw new IllegalStateException("결제 상태가 PENDING이 아닙니다. 현재 상태: " + this.status);
        }
        this.status = APPROVED;
    }

    public void fail() {
        if (this.status != PENDING) {
            throw new IllegalStateException("결제 상태가 PENDING이 아닙니다. 현재 상태: " + this.status);
        }
        this.status = FAILED;
    }

    private void validatePaymentKey(final String paymentKey) {
        if (paymentKey == null || paymentKey.isBlank()) {
            throw new IllegalArgumentException("paymentKey는 null이거나 공백일 수 없습니다.");
        }
    }

    private void validateOrderId(final String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("orderId는 null이거나 공백일 수 없습니다.");
        }
    }

    private void validateAmount(final Long amount) {
        if (amount == null) {
            throw new IllegalArgumentException("주문 금액은 null일 수 없습니다.");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("주문 금액은 음수일 수 없습니다.");
        }
    }

    private void validateStatus(final PaymentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("결제 상태는 null일 수 없습니다.");
        }
    }
}
