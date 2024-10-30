package com.sample.spring.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		Gson gson = new Gson();
		
		String jsonStr = gson.toJson(Map.of("error","ERROR_ACCESS_TOKEN"));
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		
		PrintWriter printWriter = response.getWriter();
		printWriter.print(jsonStr);
		printWriter.close();
		
	}

}