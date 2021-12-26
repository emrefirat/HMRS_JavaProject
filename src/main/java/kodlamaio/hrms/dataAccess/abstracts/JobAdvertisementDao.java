package kodlamaio.hrms.dataAccess.abstracts;

import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import kodlamaio.hrms.entities.dtos.JobAdvertisementDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface JobAdvertisementDao extends JpaRepository<JobAdvertisement,Integer> {

    List<JobAdvertisement> getByIsActiveTrue();
    List<JobAdvertisement> getByIsActiveTrueAndEmployer_Id(int id);
    Optional<JobAdvertisement> getJobAdvertisementById(Integer id);

    @Query("select new kodlamaio.hrms.entities.dtos.JobAdvertisementDto(ja.id, em.companyName, jt.name,ja.openPositions,ja.releaseDate,ja.deadline,ja.isActive)"
            + "FROM JobAdvertisement ja INNER join ja.employer  em INNER join ja.jobTitle jt"
    )
    List<JobAdvertisementDto> getJobAdvertisementsDtoIsActive();


    @Query("select new kodlamaio.hrms.entities.dtos.JobAdvertisementDto(ja.id, em.companyName, jt.name,ja.openPositions,ja.releaseDate,ja.deadline,ja.isActive)"
            + "FROM JobAdvertisement ja INNER join ja.employer  em INNER join ja.jobTitle jt where ja.employer.companyName = :#{#companyName} "
    )
    List<JobAdvertisementDto> getJobAdvertisementsDtoIsActiveByCompanyName(@Param("companyName") String companyName);




}