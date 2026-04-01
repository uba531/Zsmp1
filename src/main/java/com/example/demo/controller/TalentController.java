package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.TalentForm;
import com.example.demo.service.TalentService;

@Controller
public class TalentController { 

	@Autowired
    private TalentService talentService;
	
	@GetMapping("/talent")
	public String showForm(TalentForm talentForm, Model model) {
	   
	    if (talentForm == null) {
	        talentForm = new TalentForm();
	    }
	    
	    // 2. バケツを画面に渡す（中身があってもなくても、これで共通化できる）
	    model.addAttribute("talentForm", talentForm);
	    
	    return "talent-input";
	}
	
	
	//RepositoryでDBから全部のデータを取得
	@GetMapping("/talent/list")
	public String showList(Model model) {
	  
	    
	    //  画面（HTML）に「talentList」という名前で渡す
	    model.addAttribute("talentList", talentService.selectAll());
	    
	    return "talent-list"; 
	}

	@PostMapping("/talent/confirm")
	public String confirm(
			@Valid 
			@ModelAttribute TalentForm talentForm,
			BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "talent-input";
		}
		// model.addAttribute("talentForm", talentForm); // @ModelAttributeがあれば自動で追加されるので省略可
		return "talent-confirm";
	}

	@PostMapping("/talent/complete")
	public String complete(@ModelAttribute TalentForm form) {
		//FormをEntityに変換して保存をserviceに任せる
		talentService.insert(form);
		
		return "redirect:/talent/complete"; //リダイレクトで二重投稿の防止
	}

	@GetMapping("/talent/complete")
	public String showComplete() {
		return "talent-complete"; 
	}
}