package edu.otaciotarelho.pointofsale.domain.exception;

import lombok.Builder;

@Builder
public class StandardError {
    private static final long serialVersionUID = 1L;
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
