package com.example.salessummary.web;

import com.example.salessummary.entities.Salesman;
import com.example.salessummary.repositories.SalesmanRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class SalesmanController {

    @Autowired
    private SalesmanRepository salesmanRepository;

    @GetMapping(path="/index")
    public String salesman(Model model){
        List<Salesman> salesman=salesmanRepository.findAll();
        model.addAttribute("listSalesman", salesman);

        model.addAttribute("salesman",new Salesman());


        return "salesman";
    }

    @GetMapping(path="/")
    public String salesman2(Model model){
        List<Salesman> salesman=salesmanRepository.findAll();
        model.addAttribute("listSalesman", salesman);

        model.addAttribute("salesman",new Salesman());


        return "salesman";
    }

    @GetMapping("/delete")
    public String delete(Long id){
        salesmanRepository.deleteById(id);

        return "redirect:/index";
    }

    @GetMapping("/formSalesman")
    public String formSalesman(Model model){
        model.addAttribute("salesman",new Salesman());

        return "salesman";
    }

    @PostMapping(path = "/save")
    public String save(Model model, Salesman salesman, BindingResult bindingResult,
                       ModelMap mm, HttpSession session){
        if(bindingResult.hasErrors()){
            return "formSalesman";
        }else{
            salesmanRepository.save(salesman);
        }

        return "redirect:/index";
    }

    @GetMapping("/editSalesman")
    public String editSalesman(Model model, Long id, HttpSession session) {
        //num = 2;
        session.setAttribute("info", 0);
        Salesman salesman = salesmanRepository.findById(id).orElse(null);
        if (salesman == null) throw new RuntimeException("Salesman does not exist");
        model.addAttribute("salesman", salesman);
        return "editSalesman";
    }


    }
