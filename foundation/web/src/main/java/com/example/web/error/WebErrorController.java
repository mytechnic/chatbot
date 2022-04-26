package com.example.web.error;

import com.example.domain.rest.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@Slf4j
@AllArgsConstructor
public class WebErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @RequestMapping(path = "/error")
    @ResponseBody
    public ResponseEntity<?> errorJSON(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> errorMap = errorAttributes.getErrorAttributes(new ServletWebRequest(request), getErrorAttributeOptions());

        log.debug("error: {}, url: [{}] {}", errorMap.get("error"), request.getMethod(), request.getRequestURL());

        String code;
        String message;
        HttpStatus httpStatus;
        if ("999".equals(errorMap.get("status").toString())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        httpStatus = HttpStatus.valueOf(Integer.parseInt(errorMap.get("status").toString()));
        code = httpStatus.name();
        message = ObjectUtils.isEmpty(request.getAttribute("javax.servlet.error.message"))
                ? httpStatus.getReasonPhrase() : request.getAttribute("javax.servlet.error.message").toString();
        String path = (String) errorMap.get("path");

        log.debug("error: {}, url: {}", errorMap, path);

        if (httpStatus != HttpStatus.NOT_FOUND) {
            httpStatus = HttpStatus.OK;
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(new ApiResponse<>(code, message), httpStatus);
    }

    private ErrorAttributeOptions getErrorAttributeOptions() {
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
        options = options.including(ErrorAttributeOptions.Include.STACK_TRACE);
        return options;
    }
}
