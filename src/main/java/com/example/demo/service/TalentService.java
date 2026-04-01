package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Talent;
import com.example.demo.form.TalentForm;
import com.example.demo.repository.TalentRepository;

@Service // これを付けることでSpringの管理対象（職人）になります
public class TalentService {

    @Autowired
    private TalentRepository talentRepository;

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
}