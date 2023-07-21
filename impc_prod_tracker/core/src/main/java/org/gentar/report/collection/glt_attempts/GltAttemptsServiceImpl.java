package org.gentar.report.collection.glt_attempts;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.gentar.report.Report;
import org.gentar.report.ReportService;
import org.gentar.report.collection.glt_attempts.projection.GltAttemptProjection;
import org.gentar.report.collection.glt_attempts.repository.GltAttemptRepository;
import org.springframework.stereotype.Component;

@Component
public class GltAttemptsServiceImpl implements GltAttemptsService {

    private final ReportService reportService;
    private final GltAttemptRepository gltAttemptRepository;

    public GltAttemptsServiceImpl(ReportService reportService,
                                  GltAttemptRepository gltAttemptRepository) {
        this.reportService = reportService;
        this.gltAttemptRepository = gltAttemptRepository;
    }

    @Override
    public void generateGltAttemptsReport(HttpServletResponse response,
                                          String reportType,
                                          String attempt,
                                          String workUnit,
                                          String workGroup,
                                          String startYear,
                                          String endYear,
                                          String startMonth,
                                          String endMonth)
        throws IOException, ParseException {


        if (workUnit != null) {
            if (workGroup != null) {
                gltAttemptsByAttemptWithWorkUnitWorkGroup(response, reportType, attempt, workUnit,
                    workGroup, startYear,
                    endYear, startMonth,
                    endMonth);
            } else {
                gltAttemptsByAttemptWithWorkUnit(response, reportType, attempt, workUnit, startYear,
                    endYear, startMonth,
                    endMonth);
            }
        } else {
            gltAttemptsByAttemptWithoutWorkUnit(response, reportType, attempt, startYear, endYear,
                startMonth,
                endMonth);
        }
    }


    @Override
    public List<GltAttemptProjection> generateGltAttemptsJson(
        String reportType,
        String attempt,
        String workUnit,
        String workGroup,
        String startYear,
        String endYear,
        String startMonth,
        String endMonth)
        throws ParseException, IOException {


        if (workUnit != null) {
            if (workGroup != null) {
                return gltAttemptsByAttemptWithWorkUnitWorkGroupJson(reportType, attempt, workUnit,
                    workGroup, startYear,
                    endYear, startMonth,
                    endMonth);
            } else {
                return gltAttemptsByAttemptWithWorkUnitJson(reportType, attempt, workUnit,
                    startYear,
                    endYear, startMonth,
                    endMonth);
            }
        } else {
            return gltAttemptsByAttemptWithoutWorkUnitJson(reportType, attempt, startYear, endYear,
                startMonth,
                endMonth);
        }
    }

    @Override
    public void generateGltAttemptsIntersectionReport(HttpServletResponse response)
        throws IOException {

        List<GltAttemptProjection> gltAttemptProjections;

        gltAttemptProjections = gltAttemptRepository
            .findGltAttemptsIntersection();

        formatProjectionIntersectionReportText(response, gltAttemptProjections);
    }

    @Override
    public List<GltAttemptProjection> getGltAttemptsIntersectionJson() {

        List<GltAttemptProjection> gltAttemptProjections;

        gltAttemptProjections = gltAttemptRepository
            .findGltAttemptsIntersection();

        return gltAttemptProjections;
    }

    @Override
    public void generateGltAttemptsUnionReport(HttpServletResponse response) throws IOException {
        List<GltAttemptProjection> gltAttemptProjections;

        gltAttemptProjections = gltAttemptRepository
            .findGltAttemptsUnion();

        formatProjectionUnionReportText(response, gltAttemptProjections);
    }

    @Override
    public List<GltAttemptProjection> getGltAttemptsUnionJson() {
        List<GltAttemptProjection> gltAttemptProjections;

        gltAttemptProjections = gltAttemptRepository
            .findGltAttemptsUnion();

        return gltAttemptProjections;
    }

    private void gltAttemptsByAttemptWithWorkUnit(HttpServletResponse response,
                                                  String reportType,
                                                  String attempt,
                                                  String workUnit,
                                                  String startYear,
                                                  String endYear,
                                                  String startMonth,
                                                  String endMonth)
        throws IOException, ParseException {
        if ("year".equalsIgnoreCase(reportType)) {
            gltAttemptsByAttemptWithWorkUnitForYear(response, reportType, attempt, workUnit,
                startYear, endYear);

        } else if ("month".equalsIgnoreCase(reportType)) {
            gltAttemptsByAttemptWithWorkUnitForMonth(response, reportType, attempt, workUnit,
                startYear, endYear, startMonth,
                endMonth);
        }
    }

