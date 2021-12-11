package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.AuthService;
import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.core.utilities.results.*;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.entities.concretes.Candidate;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CandidateManager implements CandidateService {
    private CandidateDao candidateDao;
    private AuthService authService;

    @Autowired
    public CandidateManager(CandidateDao candidateDao, @Lazy AuthService authService) {
        this.candidateDao = candidateDao;
        this.authService = authService;
    }

    @Override
    public DataResult<List<Candidate>> getAll() {
        return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(),"Candidates are listed.");
    }

    @Override
    public DataResult<Candidate> add(Candidate candidate,String repeatPassword) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(candidate.getDateOfBirth().toString(), formatter);
        candidate.setDateOfBirth(localDate);
        Result regResult = this.authService.registerForCandidate(candidate,repeatPassword);
        if(regResult.isSuccess()){
            String randomCode = RandomString.make(8);
            //user.setVerificationCode(randomCode);
            //sendVerificationEmail(user, siteURL);
            candidateDao.save(candidate);
            return new SuccessDataResult<Candidate>(candidate,"Kullanıcı eklendi");
        }
        return new ErrorDataResult<Candidate>(candidate,regResult.getMessage());
    }
}
