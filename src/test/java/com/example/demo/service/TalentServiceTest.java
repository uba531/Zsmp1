package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*; // 検証用メソッド

import org.junit.jupiter.api.Test;              // テスト用アノテーション
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Talent;

@SpringBootTest // Spring Bootのtest
class TalentServiceTest {

    @Autowired
    private TalentService talentService;

    @Test // これを付けると「テスト項目」として認識されます
    void testFindById() {
        // 1. 準備（DBにID:1のデータで検証予定
        Integer testId = 1;

        // 2. 実行（実際にServiceのメソッドを呼ぶ）
        Talent result = talentService.findById(testId);

        // 3. 検証
        assertNotNull(result, "ID 1 のデータの取得");
        
       
    }
}