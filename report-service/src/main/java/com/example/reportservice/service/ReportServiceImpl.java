package com.example.reportservice.service;

import com.example.reportservice.domain.model.entity.Report;
import com.example.reportservice.domain.persistence.ReportRepository;
import com.example.reportservice.domain.service.ReportService;
import com.example.reportservice.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.validation.Validator;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {




    private static final String ENTITY = "Report";
    @Autowired
    private ReportRepository reportRepository;


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

                    request.setUserReportedid(UserReportedId);
                    request.setUserMainid(UserMainId);
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
