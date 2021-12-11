package kodlamaio.hrms.entities.concretes;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "candidates")
@AllArgsConstructor
@NoArgsConstructor

public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nationality_id")
    private String nationalityId;

    @Column(name = "date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ApiModelProperty(hidden = true) //Api'de gizler
    @Column(name = "is_active", columnDefinition = "boolean default false")
    private Boolean isActive = false;

    @JsonIgnore
    @OneToMany(mappedBy = "candidate")
    private List<ResumeEducation> resumeEducations;

    @JsonIgnore
    @OneToMany(mappedBy = "candidate",cascade=CascadeType.ALL)
    private List<ResumeExperience> resumeExperiences;

}


