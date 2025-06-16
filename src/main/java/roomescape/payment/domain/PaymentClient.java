package roomescape.payment.domain;

import roomescape.exception.payment.PaymentException;

public interface PaymentClient {

    void approvePayment(PaymentApproveRequest paymentApproveRequest) throws PaymentException;
}
