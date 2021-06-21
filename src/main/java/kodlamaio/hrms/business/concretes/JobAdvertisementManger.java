package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.JobAdvertisementService;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobAdvertisementDao;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class JobAdvertisementManger implements JobAdvertisementService {
    private JobAdvertisementDao jobAdvertisementDao;

    @Autowired
    public JobAdvertisementManger(JobAdvertisementDao jobAdvertisementDao) {
        this.jobAdvertisementDao = jobAdvertisementDao;
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
    public SuccessDataResult<List<JobAdvertisement>> getByIsActiveTrue() {
        return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.getByIsActiveTrue(),"Aktif ilanlar listelendi.");
    }

    @Override
    public SuccessDataResult<List<JobAdvertisement>> getByIsActiveTrueAndEmployer_Id(int id) {
        return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.getByIsActiveTrueAndEmployer_Id(id));
    }

    @Override
    public SuccessResult setDeactivateJobAdvertisement(int id) {
        this.jobAdvertisementDao.setDeactivateJobAdvertisement(id);
        return new SuccessResult("Ilan pasif edildi");
    }
}
