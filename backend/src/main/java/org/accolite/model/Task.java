package org.accolite.model;

import lombok.*;

import java.util.List;

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
    List<ModelEntity> entities;
    String[] extensions;
}


