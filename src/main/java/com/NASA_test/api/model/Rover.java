package com.NASA_test.api.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author jspaciuk
 * @created/modified on 08/25/2021
 */
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Accessors(fluent = true)
public class Rover {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("landing_date")
    public String landingDate;
    @JsonProperty("launch_date")
    public String launchDate;
    @JsonProperty("status")
    public String status;
}