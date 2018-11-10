package com.uem.boxit.controller;

import com.uem.boxit.dto.RelatorioDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@RestController
@RequestMapping(
        value = "/relatorio",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class RelatorioController {

    @PutMapping
    public void mostraRelatorio(@RequestBody RelatorioDTO dto) {
        System.out.println("eba");
        Connection conexao = Conexao.conector();
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
        JasperPrint print = null;
        /*
        if(dto.getTipo() == 3) {
            parametros.put("CNPJ", dto.getCNPJ());
            print = JasperFillManager.fillReport("C:\\Users\\Jukios\\Desktop\\boxit-oficial\\api\\src\\main\\java\\com\\uem\\boxit\\controller\\historicoComprador.jasper", parametros, conexao);
        }
        JasperViewer.viewReport(print, false);
        */
    }
}