    private List<GltAttemptProjection> gltAttemptsByAttemptWithWorkUnitJson(String reportType,
                                                                            String attempt,
                                                                            String workUnit,
                                                                            String startYear,
                                                                            String endYear,
                                                                            String startMonth,
                                                                            String endMonth)
        throws IOException, ParseException {
        if ("year".equalsIgnoreCase(reportType)) {
            return gltAttemptsByAttemptWithWorkUnitForYearJson(reportType, attempt, workUnit,
                startYear, endYear);

        } else if ("month".equalsIgnoreCase(reportType)) {
            return gltAttemptsByAttemptWithWorkUnitForMonthJson(reportType, attempt, workUnit,
                startYear, endYear, startMonth,
                endMonth);
        }

        return null;
    }

    private void gltAttemptsByAttemptWithWorkUnitForYear(HttpServletResponse response,
                                                         String reportType,
                                                         String attempt,
                                                         String workUnit,
                                                         String startYear,
                                                         String endYear)
        throws IOException, ParseException {
        List<GltAttemptProjection> gltAttemptProjections;


        Timestamp startTimestamp = getStartDate(startYear, "");
        Timestamp endTimestamp = getEndDate(endYear, "");

        gltAttemptProjections = gltAttemptRepository
            .findGltAttemptsByAttemptTypeByWorkUnitWithYear(attempt, workUnit, startTimestamp,
                endTimestamp);

        formatProjectionReportText(response, reportType, attempt,
            gltAttemptProjections, "workUnitYear");
    }

    private List<GltAttemptProjection> gltAttemptsByAttemptWithWorkUnitForYearJson(
        String reportType,
        String attempt,
        String workUnit,
        String startYear,
        String endYear)
        throws IOException, ParseException {


        Timestamp startTimestamp = getStartDate(startYear, "");
        Timestamp endTimestamp = getEndDate(endYear, "");

        return gltAttemptRepository
            .findGltAttemptsByAttemptTypeByWorkUnitWithYear(attempt, workUnit, startTimestamp,
                endTimestamp);

    }

    private void gltAttemptsByAttemptWithWorkUnitForMonth(HttpServletResponse response,
                                                          String reportType,
                                                          String attempt,
                                                          String workUnit,
                                                          String startYear,
                                                          String endYear,
                                                          String startMonth,
                                                          String endMonth)
        throws IOException, ParseException {
        List<GltAttemptProjection> gltAttemptProjections;

        Timestamp startTimestamp = getStartDate(startYear, startMonth);
        Timestamp endTimestamp = getEndDate(endYear, endMonth);

        gltAttemptProjections =
            gltAttemptRepository
                .findGltAttemptsByAttemptTypeByWorkUnitWithMonth(attempt, workUnit, startTimestamp,
                    endTimestamp);

        formatProjectionReportText(response, reportType, attempt,
            gltAttemptProjections, "workUnitYearMonth");
    }

    private List<GltAttemptProjection> gltAttemptsByAttemptWithWorkUnitForMonthJson(
        String reportType,
        String attempt,
        String workUnit,
        String startYear,
        String endYear,
        String startMonth,
        String endMonth)
        throws IOException, ParseException {

        Timestamp startTimestamp = getStartDate(startYear, startMonth);
        Timestamp endTimestamp = getEndDate(endYear, endMonth);

        return
            gltAttemptRepository
                .findGltAttemptsByAttemptTypeByWorkUnitWithMonth(attempt, workUnit, startTimestamp,
                    endTimestamp);

    }

    private void gltAttemptsByAttemptWithWorkUnitWorkGroup(HttpServletResponse response,
                                                           String reportType,
                                                           String attempt,
                                                           String workUnit,
                                                           String workGroup,
                                                           String startYear,
                                                           String endYear,
                                                           String startMonth,
                                                           String endMonth)
        throws IOException, ParseException {

        if ("year".equalsIgnoreCase(reportType)) {
            gltAttemptsByAttemptWithWorkUnitWorkGroupForYear(response, reportType, attempt,
                workUnit, workGroup,
                startYear, endYear);

        } else if ("month".equalsIgnoreCase(reportType)) {
            gltAttemptsByAttemptWithWorkUnitWorkGroupForMonth(response, reportType, attempt,
                workUnit, workGroup,
                startYear, endYear, startMonth,
                endMonth);
        }
    }


