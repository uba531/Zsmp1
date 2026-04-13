package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Talent;
import com.example.demo.form.TalentForm;
import com.example.demo.repository.TalentRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class TalentService {

    
    private final TalentRepository talentRepository;

    // 一覧を取得
    public List<Talent> selectAll() {
        return talentRepository.findAll();
    }

    // FormをEntityに変換して保存する職人の仕事
    public void insert(TalentForm form) {
        Talent talent = new Talent();
        talent.setTalentName(form.getTalentName());
        talent.setReason(form.getReason());
        
        talentRepository.save(talent);
    }
    
 // IDを指定して削除
    public void delete(Integer id) {
        talentRepository.deleteById(id);
    }
    
 // IDを指定して1件検索する
 public Talent findById(Integer id) {
     // findByIdは「値がないかもしれない(Optional)」という型で返ってくるので、
     // .orElse(null) をつけて「なければnullを返す」ようにします
     return talentRepository.findById(id).orElse(null);
 }

 // データを更新する（保存と同じsaveメソッドを使います）
 public void update(TalentForm form) {
     Talent talent = new Talent();
     talent.setId(form.getId()); // ←ここが重要！IDをセットすると「更新」になります
     talent.setTalentName(form.getTalentName());
     talent.setReason(form.getReason());
     
     talentRepository.save(talent);
 }
 
 public List<Talent> findAll() {
	    return talentRepository.findAll(); // リポジトリの機能をそのまま呼ぶ
	}
}