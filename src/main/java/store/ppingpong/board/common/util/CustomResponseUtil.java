package store.ppingpong.board.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class CustomResponseUtil {
    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void fail(HttpServletResponse response, String responseBody, HttpStatus httpCode) {
        try {
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(httpCode.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }

    }

    public static void success(HttpServletResponse response, Object dto) {

        try {
            ObjectMapper om = new ObjectMapper();
            String responseBody = om.writeValueAsString(dto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }
}
