package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;

import java.util.List;

public interface JobAdvertisementService {
    Result add(JobAdvertisement jobAdvertisement);
    SuccessDataResult<List<JobAdvertisement>> getByIsActiveTrue();
    SuccessDataResult<List<JobAdvertisement>> getByIsActiveTrueAndEmployer_Id(int id);
    SuccessResult setDeactivateJobAdvertisement(int id);



}
