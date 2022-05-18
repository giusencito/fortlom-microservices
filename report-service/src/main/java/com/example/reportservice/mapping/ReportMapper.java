package com.example.reportservice.mapping;

import com.example.forumservice.domain.model.entity.Forum;
import com.example.forumservice.models.CreateForumResource;
import com.example.forumservice.models.ForumResource;
import com.example.forumservice.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.io.Serializable;
import java.util.List;
public class ReportMapper implements Serializable{

    @Autowired
    EnhancedModelMapper mapper;

    public ReportResource toResource(Report model) {
        return mapper.map(model, ReportResource.class);
    }

    public Page<ReportResource> modelListToPage(List<Report> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, ReportResource.class), pageable, modelList.size());
    }

    public Report toModel(CreateReportResource resource) {
        return mapper.map(resource, Report.class);
    }


}
