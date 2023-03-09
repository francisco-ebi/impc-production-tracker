package org.gentar.report.export;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import org.gentar.report.collection.glt_attempts.GltAttemptsServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class GltAttemptsController {

    private final GltAttemptsServiceImpl gltAttemptsService;

    public GltAttemptsController(GltAttemptsServiceImpl gltAttemptsService) {
        this.gltAttemptsService = gltAttemptsService;
    }


    //http://localhost:8080/api/reports/glt_attempts?attempt=crispr&workunit=IMPC&startingyear=2016&endingyear=2023
    @GetMapping("/glt_attempts")
    @Transactional(readOnly = true)
    public void exportGltAttemptsWithMonth(HttpServletResponse response,
                                           @RequestParam(value = "reporttype")
                                               String reportType,
                                           @RequestParam(value = "attempt")
                                                   String attempt,
                                           @RequestParam(value = "workunit", required = false)
                                               String workUnit,
                                           @RequestParam(value = "workGroup", required = false)
                                                   String workGroup,
                                           @RequestParam(value = "startyear", required = false)
                                                   String startYear,
                                           @RequestParam(value = "endyear", required = false)
                                               String endYear,
                                           @RequestParam(value = "startmonth", required = false)
                                                   String starMonth,
                                           @RequestParam(value = "endmonth", required = false)
                                                   String endMonth)
        throws IOException, ParseException {
        gltAttemptsService
            .generateGltAttemptsReport(response,reportType, attempt,workUnit,workGroup,startYear, endYear,starMonth, endMonth);
    }
}