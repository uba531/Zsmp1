package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class TalentForm {
	
	
	private Integer id; 

	@Size(max = 15, message = "タレント名は15文字以内で入力してください")
	@NotBlank(message = "タレント名を入力してください")
	private String talentName;
	
    @Size(max = 200, message = "好きな理由は200文字以内で入力してください")
	@NotBlank(message = "好きな理由を入力してください")
	private String reason;
	
	
}
