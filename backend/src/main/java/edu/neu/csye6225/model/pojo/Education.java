package edu.neu.csye6225.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(uniqueConstraints= @UniqueConstraint(columnNames = {"user_id"}))
public class Education {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false)
    private String id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotEmpty(message = "User id cannot be empty")
    @Column(nullable = false, updatable = false)
    private String user_id;

    private String universityName;
    private String highestDegree;
    private int workExperiences;
    private String skillsSet;
    private String linkedInURL;
    private String gitHubURL;
    private String portfolioURL;

    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(updatable = false)
    private ZonedDateTime account_created;

    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ZonedDateTime account_updated;
}
