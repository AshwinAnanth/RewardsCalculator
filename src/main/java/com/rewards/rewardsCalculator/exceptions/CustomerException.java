package com.rewards.rewardsCalculator.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerException extends RuntimeException {

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}
