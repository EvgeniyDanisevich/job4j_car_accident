package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class AccidentControl {
    private final AccidentService service;

    public AccidentControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/create")
    public String create(Model model) {
        Collection<AccidentType> types = service.findAllTypes();
        model.addAttribute("types", types);
        Collection<Rule> rules = service.findAllRules();
        model.addAttribute("rules", rules);
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        String typeId = req.getParameter("type.id");
        accident.setType(service.findTypeById(Integer.parseInt(typeId)));
        accident.setRules(service.generateRules(ids));
        service.create(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", service.findById(id));
        Collection<AccidentType> types = service.findAllTypes();
        model.addAttribute("types", types);
        Collection<Rule> rules = service.findAllRules();
        model.addAttribute("rules", rules);
        return "accident/update";
    }
}