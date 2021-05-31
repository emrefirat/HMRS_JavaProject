package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.AuthService;
import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;
import org.springframework.stereotype.Service;


@Service
public class AuthManager implements AuthService {
    private CandidateService candidateService;
    private EmployerService employerService;
    private CandidateDao candidateDao;
    public AuthManager(CandidateService candidateService, EmployerService employerService) {
        this.candidateService = candidateService;
        this.employerService = employerService;

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
        if(this.candidateDao.getByEmail(candidate.getEmail()) != null){
            return new ErrorResult("Bu eposta adresine sahip bir hesap zaten var.");
        }



        return null;
    }

    @Override
    public Result registerForEmployer(Employer employer, String passwordRepeat) {
        return null;
    }


    private boolean checkPasswordRepeat(String password,String passwordRepeat){
        if (password.equals(passwordRepeat)){
            return true;
        }
        return false;
    }



}
