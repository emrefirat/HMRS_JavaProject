package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.ControlService;
import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.utilities.adapters.MernisAdapter;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;
import org.springframework.stereotype.Service;


@Service
public class ControlManager implements ControlService {
    private CandidateService candidateService;
    private EmployerService employerService;
    private CandidateDao candidateDao;
    private EmployerDao employerDao;
    private MernisAdapter mernisAdapter;

    public ControlManager(CandidateService candidateService, EmployerService employerService,
                          CandidateDao candidateDao, EmployerDao employerDao, MernisAdapter mernisAdapter) {
        this.candidateService = candidateService;
        this.employerService = employerService;
        this.candidateDao = candidateDao;
        this.employerDao = employerDao;
        this.mernisAdapter = mernisAdapter;

    }

    @Override
    public Result registerForCandidate(Candidate candidate, String passwordRepeat) {
        if (!checkPasswordRepeat(candidate.getPassword(),passwordRepeat)){
            return new ErrorResult("Parolalar Eslesmiyor");
        }
        if (candidate.getFirstName().isEmpty() ||
                candidate.getLastName().isEmpty() ||
                candidate.getNationalityId().isEmpty() ||
                candidate.getDateOfBirth() == null ||
                candidate.getEmail().isEmpty() ||
                candidate.getPassword().isEmpty() ||
                passwordRepeat.isEmpty()){
            return new ErrorResult("Tum alanlar zorunludur.");
        }
        if (this.checkIfNationalIdAlreadyExists(candidate).isSuccess()){
            return new ErrorResult("An candidate have a this national Id already exists");
        }
        if (this.checkIfEmailAlreadyExists(candidate).isSuccess()){
            return new ErrorResult("An candidate have a this email address already exists");
        }
        if (!this.mernisAdapter.isValid(candidate).isSuccess()){
            return new ErrorResult("Could not verify from this Mernis");
        }
        return new SuccessResult();
    }

    @Override
    public Result registerForEmployer(Employer employer, String passwordRepeat) {
        if (!checkPasswordRepeat(employer.getPassword(),passwordRepeat)){
            return new ErrorResult("Parolalar Eslesmiyor");
        }

        if (employer.getCompanyName().isEmpty() ||
                employer.getWebAddress().isEmpty() ||
                employer.getPhoneNumber().isEmpty() ||
                employer.getEmail().isEmpty() ||
                employer.getPassword().isEmpty() ) {return new ErrorResult("Tum alanlar zorunludur");
        }

        if (employerDao.existsByEmail(employer.getEmail())){
            return new ErrorResult("This email address already exists for the another employer");
        }
        if (!this.IsCheckEmailAddressEndingAsWebsite(employer).isSuccess()){
            return new ErrorResult("Email and website don't match.");
        }
        return new SuccessResult();
    }


    private boolean checkPasswordRepeat(String password,String passwordRepeat){
        if (password.equals(passwordRepeat)){
            return true;
        }
        return false;
    }

    private Result checkIfNationalIdAlreadyExists(Candidate candidate) {
        boolean result = this.candidateDao.existsByNationalityId(candidate.getNationalityId());
        if (result){
            return new SuccessResult();
        }
        return new ErrorResult();
    }
    private Result checkIfEmailAlreadyExists(Candidate candidate) {
        boolean result = this.candidateDao.existsByEmail(candidate.getEmail());
        if (result){
            return new SuccessResult();
        }
        return new ErrorResult();
    }
    private Result IsCheckEmailAddressEndingAsWebsite(Employer employer){
        String[] parsedData = employer.getEmail().split("@");
        System.out.println(parsedData[1]);
        if (employer.getWebAddress().endsWith(parsedData[1])){
            return new SuccessResult();
        }
        return new ErrorResult();
    }


}
