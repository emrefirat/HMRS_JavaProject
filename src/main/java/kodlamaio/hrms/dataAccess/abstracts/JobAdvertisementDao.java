package kodlamaio.hrms.dataAccess.abstracts;

import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JobAdvertisementDao extends JpaRepository<JobAdvertisement,Integer> {

    List<JobAdvertisement> getByIsActiveTrue();
    List<JobAdvertisement> getByIsActiveTrueOrderByDeadline();
    List<JobAdvertisement> getByIsActiveTrueAndEmployer_Id(int id);
    @Modifying
    @Transactional
    @Query("update JobAdvertisement j set j.isActive = false where j.id = :id")
    void setDeactivateJobAdvertisement(int id);




}