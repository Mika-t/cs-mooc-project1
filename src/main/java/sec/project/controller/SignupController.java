package sec.project.controller;

import javax.annotation.PostConstruct;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

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

    @RequestMapping("*")
    public String defaultMapping() {
        System.out.println("defaultmapping");
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        System.out.println("GET "+signupRepository.findAll().toString());
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, Model model) {
        System.out.println("name: "+name);
        System.out.println("address: "+address);
        Signup signup=new Signup(name, address);
        signupRepository.save(signup);
        Long id = signup.getId();
        System.out.println("GET "+signupRepository.findOne(id));
        model.addAttribute("signups", signupRepository.findOne(id));
        return "done";
    }

}
