package com.uem.boxit.controller;

import com.uem.boxit.dto.FotoDTO;
import com.uem.boxit.service.ProdutoService;
import com.uem.boxit.storage.S3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(
        value = "/produto",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class ProdutoController {

    @Autowired
    private S3 s3;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/foto")
    public FotoDTO uploadFoto(@RequestParam MultipartFile foto) throws IOException {
        String nome = s3.salvarTemporariamente(foto);
        return new FotoDTO(nome, s3.configurarUrl(nome));
    }
}
