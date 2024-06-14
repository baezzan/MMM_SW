import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String URL = "https://www.seongong.shop/sign-up";
    private static final OkHttpClient client = new OkHttpClient();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. 파라미터 가져오기
        String userID = request.getParameter("userID");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");

        // 2. 정규표현식 확인
        String userIDRegExp = "^[a-z]+[a-z0-9]{5,19}$";
        String passwordRegExp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$";
        String nicknameRegExp = "^[가-힣|a-z|A-Z|0-9|]{2,10}$";

        if (!Pattern.matches(userIDRegExp, userID)) {
            sendError(response, "아이디 형식: 영문자로 시작하는 영문자 또는 숫자 6-20");
            return;
        }
        if (!Pattern.matches(passwordRegExp, password)) {
            sendError(response, "비밀번호 형식: 8-16 문자, 숫자 조합");
            return;
        }
        if (!Pattern.matches(nicknameRegExp, nickname)) {
            sendError(response, "닉네임 형식 2-10 한글, 숫자 또는 영문");
            return;
        }

        // 3. 회원가입 API 요청
        RequestBody formBody = new FormBody.Builder()
                .add("userID", userID)
                .add("password", password)
                .add("nickname", nickname)
                .build();
        Request apiRequest = new Request.Builder()
                .url(URL)
                .post(formBody)
                .build();

        try (Response apiResponse = client.newCall(apiRequest).execute()) {
            if (!apiResponse.isSuccessful()) {
                sendError(response, "요청에 문제가 생겼습니다.");
                return;
            }

            // 5. 요청이 성공하면, jwt를 localstorage에 저장하고 main page 이동
            String responseBody = apiResponse.body().string();
            SignupResponse signupResponse = new ObjectMapper().readValue(responseBody, SignupResponse.class);

            if (signupResponse.code != 200) {
                sendError(response, signupResponse.message);
                return;
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new ObjectMapper().writeValueAsString(signupResponse));
            out.flush();
        }
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"error\": \"" + message + "\"}");
        out.flush();
    }

    static class SignupResponse {
        public int code;
        public String result;
        public String jwt;
        public String message;
    }
}
