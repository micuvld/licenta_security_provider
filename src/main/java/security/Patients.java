package security;

import ldapConnection.IConnector;
import ldapConnection.MasterConnector;
import org.forgerock.opendj.ldap.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        String requesterDn = request.getParameter("requesterDn");

        JSONObject responseJson;
        JSONArray responseJsonArray = new JSONArray();

        IConnector connector = MasterConnector.getInstance();

        ArrayList<Entry> searchedEntries = null;
            searchedEntries = connector.readEntires("owner=" + requesterDn);
            for (Entry e : searchedEntries) {
                responseJson = new JSONObject();
                responseJson.put("member", e.getAttribute("member").firstValueAsString());
                responseJsonArray.add(responseJson);
            }
            response.getWriter().write(responseJsonArray.toJSONString());
    }
}