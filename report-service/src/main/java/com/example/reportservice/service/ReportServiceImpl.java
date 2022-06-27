package com.example.reportservice.service;

import com.example.reportservice.domain.model.entity.Report;
import com.example.reportservice.domain.persistence.ReportRepository;
import com.example.reportservice.domain.service.ReportService;
import com.example.reportservice.shared.exception.ResourceNotFoundException;
import com.example.reportservice.shared.exception.ResourcePerzonalized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {




    private static final String ENTITY = "Report";
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private RestTemplate restTemplate;

    public ReportServiceImpl() {

    }


    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    @Override
    public Page<Report> getAll(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }

    @Override
    public Report getById(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, reportId));
    }

    @Override
    public Report create(Long UserMainId, Long UserReportedId, Report request) {
        //boolean check1 = restTemplate.getForObject("http://user-service/api/v1/userservice/artists/check/" + UserMainId,boolean.class);
        //boolean check2 = restTemplate.getForObject("http://user-service/api/v1/userservice/artists/check/" + UserReportedId,boolean.class);
        //boolean check3 = restTemplate.getForObject("http://user-service/api/v1/userservice/fanatics/check/" + UserMainId,boolean.class);
        //boolean check4 = restTemplate.getForObject("http://user-service/api/v1/userservice/fanatics/check/" + UserReportedId,boolean.class);


            request.setUserReportedId(UserReportedId);
            request.setUserMainId(UserMainId);
            return reportRepository.save(request);




    }

    @Override
    public Report update(Long reportId, Report request) {
        return null;
    }

    @Override
    public List<Report> findByUserMainId(Long UserMainId) {

        return reportRepository.findByUserMainId(UserMainId);

    }

    @Override
    public List<Report> findByUserReportedId(Long UserReportedId) {



        return reportRepository.findByUserReportedId(UserReportedId);

    }

    @Override
    public ResponseEntity<?> delete(Long reportId) {
        return reportRepository.findById(reportId).map(post -> {
            reportRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, reportId));
    }
}
