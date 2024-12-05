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

//    String r = "今天是一个美好的星期天，我参加了很多有意思的活动。早上11点，我和周顶一起去吃了当地的特色小吃一螺螂粉。这是我第一次尝试螺螂粉，味道真是让人大开眼界!我们还在那里闲聊了-会儿，谈论着最近的工作和生活。\n" +
//            "下午5点半，我又和朋友李君凯一起去吃了猪脚饭。猪脚饭的味道一如既往的美味，让我感受到家的温暖。我们还一起分享了最近遇到的困难和解决方法，互相鼓励和支持。\n" +
//            "晚上8点半，我去参加了一个很有意思的活动，和同行业的朋友一起分享互联网行业最新的动态高铁成、周顶、李君凯、周起仝都来了，我们一起讨论了最近的趋势和变化，并分享了自己的心得和体会。这次活动让我学习到了很多新的知识，也结交了一些新朋友。\n" +
//            "今天是一个充实又有意义的一天，我很庆幸能够和这些优秀的朋友一起分享生活和工作。希望我，们能够继续保持联系，共同进步，一起迎接更多的挑战。";
}
