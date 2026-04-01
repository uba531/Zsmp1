package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TalentForm {
	
	
	private Integer id; 

	@NotBlank(message = "タレント名を入力してください")
	private String talentName;

	@NotBlank(message = "好きな理由を入力してください")
	private String reason;
	
	
}
