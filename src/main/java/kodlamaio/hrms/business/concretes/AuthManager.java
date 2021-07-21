package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.AuthService;
import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;
import org.springframework.stereotype.Service;


@Service
public class AuthManager implements AuthService {
    private CandidateService candidateService;
    private EmployerService employerService;
    private CandidateDao candidateDao;
    private EmployerDao employerDao;

    public AuthManager(CandidateService candidateService, EmployerService employerService,
                       CandidateDao candidateDao,EmployerDao employerDao) {
        this.candidateService = candidateService;
        this.employerService = employerService;
        this.candidateDao = candidateDao;
        this.employerDao = employerDao;

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
        if (this.checkIfEmailAlreadyExists(candidate).isSuccess()){
            return new ErrorResult("An candidate have a this email address already exists");
        }
        if (this.checkIfNationalIdAlreadyExists(candidate).isSuccess()){
            return new ErrorResult("An candidate have a this national Id already exists");
        }

        return new SuccessResult();
    }

    @Override
    public Result registerForEmployer(Employer employer, String passwordRepeat) {
        if (!checkPasswordRepeat(employer.getUser().getPassword(),passwordRepeat)){
            return new ErrorResult("Parolalar Eslesmiyor");
        }

        if (employer.getCompanyName().isEmpty() ||
                employer.getWebAddress().isEmpty() ||
                employer.getPhoneNumber().isEmpty() ||
                employer.getUser().getPassword().isEmpty() ) {return new ErrorResult("Tum alanlar zorunludur");
        }

        if (employerDao.existsByUser_Email(employer.getUser().getEmail())){
            return new ErrorResult("This email address already exists for the another employer");
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
            new ErrorResult();
        }
        return new SuccessResult();
    }
    private Result checkIfEmailAlreadyExists(Candidate candidate) {
        boolean result = this.candidateDao.existsByEmail(candidate.getEmail());
        if (result){
            new ErrorResult();
        }
        return new SuccessResult();
    }


}
