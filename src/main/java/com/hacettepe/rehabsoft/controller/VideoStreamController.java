package com.hacettepe.rehabsoft.controller;

import com.hacettepe.rehabsoft.service.implementations.VideoStreamService;
import com.hacettepe.rehabsoft.util.VideoStreamConstants.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/video")
public class VideoStreamController {

    private final VideoStreamService videoStreamService;

    public VideoStreamController(VideoStreamService videoStreamService) {
        this.videoStreamService = videoStreamService;
    }

    @GetMapping("/stream/{fileType}/{fileName}")
    public Mono<ResponseEntity<byte[]>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("fileType") String fileType,
                                                    @PathVariable("fileName") String fileName) {

        log.warn("Video stream controller'a girdi");
        return Mono.just(videoStreamService.prepareContent(fileName, fileType, httpRangeList));
    }
}

