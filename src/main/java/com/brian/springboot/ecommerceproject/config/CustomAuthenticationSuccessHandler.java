package com.brian.springboot.ecommerceproject.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.brian.springboot.ecommerceproject.entity.CartsCourses;
import com.brian.springboot.ecommerceproject.entity.Order;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.model.OrdersAndCoursesForFronted;
import com.brian.springboot.ecommerceproject.service.CommonService;
import com.brian.springboot.ecommerceproject.service.UserService;
import com.brian.springboot.ecommerceproject.utils.ControllerUtils;


@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private CommonService commonService;
    
    private UserService userService;
    
    private Logger logger = Logger.getLogger(getClass().getName());
    
    public CustomAuthenticationSuccessHandler(CommonService commonService, UserService userService){
    	this.commonService = commonService;
    	this.userService = userService;
    }
	
    /**
     * 登入後進入首頁前的處理
     */
	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		String userName = authentication.getName();
		
		// 取得使用者資訊
		Optional<User> theUser = userService.findUserByName(userName);
		
		
		// 取得session
		HttpSession session = request.getSession();
		
		if(theUser.isPresent()) {
			
			logger.info("userName : " + theUser.get().getUsername());
			
			// 設定購物車資訊
			setCartData(theUser.get(), session);
			
			// 設定訂單資訊
			setOrderData(theUser.get(), session);
			
			session.setAttribute("user", theUser.get());
			session.setAttribute("courseTypes", commonService.getCourseTypes());
			
		}
		
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void setCartData(User theUser, HttpSession session) {
		// 取得購物車商品內容及數量
		List<CartsCourses> cartsCourses = commonService.getCartsCourses(theUser.getId());
		
		if(Optional.ofNullable(cartsCourses).isPresent()) {
			// 有資料:就直接取得購物車資訊
			if(cartsCourses.size() > 0) {
				int cartId = cartsCourses.get(0).getCartId();
				Map<Integer, Integer> cartCourseIdMap = cartsCourses.stream().collect(Collectors.toMap(CartsCourses::getCourseId, CartsCourses::getCourseId));
				
				session.setAttribute("cartId", cartId);
				session.setAttribute("cartCourseIdMap", cartCourseIdMap);
				session.setAttribute("cartCourseSize", cartCourseIdMap.keySet().size());
				
			}
		} else {
			// 無資料 : 初始化購物車內容
			session.setAttribute("cartId", 0);
			session.setAttribute("cartCourseIdMap", new HashMap<>());
			session.setAttribute("cartCourseSize", 0);
		}
	}
	
	private void setOrderData(User theUser, HttpSession session) {
		// 取得訂單商品內容及數量
		List<Order> orders = commonService.getOrders(theUser.getId());
		OrdersAndCoursesForFronted ordersAndCoursesForFronted = ControllerUtils.getOrdersAndCoursesForFronted(orders);
		
		// 有資料:就直接取得訂單資訊
		if(ordersAndCoursesForFronted.getOrderSize() > 0) {
			session.setAttribute("orders", ordersAndCoursesForFronted.getOrders());
			session.setAttribute("orderCourseIdMap", ordersAndCoursesForFronted.getOrdersCourseIdMap());
			session.setAttribute("orderSize", ordersAndCoursesForFronted.getOrderSize());
		} else {
			session.setAttribute("orders",  new ArrayList<>());
			session.setAttribute("orderCourseIdMap", new HashMap<>());
			session.setAttribute("orderSize", 0);
		}

	}

}