    private List<GltAttemptProjection> gltAttemptsByAttemptWithWorkUnitWorkGroupJson(
        String reportType,
        String attempt,
        String workUnit,
        String workGroup,
        String startYear,
        String endYear,
        String startMonth,
        String endMonth)
        throws IOException, ParseException {

        if ("year".equalsIgnoreCase(reportType)) {
            return gltAttemptsByAttemptWithWorkUnitWorkGroupForYearJson(reportType, attempt,
                workUnit, workGroup,
                startYear, endYear);

        } else if ("month".equalsIgnoreCase(reportType)) {
            return gltAttemptsByAttemptWithWorkUnitWorkGroupForMonthJson(reportType, attempt,
                workUnit, workGroup,
                startYear, endYear, startMonth,
                endMonth);
        }

        return null;
    }

    private void gltAttemptsByAttemptWithWorkUnitWorkGroupForYear(HttpServletResponse response,
                                                                  String reportType,
                                                                  String attempt,
                                                                  String workUnit,
                                                                  String workGroup,
                                                                  String startYear,
                                                                  String endYear)
        throws IOException, ParseException {
        List<GltAttemptProjection> gltAttemptProjections;


        Timestamp startTimestamp = getStartDate(startYear, "");
        Timestamp endTimestamp = getEndDate(endYear, "");

        gltAttemptProjections = gltAttemptRepository
            .findGltAttemptsByAttemptTypeByWorkUnitWorkGroupWithYear(attempt, workUnit, workGroup,
                startTimestamp,
                endTimestamp);

        formatProjectionReportText(response, reportType, attempt,
            gltAttemptProjections, "workUnitWorkGroupYear");
    }

    private List<GltAttemptProjection> gltAttemptsByAttemptWithWorkUnitWorkGroupForYearJson(
        String reportType,
        String attempt,
        String workUnit,
        String workGroup,
        String startYear,
        String endYear)
        throws IOException, ParseException {


        Timestamp startTimestamp = getStartDate(startYear, "");
        Timestamp endTimestamp = getEndDate(endYear, "");

        return gltAttemptRepository
            .findGltAttemptsByAttemptTypeByWorkUnitWorkGroupWithYear(attempt, workUnit, workGroup,
                startTimestamp,
                endTimestamp);

    }

    private void gltAttemptsByAttemptWithWorkUnitWorkGroupForMonth(HttpServletResponse response,
                                                                   String reportType,
                                                                   String attempt,
                                                                   String workUnit,
                                                                   String workGroup,
                                                                   String startYear,
                                                                   String endYear,
                                                                   String startMonth,
                                                                   String endMonth)
        throws IOException, ParseException {
        List<GltAttemptProjection> gltAttemptProjections;

        Timestamp startTimestamp = getStartDate(startYear, startMonth);
        Timestamp endTimestamp = getEndDate(endYear, endMonth);

        gltAttemptProjections =
            gltAttemptRepository
                .findGltAttemptsByAttemptTypeByWorkUnitWorkGroupWithMonth(attempt, workUnit,
                    workGroup, startTimestamp,
                    endTimestamp);

        formatProjectionReportText(response, reportType, attempt,
            gltAttemptProjections, "workUnitWorkGroupYearMonth");
    }

    private List<GltAttemptProjection> gltAttemptsByAttemptWithWorkUnitWorkGroupForMonthJson(
        String reportType,
        String attempt,
        String workUnit,
        String workGroup,
        String startYear,
        String endYear,
        String startMonth,
        String endMonth)
        throws IOException, ParseException {

        Timestamp startTimestamp = getStartDate(startYear, startMonth);
        Timestamp endTimestamp = getEndDate(endYear, endMonth);

        return
            gltAttemptRepository
                .findGltAttemptsByAttemptTypeByWorkUnitWorkGroupWithMonth(attempt, workUnit,
                    workGroup, startTimestamp,
                    endTimestamp);

    }

