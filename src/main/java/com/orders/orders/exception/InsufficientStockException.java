package com.orders.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InsufficientStockException extends OrdersException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Stock is insufficient for deduction.");
        pb.setDetail("The requested operation cannot be completed because the current stock level is zero or below the required threshold.");

        return pb;
    }
}
