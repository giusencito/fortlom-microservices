package com.example.reportservice.models;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportResource {
    private Long id;

    private String ReportDescription;

    private Long UserMainId;

    private Long UserReportedId;

}
