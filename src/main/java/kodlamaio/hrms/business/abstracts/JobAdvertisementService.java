package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;

import java.util.List;

public interface JobAdvertisementService {
    Result add(JobAdvertisement jobAdvertisement);
    DataResult<List<JobAdvertisement>> getByIsActiveTrue();
    DataResult<List<JobAdvertisement>> getByIsActiveTrueAndEmployer_Id(int id);
    Result getById(int id);
    Result setDeactivateJobAdvertisement(int id);
    Result setActivateJobAdvertisement(int id);




}
