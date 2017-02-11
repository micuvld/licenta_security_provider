package security;

import ldapConnection.Connector;
import ldapConnection.LoginService;
import org.forgerock.opendj.ldap.Entry;
import org.forgerock.opendj.ldap.LdapException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.AuthenticationStatus;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vlad on 02.12.2016.
 */
@WebServlet("/patients")
public class Patients extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*String requesterDn = request.getParameter("requesterDn");

        JSONObject responseJson;
        JSONArray responseJsonArray = new JSONArray();

        Connector connector = Connector.getInstance();
        try {
            LoginService.masterLogin(connector, "qwerty");
        } catch (LdapException e) {
            e.printStackTrace();
        }

        ArrayList<Entry> searchedEntries = null;
        try {
            searchedEntries = connector.readEntires("owner=" + requesterDn);
            for (Entry e : searchedEntries) {
                responseJson = new JSONObject();
                responseJson.put("member", e.getAttribute("member").firstValueAsString());
                responseJsonArray.add(responseJson);
            }
            response.getWriter().write(responseJsonArray.toJSONString());
        } catch (LdapException e) {
            response.getWriter().write("ERROR - LdapException");
        }
        */
        response.getWriter().write("HI");
    }
}