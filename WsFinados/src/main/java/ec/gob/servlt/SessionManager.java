package ec.gob.servlt;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SessionManager
 */
@WebServlet("/SessionManager")
public class SessionManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SessionManager() {
		super();        
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)  {		
		String tmpRequest = request.getParameter("request");
		if (tmpRequest != null && tmpRequest.equals("logout")){
			request.getSession().removeAttribute("logined");
			request.getSession().removeAttribute("context");			
			request.getSession().invalidate();
			try {
				response.sendRedirect(request.getContextPath());
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
	}

}
