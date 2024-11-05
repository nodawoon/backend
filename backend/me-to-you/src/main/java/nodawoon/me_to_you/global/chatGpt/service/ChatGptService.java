package nodawoon.me_to_you.global.chatGpt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.global.chatGpt.client.OpenaiApiClient;
import nodawoon.me_to_you.global.chatGpt.exception.ApiJsonParseException;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class ChatGptService implements ChatGptServiceUtils {

    private final OpenaiApiClient openaiApiClient;

    public String generateChatgpt() {
        long startTime = System.currentTimeMillis(); // 시작 시간 기록

        String message = "";

        String jsonResponse = openaiApiClient.sendMessage(message);
        String content = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray choicesArray = jsonObject.getJSONArray("choices");
            JSONObject choiceObject = choicesArray.getJSONObject(0);
            JSONObject messageObject = choiceObject.getJSONObject("message");
            content = messageObject.getString("content");
        } catch (JSONException e) {
            throw ApiJsonParseException.EXCEPTION;
        }

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 경과 시간 계산

        log.info("generateChatgpt 실행 시간: {} ms", duration); // 로그로 출력

        return content;
    }

}
