package security;

import ldapConnection.Connector;
import ldapConnection.LoginService;
import org.forgerock.opendj.ldap.Entry;
import org.forgerock.opendj.ldap.LdapException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.PropertyLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vlad on 02.12.2016.
 */
@WebServlet("/gateway")
public class Gateway extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requesterDn = request.getParameter("requesterDn");
        String patientDn = request.getParameter("patientDn");

        JSONObject responseJson = new JSONObject();

        Connector connector = Connector.getInstance();
        try {
            LoginService.masterLogin(connector, "qwerty");
        } catch (LdapException e) {
            e.printStackTrace();
        }

        ArrayList<Entry> searchedEntries = null;
        try {
            searchedEntries = connector.readEntires("member=" + patientDn);
            System.out.println("member=" + patientDn + " ou=Groups");
            for (Entry e : searchedEntries) {
                String ownerDn = e.getAttribute("owner").firstValueAsString();
                Entry groupOwner = connector.readEntry(
                        e.getAttribute("owner").firstValueAsString());

                if (groupOwner != null) {
                    responseJson.put("gateway", ownerDn);
                    responseJson.put("hostname", "http://169.254.75.68:8080/GatewayHost");
                }
            }
            response.getWriter().write(responseJson.toJSONString());
        } catch (LdapException e) {
            response.getWriter().write("ERROR - LdapException");
        }
    }
}