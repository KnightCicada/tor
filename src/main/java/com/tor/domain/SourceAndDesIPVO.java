package com.tor.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class SourceAndDesIPVO {
    private String source;
    private String target;
}
