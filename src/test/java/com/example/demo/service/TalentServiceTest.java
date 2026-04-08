package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*; // 検証用メソッド

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test; // テスト用アノテーション
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Talent;
import com.example.demo.form.TalentForm;

@SpringBootTest // Spring Bootのtest
class TalentServiceTest {

	@Autowired
	private TalentService talentService;

	@Test
	@Transactional // トランザクション管理（テスト後にロールバックされる）
	void testFindById() {
		// 【追加】まずテストデータを1件登録する（準備）
		TalentForm form = new TalentForm();
		form.setTalentName("テストタレント");
		form.setReason("テスト理由");
		talentService.insert(form); // これでDBに1件入る
		Talent result = talentService.findById(1);

		// 3. 検証
		assertNotNull(result, "データが取得できるはずです");
		assertEquals("テストタレント", result.getTalentName(), "名前が一致するはずです");
	}
}