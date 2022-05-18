package com.example.reportservice.api;

import com.example.reportservice.domain.model.entity.Report;
import com.example.reportservice.domain.service.ReportService;
import com.example.reportservice.mapping.ReportMapper;
import com.example.reportservice.models.CreateReportResource;
import com.example.reportservice.models.ReportResource;
import com.example.reportservice.models.UpdateReportResource;


//import io.swagger.annotations.ApiOperation;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;







@RestController
@RequestMapping("/api/v1/reportservice")
public class ReportController {


    @Autowired
    private ReportService reportservice;

    @Autowired
    private ReportMapper mapper;

    @Autowired
    private ModelMapper mapping;
    //@ApiOperation(value="getAllReports",notes = "Esta consulta nos retorna todos los reportes existentes")
    @GetMapping("/reports")
    public Page<ReportResource> getAllReports(Pageable pageable) {
        return mapper.modelListToPage(reportservice.getAll(), pageable);
    }
    //@ApiOperation(value="getReportById",notes = "Esta consulta nos ayuda a retorna un reporte segun su id")
    @GetMapping("/reports/{reportId}")
    public ReportResource getReportById(@PathVariable("reportId") Long followId) {
        return mapper.toResource(reportservice.getById(followId));
    }

    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackCreateReport")
    //@ApiOperation(value="createReport",notes = "Esta consulta nos ayuda a crear un reporte segun el id del usuario principal y del usuario a reportar")
    @PostMapping("/usersmains/{UserMainId}/usersreports/{UserReportedId}/reports")
    public ResponseEntity<ReportResource> createReport(@PathVariable Long UserMainId, @PathVariable Long UserReportedId, @RequestBody CreateReportResource request) {
        Report comment = mapping.map(request, Report.class);

        return ResponseEntity.ok(mapping.map(reportservice.create(UserMainId, UserReportedId, comment), ReportResource.class));
    }
    public ResponseEntity<ReportResource> fallBackCreateReport(@PathVariable Long UserMainId, @PathVariable Long UserReportedId, @RequestBody CreateReportResource request, RuntimeException e) {

        return new ResponseEntity("El usuario " + UserMainId + "  no puede crear un reporte para el usuario " +UserReportedId +" por el momento", HttpStatus.OK);

    }

    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackGetAllReportsByMainId")
    //@ApiOperation(value="getAllReportsByMainId",notes = "Esta consulta nos retorna todos los reportes segun el id del usuario que va a reportar")
    @GetMapping("/usersmains/{UserMainId}/reports")
    public ResponseEntity<Page<ReportResource>> getAllReportsByMainId(@PathVariable Long UserMainId,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(reportservice.findByUserMainId(UserMainId), pageable));
    }

    public ResponseEntity<Page<ReportResource>> fallBackGetAllReportsByMainId(@PathVariable Long UserMainId,Pageable pageable, RuntimeException e) {

        return new ResponseEntity("El usuario " + UserMainId + "  no puede mostrar sus  reportes por el momento", HttpStatus.OK);


    }



    @CircuitBreaker(name = "userCB", fallbackMethod = "fallBackGetAllReportsByReportedId")
    //@ApiOperation(value="getAllReportsByReportedId",notes = "Esta consulta nos retorna todos los reportes segun el id del usuario que es reportado")
    @GetMapping("/usersreports/{UserReportedId}/reports")
    public ResponseEntity<Page<ReportResource>> getAllReportsByReportedId(@PathVariable Long UserReportedId,Pageable pageable) {

        return ResponseEntity.ok(mapper.modelListToPage(reportservice.findByUserReportedId(UserReportedId), pageable));
    }
    public ResponseEntity<Page<ReportResource>> fallBackGetAllReportsByReportedId(@PathVariable Long UserReportedId,Pageable pageable, RuntimeException e) {

        return new ResponseEntity("El usuario " + UserReportedId + "  no puede mostrar sus  reportes por el momento", HttpStatus.OK);




    }
    //@ApiOperation(value="deleteReport",notes = "Esta consulta nos elimina un reporte segun su id")
    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<?> deleteReport(@PathVariable Long reportId) {
        return reportservice.delete(reportId);
    }

    //@ApiOperation(value="updateReport",notes = "Esta consulta nos ayuda a actualizar un reporte segun su id")
    @PutMapping("/reports/{reportId}")
    public ReportResource updateReport(@PathVariable Long reportId, @RequestBody UpdateReportResource request) {
        return mapper.toResource(reportservice.update(reportId, mapper.toModel(request)));
    }






}
