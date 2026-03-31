package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.TalentForm;

@Controller
public class TalentController { // クラス名も Talent と L一つが一般的ですが、そのままでも動きます

	@GetMapping("/talent")
	public String showForm(TalentForm talentForm, Model model) {
	   
	    if (talentForm == null) {
	        talentForm = new TalentForm();
	    }
	    
	    // 2. バケツを画面に渡す（中身があってもなくても、これで共通化できる）
	    model.addAttribute("talentForm", talentForm);
	    
	    return "talent-input";
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
		// ここで Repository.save(form) などを行う予定ですね！
		return "redirect:/talent/complete"; // 完了画面のURLへリダイレクト
	}

	@GetMapping("/talent/complete")
	public String showComplete() {
		return "talent-complete"; // templates/talent-complete.html を表示
	}
}