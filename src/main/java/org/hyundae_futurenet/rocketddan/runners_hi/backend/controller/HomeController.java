package org.hyundae_futurenet.rocketddan.runners_hi.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.repository.TestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Test API", description = "Test: 시간 목록")
public class HomeController {

    private final TestRepository testRepository;

    @Operation(summary = "시간 조회", description = "시간을 조회합니다.")
    @GetMapping("/")
    public String home() {

        return testRepository.now();
    }
}
