package org.accolite.model;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
public class Task {
    String language;
    String framework;
    String groupId;
    String artifactId;
    String build;
    String deploy;
    String orchestration;
    String authentication;
    String tracing;
    String monitoring;
    String logging;
    String database;
    String[] extensions;

}