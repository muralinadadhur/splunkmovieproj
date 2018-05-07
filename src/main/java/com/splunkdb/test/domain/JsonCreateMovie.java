package com.splunkdb.test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class JsonCreateMovie {
    private String name;
    private String description;
}
