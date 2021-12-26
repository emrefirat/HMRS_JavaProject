package kodlamaio.hrms.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JobAdvertisementDto {
    private int id;
    private String companyName;
    private String jobTitle;
    private int openPositions;
    private LocalDate releaseDate;
    private LocalDate deadLine;
    private boolean isActive;
}