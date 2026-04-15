package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*; // 検証用メソッド

import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test; // テスト用アノテーション
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.entity.Talent;
import com.example.demo.form.TalentForm;

@SpringBootTest // Spring Bootのtest
class TalentServiceTest {

	@Autowired
	private TalentService talentService;

	@Test
	@Transactional
	void testUpdate() {
	    // 1. 【準備】まずはデータを1件登録する
	    TalentForm form = new TalentForm();
	    form.setTalentName("テスト太郎");
	    form.setReason("更新前の理由");
	    talentService.insert(form);
	    
	    Page<Talent> talentPage = talentService.findAll(PageRequest.of(0, 100),null);

	    // 登録されたデータを一度すべて取得し、最新（今入れたもの）のIDを特定する
	    List<Talent> allTalents = talentPage.getContent();
	    Talent target = allTalents.get(allTalents.size() - 1);
	    int targetId = target.getId();

	    // 2. 【実行】更新用のデータを作って、updateメソッドを呼び出す
	    TalentForm updateForm = new TalentForm();
	    updateForm.setTalentName("更新次郎"); // 名前を変更
	    updateForm.setReason("更新後の理由");
	    
	    talentService.update(targetId, updateForm);

	    // 3. 【検証】再度IDで検索して、中身が書き換わっているか確認する
	    Talent result = talentService.findById(targetId);
	    
	    assertNotNull(result);
	    assertEquals("更新次郎", result.getTalentName(), "名前が更新されていません");
	    assertEquals("更新後の理由", result.getReason(), "理由が更新されていません");
	}
}