package org.gentar.report.generation;

import org.gentar.report.collection.mgi_crispr_allele.MgiCrisprAlleleReportServiceImpl;
import org.gentar.report.collection.mgi_es_cell_allele.MgiEsCellAlleleServiceImpl;
import org.gentar.report.collection.mgi_modification_allele.MgiModificationAlleleServiceImpl;
import org.gentar.report.collection.mgi_phenotyping_colony.MgiPhenotypingColonyReportServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/tracking-api/reports/generate")
public class MgiReportController {

    private final MgiCrisprAlleleReportServiceImpl mgiCrisprAlleleService;
    private final MgiPhenotypingColonyReportServiceImpl mgiPhenotypingColonyReportService;
    private final MgiEsCellAlleleServiceImpl mgiEsCellAlleleService;
    private final MgiModificationAlleleServiceImpl mgiModificationAlleleService;

    public MgiReportController(MgiCrisprAlleleReportServiceImpl mgiCrisprAlleleService,
                               MgiPhenotypingColonyReportServiceImpl mgiPhenotypingColonyReportService,
                               MgiEsCellAlleleServiceImpl mgiEsCellAlleleService,
                               MgiModificationAlleleServiceImpl mgiModificationAlleleService){
        this.mgiCrisprAlleleService = mgiCrisprAlleleService;
        this.mgiPhenotypingColonyReportService = mgiPhenotypingColonyReportService;
        this.mgiEsCellAlleleService = mgiEsCellAlleleService;
        this.mgiModificationAlleleService = mgiModificationAlleleService;
    }


    @GetMapping("/mgi/phenotyping_colonies")
    public void exportCsv(HttpServletResponse response) throws IOException
    {
        mgiPhenotypingColonyReportService.generateMgiPhenotypingColonyReport();
    }


    @GetMapping("/mgi/crispr_alleles")
    public void exportCrisprAlleles(HttpServletResponse response) throws IOException
    {
        mgiCrisprAlleleService.generateMgiCrisprAlleleReport();
    }


    @GetMapping("/mgi/modification_alleles")
    public void exportModificationAlleles(HttpServletResponse response) throws IOException
    {
        mgiModificationAlleleService.generateMgiModificationAlleleReport();
    }


    @GetMapping("/mgi/es_cell_alleles")
    public void exportEsCellAlleles(HttpServletResponse response) throws IOException
    {
        mgiEsCellAlleleService.generateMgiEsCellAlleleReport();
    }
}
