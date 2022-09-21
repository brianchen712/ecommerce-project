package com.brian.springboot.ecommerceproject.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.brian.springboot.ecommerceproject.validator.ValidCardNo;
import com.brian.springboot.ecommerceproject.validator.ValidExpiredDate;

public class CheckoutForm {

	private int id;

	@ValidCardNo
	@NotNull(message = "卡號必填")
	@Digits(integer = 16, fraction = 0, message = "卡號需為數字")
	@Size(min = 16, max = 16, message = "卡號需為16位")
	private String cardNo;

	@ValidExpiredDate
	@NotNull(message = "到期日必填")
	@Digits(integer = 4, fraction = 0, message = "到期日需為數字")
	@Size(min = 4, max = 4, message = "到期日需為4位")
	private String expiredDate;

	@NotNull(message = "安全碼必填")
	@Digits(integer = 3, fraction = 0, message = "安全碼需為數字")
	@Size(min = 3, max = 3, message = "安全碼需為3位")
	private String cvv;

	public CheckoutForm() {

	}

	public CheckoutForm(String cardNo, String expiredDate, String cvv) {
		this.cardNo = cardNo;
		this.expiredDate = expiredDate;
		this.cvv = cvv;
	}

	public CheckoutForm(int id, String cardNo, String expiredDate, String cvv) {
		this.id = id;
		this.cardNo = cardNo;
		this.expiredDate = expiredDate;
		this.cvv = cvv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}
