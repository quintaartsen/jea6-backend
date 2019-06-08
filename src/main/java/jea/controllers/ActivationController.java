package jea.controllers;

import jea.services.interfaces.IActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping(path = "activation")
public class ActivationController {

    private static final String REDIRECT_URL = "http://localhost:4200/login";
    private final IActivationService activationService;

    @Autowired
    public ActivationController(final IActivationService activationService) {
        this.activationService = activationService;
    }

    @GetMapping(path = "{entryId}")
    public @ResponseBody
    ResponseEntity visitActivationEntry(@PathVariable final UUID entryId) throws URISyntaxException {
        activationService.sendActivationEntryVisitNotice(entryId);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI(REDIRECT_URL));

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}