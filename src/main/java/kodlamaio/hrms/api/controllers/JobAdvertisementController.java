package kodlamaio.hrms.api.controllers;

import kodlamaio.hrms.business.abstracts.JobAdvertisementService;
import kodlamaio.hrms.core.utilities.results.*;
import kodlamaio.hrms.entities.concretes.Candidate;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/jobAdvertisements")

public class JobAdvertisementController {

    private JobAdvertisementService jobAdvertisementService;

    @Autowired
    public JobAdvertisementController(JobAdvertisementService jobAdvertisementService) {
        this.jobAdvertisementService = jobAdvertisementService;
    }
    @GetMapping("/getbyIsActiveTrue")
    DataResult<List<JobAdvertisement>> getJobAdvertisementsByActiveIsTrue(){
        return this.jobAdvertisementService.getByIsActiveTrue();
    }
    @PostMapping("/add")
    public Result add(@RequestBody JobAdvertisement jobAdvertisement){
        return this.jobAdvertisementService.add(jobAdvertisement);
    }

    @GetMapping("/getByIsActiveTrueAndEmployer")
    DataResult<List<JobAdvertisement>> getByIsActiveTrueAndEmployer_Id(@RequestParam int id){
        return this.jobAdvertisementService.getByIsActiveTrueAndEmployer_Id(id);
    }

    @GetMapping("/getById")
    Result getById(@RequestParam int id){
        return this.jobAdvertisementService.getById(id);

    }
    @PostMapping("/setDeactivateJobAdvertisement")
    Result setDeactivateJobAdvertisement(@RequestParam int id){
        var result = this.jobAdvertisementService.setDeactivateJobAdvertisement(id).isSuccess();
        var message = this.jobAdvertisementService.setDeactivateJobAdvertisement(id).getMessage();
        if (result){
            return new SuccessResult(message);
        }
        return new ErrorResult(message);

    }
    @PostMapping("/setActivateJobAdvertisement")
    Result setActivateJobAdvertisement(@RequestParam int id){
        return this.jobAdvertisementService.setActivateJobAdvertisement(id);

    }

}
