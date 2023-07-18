package org.gentar.biology.glt_production_numbers;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.gentar.biology.gene_list.record.GeneListProjection;
import org.gentar.report.collection.glt_attempts.GltAttemptsServiceImpl;
import org.gentar.report.collection.glt_attempts.projection.GltAttemptProjection;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GltAttemptsJsonController {

    private final GltAttemptsServiceImpl gltAttemptsService;

    public GltAttemptsJsonController(GltAttemptsServiceImpl gltAttemptsService) {
        this.gltAttemptsService = gltAttemptsService;
    }


    //http://localhost:8080/api/glt_production_numbers?attempt=crispr&workunit=IMPC&startingyear=2016&endingyear=2023
    @GetMapping("/glt_production_numbers")
    @Transactional(readOnly = true)
    public ResponseEntity<List<GltAttemptProjectionDto>> exportGltAttemptsWithMonth(HttpServletResponse response,
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
        if (attempt.equals("escell")) {
            attempt = "es cell";
        }

        try {
            List<GltAttemptProjection> gltAttemptProjections = gltAttemptsService
                .generateGltAttemptsJson(reportType, attempt, workUnit, workGroup,
                    startYear, endYear, starMonth, endMonth);

            List<GltAttemptProjectionDto>  gltAttemptProjectionsDto =
                gltAttemptProjections.stream().map(this::mapToDto).collect(Collectors.toList());

            return ResponseEntity.ok(gltAttemptProjectionsDto);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an error response
            return ResponseEntity.status(500).body(null);
        }

    }

    //http://localhost:8080/api/glt_production_numbers/overlap/intersection
    @GetMapping("/glt_production_numbers/overlap/intersection")
    @Transactional(readOnly = true)
    public ResponseEntity<List<String>> exportGltAttemptsIntersection() {

        try {
            List<GltAttemptProjection> gltAttemptProjections = gltAttemptsService
                .getGltAttemptsIntersectionJson();

            List<String> symbols =
                gltAttemptProjections.stream().map(GltAttemptProjection::getSymbol).collect(Collectors.toList());

            return ResponseEntity.ok(symbols);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an error response
            return ResponseEntity.status(500).body(null);
        }
    }

    //http://localhost:8080/api/glt_production_numbers/overlap/union
    @GetMapping("/glt_production_numbers/overlap/union")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Long>> exportGltAttemptsUnion() {
        try {
            List<GltAttemptProjection> gltAttemptProjections = gltAttemptsService
                .getGltAttemptsUnionJson();

            List<Long> count =
                gltAttemptProjections.stream().map(GltAttemptProjection::getCount).collect(Collectors.toList());

            return ResponseEntity.ok(count);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an error response
            return ResponseEntity.status(500).body(null);
        }
    }

    private GltAttemptProjectionDto mapToDto(GltAttemptProjection gltAttemptProjection) {
        GltAttemptProjectionDto gltAttemptProjectionDto = new GltAttemptProjectionDto();

        try {
            gltAttemptProjectionDto.setYear(gltAttemptProjection.getYear());
        } catch (Exception e) {
            // Handle the exception (e.g., log an error) and continue with the next line
        }

        try {
            gltAttemptProjectionDto.setMonth(gltAttemptProjection.getMonth());
        } catch (Exception e) {
            // Handle the exception (e.g., log an error) and continue with the next line
        }

        try {
            gltAttemptProjectionDto.setWorkUnitName(gltAttemptProjection.getWorkUnitName());
        } catch (Exception e) {
            // Handle the exception (e.g., log an error) and continue with the next line
        }

        try {
            gltAttemptProjectionDto.setWorkGroupName(gltAttemptProjection.getWorkGroupName());
        } catch (Exception e) {
            // Handle the exception (e.g., log an error) and continue with the next line
        }

        try {
            gltAttemptProjectionDto.setSum(gltAttemptProjection.getSum());
        } catch (Exception e) {
            // Handle the exception (e.g., log an error) and continue with the next line
        }

        try {
            gltAttemptProjectionDto.setSymbol(gltAttemptProjection.getSymbol());
        } catch (Exception e) {
            // Handle the exception (e.g., log an error) and continue with the next line
        }

        try {
            gltAttemptProjectionDto.setCount(gltAttemptProjection.getCount());
        } catch (Exception e) {
            // Handle the exception (e.g., log an error) and continue with the next line
        }

        return gltAttemptProjectionDto;
    }


}