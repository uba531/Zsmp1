package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Talent;
import com.example.demo.repository.TalentRepository;

@ExtendWith(MockitoExtension.class) // Springを起動しないモード
class TalentServiceUnitTest {

    @Mock
    private TalentRepository talentRepository; // 偽物のDB担当

    @InjectMocks
    private TalentService talentService; // テストしたい本体

    @Test
    @DisplayName("モックを使った初めての単体テスト")
    void testFirstMock() {
        // 1. 【準備】
        Talent mockTalent = new Talent();
        mockTalent.setId(100);
        mockTalent.setTalentName("次郎");
        when(talentRepository.findById(100)).thenReturn(Optional.of(mockTalent));

        // 2. 【実行】Serviceのメソッドを呼ぶ
        Talent result = talentService.findById(100);

        // 3. 【検証】
        assertNotNull(result);
        assertEquals("次郎", result.getTalentName());
    }
}