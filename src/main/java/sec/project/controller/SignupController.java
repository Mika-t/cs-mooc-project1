package sec.project.controller;

import java.sql.Connection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

import java.sql.*;
import javax.websocket.Session;
import org.springframework.data.jpa.repository.JpaRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;
    
    @PostConstruct
    public void init() {
        // test data to the application
        Signup signup = new Signup("bob none", "bobs address");
        signupRepository.save(signup);
    }

    @RequestMapping("/")
    public String defaultMapping() {
        System.out.println("defaultmapping for /");
        return "redirect:/home";
    }
    @RequestMapping("/login")
    public String loginMapping() {
        System.out.println("login for /login");
        return "login";
    }
    @RequestMapping("/home")
    public String homeMapping() {
        System.out.println("home");
        return "home";
    }
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        System.out.println("GET "+signupRepository.findAll().toString());
        return "form";
    }
    


    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, Model model) throws SQLException {
        System.out.println("name: "+name);
        System.out.println("address: "+address);
        Signup signup=new Signup(name, address);
        
        signupRepository.save(signup);
        Long id = signup.getId();
       
        String query = "SELECT * FROM signups WHERE id='" + id + "'";   
        EntityManager em = null;
//        Query aquery = (Query) em.createQuery(query);
  
        
        // H2 DATABASE JUTTUNEN

        // Open connection
        
       
/*        Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "sa", "");

        // Execute query and retrieve the query results
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM signups");

        // Do something with the results -- here, we print the books
        while (resultSet.next()) {
            id = resultSet.getLong("id");
            name = resultSet.getString("name");

            System.out.println(id + "\t" + name);
        }

        // Close the connection
        resultSet.close();
        connection.close(); */
        // 
        
        
        
        System.out.println("GET "+signupRepository.findOne(id));
        model.addAttribute("signups", signupRepository.findOne(id));
        
        

        return "done";
    }
  
/*    
    protected AttackResult injectableQuery(String accountName) {
        try {
            Session session = (Session) entityManager.getDelegate();
            
            Connection connection = signupRepository;
            
            String query = "SELECT * FROM user_data WHERE last_name = '" + accountName + "'";

            try {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet results = statement.executeQuery(query);

                if ((results != null) && (results.first())) {
                    ResultSetMetaData resultsMetaData = results.getMetaData();
                    StringBuffer output = new StringBuffer();

                    output.append(writeTable(results, resultsMetaData));
                    results.last();

                    // If they get back more than one user they succeeded
                    if (results.getRow() >= 6) {
                        return trackProgress(success().feedback("sql-injection.5a.success").feedbackArgs(output.toString()).build());
                    } else {
                        return trackProgress(failed().output(output.toString()).build());
                    }
                } else {
                    return trackProgress(failed().feedback("sql-injection.5a.no.results").build());

                }
            } catch (SQLException sqle) {

                return trackProgress(failed().output(sqle.getMessage()).build());
            }
        } catch (Exception e) {
            return trackProgress(failed().output(this.getClass().getName() + " : " + e.getMessage()).build());
        }
    }

    
    
    public static String writeTable(ResultSet results, ResultSetMetaData resultsMetaData) throws IOException,
        SQLException {
        int numColumns = resultsMetaData.getColumnCount();
        results.beforeFirst();
        StringBuffer t = new StringBuffer();
        t.append("<p>");

        if (results.next()) {
            for (int i = 1; i < (numColumns + 1); i++) {
                t.append(resultsMetaData.getColumnName(i));
                t.append(", ");
            }

            t.append("<br />");
            results.beforeFirst();

            while (results.next()) {

                for (int i = 1; i < (numColumns + 1); i++) {
                    t.append(results.getString(i));
                    t.append(", ");
                }

                t.append("<br />");
            }

        } else {
            t.append("Query Successful; however no data was returned from this query.");
        }

        t.append("</p>");
        return (t.toString());
    }

*/
}
