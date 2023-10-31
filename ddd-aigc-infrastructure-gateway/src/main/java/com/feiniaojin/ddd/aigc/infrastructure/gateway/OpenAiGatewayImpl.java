package com.feiniaojin.ddd.aigc.infrastructure.gateway;

import com.feiniaojin.ddd.aigc.domain.OpenAiGateway;
import com.feiniaojin.ddd.aigc.domain.StickyNoteEntity;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

/**
 * open ai调用网关
 */
@Component
public class OpenAiGatewayImpl implements OpenAiGateway {

    @Resource
    private OpenAiConfig openAiConfig;

    private String promptTemplate = "根据以下提示，生成一篇日记。\n{0}";

    @Override
    public String generateContent(String uid, String input) {

        String token = openAiConfig.getToken();

        OpenAiService service = new OpenAiService(token, Duration.ofSeconds(30));
        String prompt = MessageFormat.format(promptTemplate, input);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt(prompt)
                .echo(false)
                .user(uid)
                .maxTokens(1024)
                .n(1)
                .build();
        CompletionResult completion = service.createCompletion(completionRequest);
        List<CompletionChoice> choices = completion.getChoices();
        CompletionChoice completionChoice = choices.get(0);
        String text = completionChoice.getText();
        service.shutdownExecutor();
        return text;
    }
}
