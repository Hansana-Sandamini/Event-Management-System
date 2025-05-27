package lk.ijse.aad;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter(urlPatterns = "/*")
@WebServlet("/event")
public class EventServlet extends HttpServlet implements Filter {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/eventdb", "root", "Ijse@1234");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // eid, ename, edescription, edate, eplace
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM event");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map<String,String>> elist = new ArrayList<Map<String,String>>();

            while (resultSet.next()) {
                Map<String,String> event = new HashMap<String,String>();
                event.put("eid", resultSet.getString("eid"));
                event.put("ename", resultSet.getString("ename"));
                event.put("edescription", resultSet.getString("edescription"));
                event.put("edate", resultSet.getString("edate"));
                event.put("eplace", resultSet.getString("eplace"));
                elist.add(event);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), elist);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> event = mapper.readValue(req.getReader(), Map.class);

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO event VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, event.get("eid"));
            ps.setString(2, event.get("ename"));
            ps.setString(3, event.get("edescription"));
            ps.setString(4, event.get("edate"));
            ps.setString(5, event.get("eplace"));

            if (ps.executeUpdate() > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"data\":\"Successfully added event!\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Something went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> event = mapper.readValue(req.getReader(), Map.class);

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE event SET ename=?, edescription=?, edate=?, eplace=? WHERE eid=?");
            ps.setString(1, event.get("ename"));
            ps.setString(2, event.get("edescription"));
            ps.setString(3, event.get("edate"));
            ps.setString(4, event.get("eplace"));
            ps.setString(5, event.get("eid"));

            if (ps.executeUpdate() > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"data\":\"Successfully updated event!\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Something went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String eid = req.getParameter("eid");

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM event WHERE eid=?");
            ps.setString(1, eid);

            if (ps.executeUpdate() > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"data\":\"Successfully deleted event!\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Something went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "DELETE,PUT");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");
    }
}
