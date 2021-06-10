package org.twitter.service.problem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvalidParams {

    private String field;
    private String message;
}
