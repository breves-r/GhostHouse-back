package com.infnet.ghosthouseautenticacao;

import com.infnet.ghosthouseautenticacao.domain.Locatario;
import com.infnet.ghosthouseautenticacao.domain.Proprietario;
import com.infnet.ghosthouseautenticacao.domain.Usuario;
import com.infnet.ghosthouseautenticacao.dto.LoginDto;
import com.infnet.ghosthouseautenticacao.dto.UsuarioRecordDto;
import com.infnet.ghosthouseautenticacao.service.LocatarioService;
import com.infnet.ghosthouseautenticacao.service.ProprietarioService;
import com.infnet.ghosthouseautenticacao.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ProprietarioService proprietarioService;
    private final LocatarioService locatarioService;
    private final UsuarioService usuarioService;

    public AuthController(ProprietarioService proprietarioService, LocatarioService locatarioService, UsuarioService usuarioService) {
        this.proprietarioService = proprietarioService;
        this.locatarioService = locatarioService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody @Valid LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.autenticar(loginDto));
    }

    @PostMapping("/proprietario")
    public ResponseEntity<Proprietario> createProprietario(@RequestBody @Valid UsuarioRecordDto usuarioRecordDto) {
        Proprietario proprietario = new Proprietario();
        BeanUtils.copyProperties(usuarioRecordDto, proprietario);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(proprietarioService.save(proprietario));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/locatario")
    public ResponseEntity<Locatario> createLocatario(@RequestBody @Valid UsuarioRecordDto usuarioRecordDto) {
        Locatario locatario = new Locatario();
        BeanUtils.copyProperties(usuarioRecordDto, locatario);

        return ResponseEntity.status(HttpStatus.CREATED).body(locatarioService.save(locatario));


    }
}
