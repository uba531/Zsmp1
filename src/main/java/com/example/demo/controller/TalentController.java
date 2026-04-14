package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TalentController {

	private final TalentService talentService;

	// 画面に入力フォームを表示
	@GetMapping("/talent")
	public String showForm(@ModelAttribute TalentForm talentForm) {

		return "talent-input";

	}

	//一覧を表示する
	//RepositoryでDBから全部のデータを取得
	@GetMapping("/talent/list")
	public String showList(Model model,
			@PageableDefault(size = 5,
			sort = "id", 
			direction = Direction.DESC
			) Pageable pageable) {

		// 1. Serviceを呼び出して、現在のページ情報を取得
		Page<Talent> talentPage = talentService.findAll(pageable);

		// 2. 「中身のリスト」を今までと同じ名前で渡す（これでテーブル部分は壊れません）
		model.addAttribute("talentList", talentPage.getContent());

		// 3. 「ページ情報そのもの」も渡す（下のボタン作成で使います）
		model.addAttribute("page", talentPage);

		return "talent-list";
	}

	//入力内容確認
	@PostMapping("/talent/confirm")
	public String confirm(
			@Valid @ModelAttribute TalentForm talentForm,
			BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "talent-input";
		}
		// model.addAttribute("talentForm", talentForm); // @ModelAttributeがあれば自動で追加されるので省略可
		return "talent-confirm";
	}

	//内容を保存し完了画面へ
	@PostMapping("/talent/complete")
	public String complete(
			@Validated @ModelAttribute TalentForm form,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "talent-input";
		}

		if (form.getId() == null) {
			// IDが空っぽなら「新規登録」
			talentService.insert(form);
			redirectAttributes.addFlashAttribute("message", "新しく登録しました！");
		} else {
			// IDがあるなら「更新」
			talentService.update(form.getId(), form);
			redirectAttributes.addFlashAttribute("message", "情報を更新しました！");
		}

		return "redirect:/talent/list";
	}

	//完了画面の表示
	@GetMapping("/talent/complete")
	public String showComplete() {
		return "talent-complete";
	}

	//IDを受け取って削除
	@PostMapping("/talent/delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
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