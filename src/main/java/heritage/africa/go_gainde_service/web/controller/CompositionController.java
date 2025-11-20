package heritage.africa.go_gainde_service.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import heritage.africa.go_gainde_service.service.external.ExternalApiMockService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compositions")
public class CompositionController {

    private final ExternalApiMockService externalApiMockService;


    @GetMapping("")
    public String getCompositions() {
        throw new RuntimeException("test");
    }




    
}
