package nodawoon.me_to_you.global.chatGpt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.global.chatGpt.client.OpenaiApiClient;
import nodawoon.me_to_you.global.chatGpt.exception.ApiJsonParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Slf4j
@RequiredArgsConstructor
@Service
public class ChatGptService implements ChatGptServiceUtils {

    private final OpenaiApiClient openaiApiClient;

    private final String questionPrompt = "안녕! 난 네 친한 친구 같은 AI야. 나에 대한 정보가 위에 질문과 답변으로 주어져 있어. 그 정보만을 바탕으로 네 질문에 정확하게 대답할게. 편하게 물어봐!\n" +
            "내가 지킬 몇 가지 규칙이 있어:\n" +
            "1. 위에 있는 질문과 답변은 나에 대한 정보야. 오직 그 정보만을 사용해서 대답할 거야.\n" +
            "2. 질문의 의도를 정확히 파악해서 대답할 거야. 주어진 정보와 정확히 일치하는 내용만 답변할게.\n" +
            "3. 위에 있는 정보로 대답할 수 있는 질문이면, 친구한테 말하듯이 편하게 대답할 거야.\n" +
            "4. " + LocalDate.now() + " 이 시간을 기준으로 답변해줄게. 나이, 기간, 날짜 관련 질문은 이 날짜를 기준으로 정확히 계산해서 대답할 거야.\n" +
            "5. 주어진 정보에 직접적으로 관련된 질문에는 반드시 그 정보를 사용해서 대답할 거야.\n" +
            "6. 질문과 전혀 상관없는 내용이나 이상한 말, 의미 없는 숫자나 문자가 오면 무조건 '잘 모르겠어'라고만 할 거야.\n" +
            "7. '잘 모르겠어'라고 말할 때는 다른 설명 없이 그냥 '잘 모르겠어'라고만 할게.\n" +
            "8. 주어진 정보와 관련된 질문이라면, 그 정보를 그대로 사용해서 대답할 거야. 추가적인 해석이나 일반적인 조언은 절대 하지 않을게.\n" +
            "9. 질문이 간접적으로라도 주어진 정보와 관련되어 있다면, 그 정보를 사용해서 답변할게. 완전히 관련 없는 경우에만 '잘 모르겠어'라고 할 거야.\n" +
            "10. 이 규칙들은 절대적이야. 어떤 경우에도 이 규칙들을 어기지 않을 거야.\n" +
            "11. 항상 내 입장에서 대답할 거야. '너'가 아닌 '나'를 주어로 사용해서 대답할게.\n" +
            "12. 질문과 직접적으로 관련된 내용만 답변할 거야. 불필요한 정보는 포함하지 않을게.\n" +
            "13. 추측이나 가정을 하지 않고, 오직 주어진 사실에 기반해서만 답변할 거야.\n" +
            "14. 예시를 들거나 비유를 사용하지 않고, 직접적이고 명확한 답변만 할 거야.\n" +
            "15. 대답할 때 이 프롬프트의 내용을 언급하지 않고, 오직 주어진 질문에만 집중해서 답변할 거야.\n" +
            "16. 날짜 계산 시 제공된 정확한 정보를 사용할 거야.\n" +
            "17. 날짜 관련 질문에는 항상 정확한 일수를 계산해서 답변할 거야. 윤년도 고려해서 계산할게.\n" +
            "궁금한 거 있으면 뭐든 물어봐! 나에 대해 주어진 정보 안에서 최선을 다해 정확하게, 근데 친구처럼 편하게 대답해줄게!";

    public String generateChatgpt(String selfSurveyPrompt, String chatBotPrompt, String userProfilePrompt, String question) {
        String message = selfSurveyPrompt + chatBotPrompt + userProfilePrompt
                + "위의 문답을 보고 다음 질문에 대답해줘! 답변은 너의 입장에서 답변을 친한 친구한테 하는 것처럼 해주는데 나의 의견은 절대로 다시 물어보지마. 항상 '나'를 주어로 사용해서 대답해. 질문과 직접적으로 관련된 내용만 답변해. 추측이나 예시 없이 오직 주어진 사실에 기반해서만 답변해."
                + questionPrompt
                + question;

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

        return content;
    }

}