    private void gltAttemptsByAttemptWithoutWorkUnit(HttpServletResponse response,
                                                     String reportType,
                                                     String attempt,
                                                     String startYear,
                                                     String endYear,
                                                     String startMonth,
                                                     String endMonth)
        throws IOException, ParseException {

        List<GltAttemptProjection>
            gltAttemptProjectionsResult = new ArrayList<>();

        if ("year".equalsIgnoreCase(reportType)) {

            gltAttemptsByAttemptWithoutWorkUnitForYear(response, reportType, attempt, startYear,
                endYear,
                gltAttemptProjectionsResult);

        } else if ("month".equalsIgnoreCase(reportType)) {
            gltAttemptsByAttemptWithoutWorkUnitForMonth(response, reportType, attempt, startYear,
                endYear, startMonth, endMonth,
                gltAttemptProjectionsResult);
        }
    }

    private List<GltAttemptProjection> gltAttemptsByAttemptWithoutWorkUnitJson(String reportType,
                                                                               String attempt,
                                                                               String startYear,
                                                                               String endYear,
                                                                               String startMonth,
                                                                               String endMonth)
        throws IOException, ParseException {

        List<GltAttemptProjection>
            gltAttemptProjectionsResult = new ArrayList<>();

        if ("year".equalsIgnoreCase(reportType)) {

            return gltAttemptsByAttemptWithoutWorkUnitForYearJson(reportType, attempt, startYear,
                endYear,
                gltAttemptProjectionsResult);

        } else if ("month".equalsIgnoreCase(reportType)) {
            return gltAttemptsByAttemptWithoutWorkUnitForMonthJson(reportType, attempt, startYear,
                endYear, startMonth, endMonth,
                gltAttemptProjectionsResult);
        }

        return null;
    }


    private void gltAttemptsByAttemptWithoutWorkUnitForYear(HttpServletResponse response,
                                                            String reportType,
                                                            String attempt,
                                                            String startYear,
                                                            String endYear,
                                                            List<GltAttemptProjection> gltAttemptProjectionsResult)
        throws IOException, ParseException {


        List<GltAttemptProjection> gltAttemptProjections;

        Timestamp startTimestamp = getStartDate(startYear, "");
        Timestamp endTimestamp = getEndDate(endYear, "");

        gltAttemptProjections = gltAttemptRepository
            .findGltAttemptsByAttemptTypeWithYear(attempt,
                startTimestamp,
                endTimestamp);

        formatProjectionReportText(response, reportType, attempt,
            gltAttemptProjections, "Year");

    }

    private List<GltAttemptProjection> gltAttemptsByAttemptWithoutWorkUnitForYearJson(
        String reportType,
        String attempt,
        String startYear,
        String endYear,
        List<GltAttemptProjection> gltAttemptProjectionsResult)
        throws IOException, ParseException {


        Timestamp startTimestamp = getStartDate(startYear, "");
        Timestamp endTimestamp = getEndDate(endYear, "");

        return gltAttemptRepository
            .findGltAttemptsByAttemptTypeWithYear(attempt,
                startTimestamp,
                endTimestamp);


    }

    private void gltAttemptsByAttemptWithoutWorkUnitForMonth(HttpServletResponse response,
                                                             String reportType,
                                                             String attempt,
                                                             String startYear,
                                                             String endYear,
                                                             String startMonth,
                                                             String endMonth,
                                                             List<GltAttemptProjection> gltAttemptProjectionsResult)
        throws IOException, ParseException {


        List<GltAttemptProjection> gltAttemptProjections;

        Timestamp startTimestamp = getStartDate(startYear, startMonth);
        Timestamp endTimestamp = getEndDate(endYear, endMonth);

        gltAttemptProjections = gltAttemptRepository
            .findGltAttemptsByAttemptTypeWithMonth(attempt,
                startTimestamp,
                endTimestamp);

        formatProjectionReportText(response, reportType, attempt,
            gltAttemptProjections, "YearMonth");

    }

    private List<GltAttemptProjection> gltAttemptsByAttemptWithoutWorkUnitForMonthJson(
        String reportType,
        String attempt,
        String startYear,
        String endYear,
        String startMonth,
        String endMonth,
        List<GltAttemptProjection> gltAttemptProjectionsResult)
        throws IOException, ParseException {

        Timestamp startTimestamp = getStartDate(startYear, startMonth);
        Timestamp endTimestamp = getEndDate(endYear, endMonth);

        return gltAttemptRepository
            .findGltAttemptsByAttemptTypeWithMonth(attempt,
                startTimestamp,
                endTimestamp);

    }

