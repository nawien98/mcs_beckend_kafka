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
    String groupId;
    String artifactId;
    String extensions;
}