package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Talent;
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
	public String complete(@Validated @ModelAttribute TalentForm form, BindingResult result, Model model) {
	    
	    // 1. エラーチェックの結果を判定する
	    if (result.hasErrors()) {
	        // エラーがあったら、保存せずに「入力画面」のHTMLを返す
	        return "talent-input"; 
	    }
	    
	    // エラーがなければ、今まで通り保存してリダイレクト
	    talentService.update(form);
	    return "redirect:/talent/complete";
	}

	@GetMapping("/talent/complete")
	public String showComplete() {
		return "talent-complete"; 
	}
	
	
	
	//IDを受け取って削除するコントローラー
	@PostMapping("/talent/delete")
	public String delete(@RequestParam Integer id,RedirectAttributes redirectAttributes) {
	    // ServiceにあるIDデリートの呼び出し
	    talentService.delete(id);
	    redirectAttributes.addFlashAttribute("deleteMessage", "ID " + id + " は削除しました");
	    return "redirect:/talent/list";
	}
	
	//IDを受け取って編集画面に移動する
	@GetMapping("/talent/edit")
	public String edit(@RequestParam Integer id, Model model) {
	    // 1. まずはDBからデータを1回だけ取ってきて、talentという変数に入れる
	    Talent talent = talentService.findById(id);

	    // (オプション) もしデータがなかった時のための安全策
	    if (talent == null) {
	        return "redirect:/talent/list"; // 一覧に逃がす
	    }

	    // 2. 取ってきたデータをForm（バケツ）に移し替える
	    TalentForm talentForm = new TalentForm();
	    talentForm.setId(talent.getId());
	    talentForm.setTalentName(talent.getTalentName());
	    talentForm.setReason(talent.getReason());
	    
	    model.addAttribute("talentForm", talentForm);
	    return "talent-input";
	}
	
}