    private void formatProjectionReportText(HttpServletResponse response,
                                            String reportType,
                                            String attempt,
                                            List<GltAttemptProjection> gltAttemptProjections,
                                            String tsvType)
        throws IOException {
        StringBuilder reportText = new StringBuilder();
        switch (tsvType) {
            case "workUnitYearMonth" -> reportText.append("Work Unit\tYear\tMonth\tSum\n");
            case "workUnitYear" -> reportText.append("Work Unit\tYear\tSum\n");
            case "workUnitWorkGroupYearMonth" -> reportText
                .append("Work Unit\tWork Group\tMonth\tYear\tSum\n");
            case "workUnitWorkGroupYear" -> reportText.append("Work Unit\tWork Group\tYear\tSum\n");
            case "YearMonth" -> reportText.append("Year\tMonth\tSum\n");
            case "Year" -> reportText.append("Year\tSum\n");
        }


        for (GltAttemptProjection gltAttemptProjection : gltAttemptProjections) {
            if (tsvType.equals("workUnitYearMonth") || tsvType.equals("workUnitYear") ||
                tsvType.equals("workUnitWorkGroupYearMonth") ||
                tsvType.equals("workUnitWorkGroupYear")) {
                reportText.append(gltAttemptProjection.getWorkUnitName()).append("\t");

                if (tsvType.equals("workUnitWorkGroupYearMonth") ||
                    tsvType.equals("workUnitWorkGroupYear")) {
                    reportText.append(gltAttemptProjection.getWorkGroupName()).append("\t");
                    if (tsvType.equals("workUnitWorkGroupYearMonth")) {
                        reportText.append(gltAttemptProjection.getMonth())
                            .append("\t");
                    }
                }
            }


            reportText.append(gltAttemptProjection.getYear());
            reportText.append("\t");

            if (tsvType.equals("YearMonth") || tsvType.equals("workUnitYearMonth")) {
                reportText.append(gltAttemptProjection.getMonth())
                    .append("\t");
            }
            reportText.append(gltAttemptProjection.getSum());
            reportText.append("\n");

        }
        Report report = new Report();
        report.setReport(reportText.toString());
        report.setCreatedAt(LocalDateTime.now());
        reportService
            .writeReport(response, "Production_Numbers_" + reportType + "_" + attempt,
                report);
    }

    private void formatProjectionIntersectionReportText(HttpServletResponse response,
                                                        List<GltAttemptProjection> gltAttemptProjections)
        throws IOException {
        StringBuilder reportText = new StringBuilder();
        reportText.append("Symbol\n");


        for (GltAttemptProjection gltAttemptProjection : gltAttemptProjections) {
            reportText.append(gltAttemptProjection.getSymbol()).append("\n");

        }
        Report report = new Report();
        report.setReport(reportText.toString());
        report.setCreatedAt(LocalDateTime.now());
        reportService
            .writeReport(response, "Production_Numbers_Intersection",
                report);
    }

    private void formatProjectionUnionReportText(HttpServletResponse response,
                                                 List<GltAttemptProjection> gltAttemptProjections)
        throws IOException {
        StringBuilder reportText = new StringBuilder();
        reportText.append("count\n");


        for (GltAttemptProjection gltAttemptProjection : gltAttemptProjections) {
            reportText.append(gltAttemptProjection.getCount());

        }
        Report report = new Report();
        report.setReport(reportText.toString());
        report.setCreatedAt(LocalDateTime.now());
        reportService
            .writeReport(response, "Production_Numbers_Union",
                report);
    }

    private Timestamp getEndDate(String endYear,
                                 String endMonth)
        throws ParseException {
        String endDateString = "";
        if (endYear == null || endYear.equals("")) {
            endDateString = "3000-12-31";
        } else {
            if (endMonth == null || endMonth.equals("")) {
                endDateString = endYear + "-12-31";
            } else {
                endDateString = endYear + "-" + endMonth + "-31";
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = dateFormat.parse(endDateString);
        return new Timestamp(endDate.getTime());
    }

    private Timestamp getStartDate(String startYear,
                                   String startMonth)
        throws ParseException {
        String startDateString = "";
        if (startYear == null || startYear.equals("")) {
            startDateString = "1000-01-01";
        } else {

            if (startMonth == null || startMonth.equals("")) {
                startDateString = startYear + "-01-01";
            } else {
                startDateString = startYear + "-" + startMonth + "-01";
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse(startDateString);
        return new Timestamp(startDate.getTime());
    }
}