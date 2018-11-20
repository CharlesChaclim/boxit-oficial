package com.uem.boxit.controller;

import com.uem.boxit.dto.RelatorioDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@RestController
@RequestMapping(
        value = "/relatorio",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class RelatorioController {

    @PostMapping
    public void mostraRelatorio(@RequestBody RelatorioDTO dto) {
        String caminho = new File("./").getAbsolutePath();
        caminho = caminho.substring(0, caminho.length() - 1);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat vdate = new SimpleDateFormat("dd/MM/yyyy");
        String start = date.format(dto.getDataInicio());
        String end = date.format(dto.getDataFim());
        String vstart = vdate.format(dto.getDataInicio());
        String vend = vdate.format(dto.getDataFim());
        HashMap parametros = new HashMap();
        parametros.put("start",start);
        parametros.put("end",end);
        parametros.put("vstart",vstart);
        parametros.put("vend",vend);
        JasperPrint print;
        if (dto.getTipo() == 2) {
            try {
                print = JasperFillManager.fillReport(caminho+"RelatorioEstoque.jasper", parametros, Conexao.getConnection());
                JRPdfExporter exporter = new JRPdfExporter();
                ExporterInput exporterInput = new SimpleExporterInput(print);
                exporter.setExporterInput(exporterInput);
                OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho+"RelatorioEstoque.pdf");
                exporter.setExporterOutput(exporterOutput);
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            } catch (JRException e) {
                e.printStackTrace();
            }
        } else if (dto.getTipo() == 3) {
            parametros.put("CNPJ", dto.getCNPJ());
            try {
                print = JasperFillManager.fillReport(caminho+"HistoricoComprador.jasper", parametros, Conexao.getConnection());
                JRPdfExporter exporter = new JRPdfExporter();
                ExporterInput exporterInput = new SimpleExporterInput(print);
                exporter.setExporterInput(exporterInput);
                OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho+"HistoricoComprador.pdf");
                exporter.setExporterOutput(exporterOutput);
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            } catch (JRException e) {
                e.printStackTrace();
            }
        } else if (dto.getTipo() == 5) {
            try {
                print = JasperFillManager.fillReport(caminho+"RelatorioReceitas.jasper", parametros, Conexao.getConnection());
                JRPdfExporter exporter = new JRPdfExporter();
                ExporterInput exporterInput = new SimpleExporterInput(print);
                exporter.setExporterInput(exporterInput);
                OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho+"RelatorioReceitas.pdf");
                exporter.setExporterOutput(exporterOutput);
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
    }
}