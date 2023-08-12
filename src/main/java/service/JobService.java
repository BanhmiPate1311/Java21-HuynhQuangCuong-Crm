package service;

import model.JobModel;
import repository.JobRepository;

import java.sql.Date;
import java.util.List;

public class JobService {

    private JobRepository jobRepository= new JobRepository();

    public List<JobModel> getAllJobs() {
        return jobRepository.findAll();
    }

    public boolean insertJob(String name, Date startDay, Date endDay) {
        return jobRepository.insertJob(name, startDay, endDay);
    }

    public boolean deleteJob(int id) {
        return jobRepository.deleteById(id);
    }

    public JobModel findById(int id) {
        return jobRepository.findById(id);
    }

    public boolean updateJob(int id, String name, Date startDay, Date endDay) {
        return jobRepository.updateJob(id, name, startDay, endDay);
    }
}
