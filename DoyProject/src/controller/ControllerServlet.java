package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String val = request.getParameter("val");		//Intercetto il parametro val
		HttpSession session = request.getSession(true);	//Lavoro a livello di sessione
		User u = (User) session.getAttribute("user");	//Se già un utente lo prendo
		String path = "";								//path che indica la JSP dove voglio andare a seconda delle azioni
		
		
		//caso del signin
		if("signin".equals(val)){
			System.out.println("username che setto: " + request.getParameter("username"));
			
			u = new User(request.getParameter("username"));	//creo un oggetto utente con l'username passato
			
			//Se non si trova corrispondenza nel database (e lo fa la Servlet) oppure l'utente è nullo allora sto nel login
			if(!u.signinU(request.getParameter("password")) || u==null )
				path = "/WEB-INF/signinErr";
			//altrimenti vado alla homepage
			else {
				session.setAttribute("user", u);
				path="/WEB-INF/homepage";
			}
			
		}
		
		if("signup".equals(val)){
			path="/WEB-INF/signup";
			
		}
		
		//Prima di uscire dal post, raccolgo quello che ho seminato, e vado dove devo andare.
		String url = path + ".jsp";
		try{
			request.getRequestDispatcher(url).forward(request, response);
			
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}
