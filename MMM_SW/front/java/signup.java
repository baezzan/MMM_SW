import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "아이디 형식: 영문자로 시작하는 영문자 또는 숫자 6-20");
            return;
        }
        if (!Pattern.matches(passwordRegExp, password)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "비밀번호 형식: 8-16 문자, 숫자 조합");
            return;
        }
        if (!Pattern.matches(nicknameRegExp, nickname)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "닉네임 형식 2-10 한글, 숫자 또는 영문");
            return;
        }

        // 3. 회원가입 API 요청 (여기서는 가정하여 바로 응답 생성)
        boolean isSignUpSuccess = true; // 실제로는 여기서 회원가입 로직 수행

        // 4. 요청이 성공적이지 않다면, 에러 응답
        if (!isSignUpSuccess) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "요청에 문제가 생겼습니다.");
            return;
        }

        // 5. 요청이 성공하면, jwt를 localstorage에 저장하고 main page 이동
        // (서블릿에서는 클라이언트의 local storage에 접근할 수 없으므로, 응답으로 JWT를 보냄)
        String jwt = "dummy-jwt-token"; // 실제로는 여기서 JWT 생성
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(new ObjectMapper().writeValueAsString(new SignupResponse(jwt, "회원가입이 성공적으로 완료되었습니다.")));
        out.flush();
    }

    static class SignupResponse {
        public String jwt;
        public String message;

        public SignupResponse(String jwt, String message) {
            this.jwt = jwt;
            this.message = message;
        }
    }
}
