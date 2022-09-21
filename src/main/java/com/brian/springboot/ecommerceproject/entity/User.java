package com.brian.springboot.ecommerceproject.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "email")
	private String email;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "update_date")
	private Timestamp updateDate;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Review> reviews;

	// one to many 要刪除child entities，需在此處增加 orphanRemoval = true
	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<CreditCard> creditCards;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Cart> carts;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order> orders;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
	

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public List<CreditCard> getCreditCards() {
		return creditCards;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void addUserPayments(CreditCard creditCard) {
		if (creditCards == null) {
			creditCards = new ArrayList<>();
		}

		creditCards.add(creditCard);
		creditCard.setUser(this);
	}

	public void addCart(Cart theCart) {
		if (carts == null) {
			carts = new ArrayList<>();
		}

		carts.add(theCart);
		theCart.setUser(this);
	}

	public void addOrder(Order theOrder) {
		if (orders == null) {
			orders = new ArrayList<>();
		}

		orders.add(theOrder);
		theOrder.setUser(this);
	}

}
