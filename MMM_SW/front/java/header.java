// 필요한 라이브러리 import
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
    
    private static final String JWT_URL = "http://example.com/jwt"; // 토큰 검증 API URL

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String jwt = (String) session.getAttribute("x-access-token");
        
        if (jwt == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        boolean isValidJwt = verifyJwt(jwt);
        
        if (!isValidJwt) {
            session.invalidate();
            response.sendRedirect("login.jsp");
            return;
        }
        
        // 로그인 상태 확인 및 헤더 설정
        JsonNode userNode = getUserInfoFromJwt(jwt);
        if (userNode != null) {
            String nickname = userNode.get("nickname").asText();
            request.setAttribute("nickname", nickname);
            request.getRequestDispatcher("/header.jsp").forward(request, response);
        } else {
            session.invalidate();
            response.sendRedirect("login.jsp");
        }
    }
    
    private boolean verifyJwt(String jwt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(JWT_URL)
            .addHeader("x-access-token", jwt)
            .build();
        
        try (Response jwtResponse = client.newCall(request).execute()) {
            if (jwtResponse.isSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(jwtResponse.body().string());
                return node.get("code").asInt() == 200;
            }
        }
        return false;
    }
    
    private JsonNode getUserInfoFromJwt(String jwt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(JWT_URL)
            .addHeader("x-access-token", jwt)
            .build();
        
        try (Response jwtResponse = client.newCall(request).execute()) {
            if (jwtResponse.isSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(jwtResponse.body().string());
                return node.get("result");
            }
        }
        return null;
    }
}

// 로그아웃 서블릿
@WebServlet("/sign-out")
public class SignOutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp");
    }
}
