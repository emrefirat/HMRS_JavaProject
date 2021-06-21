package kodlamaio.hrms.api.controllers;

import kodlamaio.hrms.business.abstracts.JobAdvertisementService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
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

    @PostMapping("/setDeactivateJobAdvertisement")
    SuccessResult setDeactivateJobAdvertisement(@RequestParam int id){
        this.jobAdvertisementService.setDeactivateJobAdvertisement(id);
        return new SuccessResult();
    }
}
