package security;

import ldapConnection.IConnector;
import ldapConnection.MasterConnector;
import org.forgerock.opendj.ldap.Entry;
import org.forgerock.opendj.ldap.ErrorResultException;
import org.json.simple.JSONObject;
import user.AuthenticationStatus;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vlad on 15.02.2017.
 */
@WebServlet("/access")
public class AccessGranter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requesterDn = request.getParameter("requesterDn");
        String patientDn = request.getParameter("patientDn");

        JSONObject responseJson = new JSONObject();

        IConnector connector = MasterConnector.getInstance();

        //GRANT CHECK

        Entry searchedEntry = null;
        try {
            searchedEntry = connector.readEntry(requesterDn);
            responseJson.put("status", AuthenticationStatus.APPROVED.ordinal() + "");
            response.getWriter().write(responseJson.toJSONString());
        } catch (ErrorResultException e) {
            responseJson.put("status", AuthenticationStatus.DENIED.ordinal() + "");
            response.getWriter().write(responseJson.toJSONString());
        }
    }
}