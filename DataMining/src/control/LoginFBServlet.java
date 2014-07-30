package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class LoginFBServlet extends HttpServlet {
	String MY_FB_ACCESS_TOKEN = "CAACEdEose0cBAMV8aRXnNPn93xq1KtunWD6zc3e2on5H0tbl7ZBRZB7YqpepY7AgZCTR4GIZCWrYTZBd6TPNhmpdttVopUIOmK2DTwDa4bfZCzrPLKUlgXov3MKf1KWMUIzwUjb9OcyOulIDwJ2LJ34T973bQPh8N4l6oKL2bPc2exxyWYM4tZCdZBlTu8KEmW4VWsrJQH7ek8dFd9scbnwP";
	String MY_FB_APP_SECRET = "a8ce3774165d43d3bbf2b543afef0e65";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FacebookClient facebookClient = new DefaultFacebookClient(MY_FB_ACCESS_TOKEN);
		User me = facebookClient.fetchObject("me", User.class);
		System.out.println("User name: " + me.getName());
		
		Connection<User> friends = facebookClient.fetchConnection("me/friends", User.class);
		List<User> friendsList = friends.getData();
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Facebook</title>");
	    out.println("</head>");
	    out.println("<body>");
		out.println("Count of my friends: " + friendsList.size());
		out.println("<br/><br/>");
		for (int i=0;i<friendsList.size();i++){
			out.println("id:="+friendsList.get(i).getId()+", name="+friendsList.get(i).getName());
			out.println("<br/><br/>");
		}
	    out.println("</body>");
	    out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
