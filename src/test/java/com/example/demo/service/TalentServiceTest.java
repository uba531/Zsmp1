package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*; // 検証用メソッド

import java.util.List;

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
	@Transactional
	void testFindById() {
	    // 1. 【準備】
	    TalentForm form = new TalentForm();
	    form.setTalentName("テストタレント");
	    form.setReason("テスト理由");
	    talentService.insert(form); 
	    
	    // 2. 【実行】
	    // データベースにある全件を一度取ってくる
	    List<Talent> allTalents = talentService.findAll(); 
	    
	    // リストの「一番最後」に入っているのが、今 insert したデータ
	    // list.size() - 1 で、最後の要素の添え字（インデックス）を指定
	    Talent latestTalent = allTalents.get(allTalents.size() - 1);
	    
	    // そのデータから、本物のID（その時たまたま割り振られた番号）を取り出す
	    int realId = latestTalent.getId(); 
	    
	    // その本物のIDを使って検索する
	    Talent result = talentService.findById(realId);
	    
	    // 3. 【検証】
	    assertNotNull(result, "登録したデータが取得できません");
	    assertEquals("テストタレント", result.getTalentName());
	}
}