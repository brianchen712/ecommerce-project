package com.brian.springboot.ecommerceproject.model;

import java.util.List;
import java.util.Map;

import com.brian.springboot.ecommerceproject.entity.Order;

public class OrdersAndCoursesForFronted {
	
	// 訂單數量
	private int orderSize;
	// 我的訂單頁使用
	List<Order> orders;
	// 首頁跟明細頁使用
	private Map<Integer, Integer> ordersCourseIdMap;
	
	public OrdersAndCoursesForFronted() {
	
	}

	public OrdersAndCoursesForFronted(int orderSize, List<Order> orders,
			Map<Integer, Integer> ordersCourseIdMap) {
		this.orderSize = orderSize;
		this.orders = orders;
		this.ordersCourseIdMap = ordersCourseIdMap;
	}

	public int getOrderSize() {
		return orderSize;
	}

	public void setOrderSize(int orderSize) {
		this.orderSize = orderSize;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Map<Integer, Integer> getOrdersCourseIdMap() {
		return ordersCourseIdMap;
	}

	public void setOrdersCourseIdMap(Map<Integer, Integer> ordersCourseIdMap) {
		this.ordersCourseIdMap = ordersCourseIdMap;
	}
	
}
