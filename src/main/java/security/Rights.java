package security;

import ldapConnection.IConnector;
import ldapConnection.MasterConnector;
import org.forgerock.opendj.ldap.Entry;
import org.forgerock.opendj.ldap.ErrorResultException;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vlad on 03.12.2016.
 */
@WebServlet("/rights")
public class Rights extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requesterDn = request.getParameter("requesterDn");
        String rightsRequesterDn = request.getParameter("rightsRequesterDn");
        String targetDn = request.getParameter("targetDn");

        JSONObject responseJson = new JSONObject();

        IConnector connector = MasterConnector.getInstance();

        Entry rightsRequesterEntry = null;
        try {
            rightsRequesterEntry = connector.readEntry(rightsRequesterDn);
        } catch (ErrorResultException e) {
            e.printStackTrace();
        }
        System.out.println(rightsRequesterEntry.getName());
        if (rightsRequesterEntry != null) {
            ArrayList<Entry> searchedEntries = null;
                searchedEntries = connector.readEntires("owner=" + rightsRequesterDn);
                for (Entry e : searchedEntries) {
                    if (e.getAttribute("member").contains(targetDn)) {
                        responseJson.put("status", "CONNECTION APPROVED");
                        response.getWriter().write(responseJson.toJSONString());
                    } else {
                        responseJson.put("status", "CONNECTION REFUSED - NO RIGHTS FOR THE REQUESTER");
                        response.getWriter().write(responseJson.toJSONString());
                    }

                }
        }
    }
}