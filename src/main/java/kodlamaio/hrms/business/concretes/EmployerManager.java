package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.ControlService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.utilities.results.*;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerManager implements EmployerService {
    private EmployerDao employerDao;
    private ControlService controlService;


    @Autowired
    public EmployerManager(EmployerDao employerDao,@Lazy ControlService controlService) {
        this.employerDao = employerDao;
        this.controlService = controlService;
    }

    @Override
    public DataResult<Employer> add(Employer employer,String repeatPassword) {
        Result regResult = this.controlService.registerForEmployer(employer,repeatPassword);
        if(regResult.isSuccess()){
            this.employerDao.save(employer);
            return new SuccessDataResult<Employer>(employer,"Firma eklendi");
        }
        return new ErrorDataResult<Employer>(employer,regResult.getMessage());


    }

    @Override
    public DataResult<List<Employer>> getAll() {
        return new SuccessDataResult<List<Employer>>(employerDao.findAll(),"Employers are listed.");
    }
}
