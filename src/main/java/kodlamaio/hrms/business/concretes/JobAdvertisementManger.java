package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.JobAdvertisementService;
import kodlamaio.hrms.core.utilities.results.*;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.JobAdvertisementDao;
import kodlamaio.hrms.entities.concretes.City;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import kodlamaio.hrms.entities.dtos.JobAdvertisementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class JobAdvertisementManger implements JobAdvertisementService {
    private JobAdvertisementDao jobAdvertisementDao;
    private EmployerDao employerDao;

    @Autowired
    public JobAdvertisementManger(JobAdvertisementDao jobAdvertisementDao, EmployerDao employerDao) {
        this.jobAdvertisementDao = jobAdvertisementDao;
        this.employerDao = employerDao;
    }

    @Override
    public Result add(JobAdvertisement jobAdvertisement) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate releaseDate = LocalDate.parse(jobAdvertisement.getReleaseDate().toString(), formatter);
        jobAdvertisement.setReleaseDate(releaseDate);

        LocalDate deadline = LocalDate.parse(jobAdvertisement.getDeadline().toString(), formatter);
        jobAdvertisement.setDeadline(deadline);

        this.jobAdvertisementDao.save(jobAdvertisement);
        return new SuccessResult("Ilan eklendi.");
    }

    @Override
    public DataResult<List<JobAdvertisement>> getByIsActiveTrue() {
        return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.getByIsActiveTrue(),"Aktif ilanlar listelendi.");
    }

    @Override
    public DataResult<List<JobAdvertisement>> getByIsActiveTrueAndEmployer_Id(int id) {
        return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.getByIsActiveTrueAndEmployer_Id(id));
    }

    @Override
    public Result getById(int id) {
        Optional<JobAdvertisement> j = this.jobAdvertisementDao.getJobAdvertisementById(id);
        if (j.isPresent()) {
            return new SuccessDataResult<>(j.get(), "Job advertisement is listed.");
        }
        return new ErrorResult("Job advertisement isn't exist");
    }

    //@Transactional(readOnly = true)
    @Transactional
    @Override
    public Result setDeactivateJobAdvertisement(int id) {
        //this.jobAdvertisementDao.setDeactivateJobAdvertisement(id);
        Optional<JobAdvertisement> j = jobAdvertisementDao.getJobAdvertisementById(id);

        if (j.isPresent()) {
            if (j.get().isActive()){
                j.get().setActive(false);
                return new SuccessResult("Ilan devre dışı bırakıldı");
            }
            return new SuccessResult("Ilan zaten devre dışı");
        }
        else {
            return new ErrorResult("Böyle bir ilan yok ya da önceden silinmiş.");
        }

    }
    @Transactional
    @Override
    public Result setActivateJobAdvertisement(int id) {
        Optional<JobAdvertisement> j = this.jobAdvertisementDao.getJobAdvertisementById(id);
        if (j.isPresent()){
            if (j.get().isActive()){
                return new ErrorResult("Job advertisement is already active");
            }
            j.get().setActive(true);
            return new SuccessResult("Job advertisement is activated successfully.");
        }
        return new ErrorResult("There is no such job advertisement or it has been previously deleted.");

    }

    @Override
    public DataResult<List<JobAdvertisementDto>> getJobAdvertisementsIsActive() {
        return new SuccessDataResult<List<JobAdvertisementDto>>(this.jobAdvertisementDao.getJobAdvertisementsDtoIsActive());
    }

    @Override
    public DataResult<List<JobAdvertisementDto>> getJobAdvertisementsDtoIsActiveByCompanyName(String companyName) {
        return new SuccessDataResult<List<JobAdvertisementDto>>(this.jobAdvertisementDao.getJobAdvertisementsDtoIsActiveByCompanyName(companyName));

    }
}
