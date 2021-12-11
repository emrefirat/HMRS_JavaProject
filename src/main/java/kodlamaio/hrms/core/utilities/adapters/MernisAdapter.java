package kodlamaio.hrms.core.utilities.adapters;

import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.entities.concretes.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MernisAdapter {

         public Result isValid(Candidate candidate){
             Mernis mernis = new Mernis();
        long longTC = Long.parseLong(candidate.getNationalityId());
        Boolean result = mernis.validate(longTC,candidate.getFirstName(),candidate.getLastName(),candidate.getDateOfBirth().getYear());
        if (result){
            return new SuccessResult();
        }
        else {
            return new ErrorResult();
        }
    }


}
