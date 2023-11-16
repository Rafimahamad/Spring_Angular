package com.project.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.securityconfigure.UserDetailServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	private JwtUtil jwtUtil;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
		System.out.println("request"+request);
		
	 String requestTokenHeader = request.getHeader("Authorization");
	
		String email=null;
		String jwtToken=null;
		
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer "))
		{
			
			//yes token valid
			
			jwtToken=requestTokenHeader.substring(7);
			
			System.out.println("request"+jwtToken);
			try
			{
				
				email = this.jwtUtil.getUserNameFromToken(jwtToken);
				
			}
			catch (ExpiredJwtException  e ) 
			{
				// TODO: handle exception
				
				System.out.println("jwt token is expired");
				e.printStackTrace();
			}
			catch (Exception  e ) 
			
			{
				// TODO: handle exception
				
				System.out.println("error");
				e.printStackTrace();
			}
			 
		
		
		
		//validate
		if(email !=null && SecurityContextHolder.getContext().getAuthentication()==null) 
		{
         UserDetails userDetails = this.userDetailServiceImpl.loadUserByUsername(email);
		
         if(this.jwtUtil.validateToken(jwtToken, userDetails))
			{
				//token is valid
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken( userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
				
		}
		else {
			System.out.println("Token is invalid");
		}
		

	}

		
filterChain.doFilter(request, response);
		
		System.out.println("program close");
	}
	
}

