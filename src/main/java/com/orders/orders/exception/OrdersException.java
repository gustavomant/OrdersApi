package com.orders.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class OrdersException extends RuntimeException {
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("Orders internal server error");
        
        return pb;
    }
}
