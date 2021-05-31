package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.entities.concretes.JobTitle;

import javax.xml.crypto.Data;
import java.util.List;

public interface JobTitleService {
    DataResult<List<JobTitle>> getAll();
}
