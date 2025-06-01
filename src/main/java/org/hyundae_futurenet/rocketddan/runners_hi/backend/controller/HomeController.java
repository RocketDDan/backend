package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.repository.TestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final TestRepository testRepository;

    @GetMapping("/")
    public String home() {

        return testRepository.now();
    }
}
