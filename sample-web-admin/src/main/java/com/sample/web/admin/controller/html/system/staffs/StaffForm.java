package com.sample.web.admin.controller.html.system.staffs;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.sample.web.base.controller.html.BaseForm;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StaffForm extends BaseForm {

	private static final long serialVersionUID = -6807767990335584883L;

	Long id;

	/*
	 * 名前
	 */
	@NotEmpty
	String firstName;

	/*
	 * 苗字
	 */
	@NotEmpty
	String lastName;

	@NotEmpty
	@Size(min = 5,max=8)
	String password;

	@NotEmpty
	@Size(min = 5,max=8)
	String passwordConfirm;

	/*
	 * メールアドレス
	 */
	@NotEmpty
	@Email
	String email;

	/*
	 * 電話番号
	 */
	@Digits(fraction = 0, integer = 10)
	String tel;
}
