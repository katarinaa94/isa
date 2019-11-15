//package rs.ac.uns.ftn.informatika.spring.security.security.auth;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class CorsFilter implements Filter {
//
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//		final HttpServletResponse response = (HttpServletResponse) res;
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//		if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
//			response.setStatus(HttpServletResponse.SC_OK);
//		} else {
//			chain.doFilter(req, res);
//		}
//	}
//
//}
