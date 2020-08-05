package Controller;

import Model.Shopper;
import Model.Supermarket;
import Model.Table;
import Writers.SQL_Reader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String name;
    private SQL_Reader sql_reader;

    private String[] parameters = {null, null, null, null, null, null};

    public void init() {
        name = "july_07_2020_created_07_08_2020_18_58_54";
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        sql_reader = new SQL_Reader(jdbcURL, jdbcUsername, jdbcPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertBook(request, response);
                    break;
                case "/list":
                    listShopper(request, response);
                    break;
                case"/setName":
                    setTableName(request, response);
                    break;
                default:
                    showHomeForm(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listShopper(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Shopper> listShopper = sql_reader.listAllShoppers(name, parameters);
        request.setAttribute("listShopper", listShopper);

        RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("table_creation.jsp");
        dispatcher.forward(request, response);
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Integer month = Integer.parseInt(request.getParameter("month"));
        Integer day = Integer.parseInt(request.getParameter("day"));
        Integer year = Integer.parseInt(request.getParameter("year"));
        Boolean weather = request.getParameter("weather").equals("y");

        Supermarket supermarket = new Supermarket();
        supermarket.creation(month, day, year, weather);
        response.sendRedirect("name");
    }

    private void showHomeForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Table> tableNames = sql_reader.listTables();
        request.setAttribute("listTables", tableNames);

        RequestDispatcher dispatcher = request.getRequestDispatcher("TableName.jsp");
        dispatcher.forward(request, response);
    }

    private void setTableName(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        name = request.getParameter("tableName");

        if(!request.getParameter("before").isEmpty()) {
            parameters[0] = request.getParameter("before");
        }

        if(!request.getParameter("after").isEmpty()) {
            parameters[1] = request.getParameter("after");
        }

        if(!request.getParameter("min").isEmpty()) {
            parameters[2] = request.getParameter("min");
        }

        if(!request.getParameter("max").isEmpty()) {
            parameters[3] = request.getParameter("max");
        }

        if(!request.getParameter("rush").isEmpty()) {
            parameters[4] = request.getParameter("rush");
        }

        if(!request.getParameter("senior").isEmpty()) {
            parameters[5] = request.getParameter("senior");
        }

        response.sendRedirect("list");
    }

}