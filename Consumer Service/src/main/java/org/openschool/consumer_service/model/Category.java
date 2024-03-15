package org.openschool.consumer_service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class Category {

    private Long id;

    private String name;
}
