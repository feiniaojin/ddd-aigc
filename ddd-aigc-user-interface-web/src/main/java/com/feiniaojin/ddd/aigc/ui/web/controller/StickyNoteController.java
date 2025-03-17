package com.feiniaojin.ddd.aigc.ui.web.controller;

import com.feiniaojin.ddd.aigc.application.service.stickynote.StickyNoteCommandApplicationService;
import com.feiniaojin.ddd.aigc.application.service.stickynote.StickyNoteQueryApplicationService;
import com.feiniaojin.ddd.aigc.application.service.stickynote.dto.*;
import com.feiniaojin.gracefulresponse.api.ExcludeFromGracefulResponse;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@RestController
@RequestMapping("/stickyNote")
public class StickyNoteController {

    @Resource
    private StickyNoteCommandApplicationService commandApplicationService;

    @Resource
    private StickyNoteQueryApplicationService queryApplicationService;

    private final Executor asyncExecutor = Executors.newFixedThreadPool(4);


    @RequestMapping("/generateDiaryContent")
    public StickyNoteGenerateContentView generateDiaryContent(StickyNoteGenerateContentQuery query) {
        return queryApplicationService.generateDiaryContent(query);
    }

    @RequestMapping("/create")
    public StickyNoteCreateView create(@RequestBody StickyNoteCreateCommand command) {
        return commandApplicationService.createStickyNote(command);
    }

    @RequestMapping("/modify")
    public void modify(@RequestBody StickyNoteModifyCommand command) {
        commandApplicationService.modifyStickyNote(command);
    }

    /**
     * 这个接口阻塞和非阻塞混用，仅用于演示
     * 建议使用webflux+r2dbc
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/generateDiaryContentSse",
            produces = "text/event-stream")
    public SseEmitter generateDiaryContentSse(StickyNoteGenerateContentQuery query) {
        SseEmitter emitter = new SseEmitterUTF8(120_000L);
        Flux<String> stringFlux = queryApplicationService.generateDiaryContentStream(query);
        asyncExecutor.execute(() -> {
            stringFlux.subscribe(data -> {
                        try {
                            emitter.send(data);
                        } catch (IOException e) {
                            emitter.completeWithError(e);
                        }
                    },
                    emitter::completeWithError,
                    emitter::complete);
        });
        return emitter;
    }

    public static class SseEmitterUTF8 extends SseEmitter {
        public SseEmitterUTF8(Long timeout) {
            super(timeout);
        }

        @Override
        protected void extendResponse(ServerHttpResponse response) {
            super.extendResponse(response);
            HttpHeaders headers = response.getHeaders();
            // 强制设置媒体类型与编码
            headers.setContentType(
                    new MediaType("text", "event-stream", StandardCharsets.UTF_8)
            );
        }
    }


    /**
     * 这个接口阻塞和非阻塞混用，仅用于演示
     * 建议使用webflux+r2dbc
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/generateDiaryContentFlux", produces = "text/html;charset=utf-8")
//    @RequestMapping(value = "/generateDiaryContentFlux", produces = "text/event-stream;charset=utf-8")
    @ExcludeFromGracefulResponse
    public Flux<String> generateDiaryContentFlux(StickyNoteGenerateContentQuery query) {
        return queryApplicationService.generateDiaryContentStream(query);
    }
}
