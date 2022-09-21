package com.brian.springboot.ecommerceproject.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brian.springboot.ecommerceproject.dao.CartRepository;
import com.brian.springboot.ecommerceproject.dao.CartsCoursesRepository;
import com.brian.springboot.ecommerceproject.dao.CourseRepository;
import com.brian.springboot.ecommerceproject.dao.CourseTypeRepository;
import com.brian.springboot.ecommerceproject.dao.CreditCardRepository;
import com.brian.springboot.ecommerceproject.dao.OrderRepository;
import com.brian.springboot.ecommerceproject.dao.UserRepository;
import com.brian.springboot.ecommerceproject.entity.Cart;
import com.brian.springboot.ecommerceproject.entity.CartsCourses;
import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.CourseType;
import com.brian.springboot.ecommerceproject.entity.CreditCard;
import com.brian.springboot.ecommerceproject.entity.Order;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.model.CreditCardInfo;
import com.brian.springboot.ecommerceproject.utils.ServiceUtils;

@Service
public class CommonServiceImpl implements CommonService {

	private CartRepository cartRepository;

	private CourseRepository courseRepository;

	private CourseTypeRepository courseTypeRepository;

	private CartsCoursesRepository cartsCoursesRepository;

	private UserRepository userRepository;
	
	private OrderRepository orderRepository;
	
	private CreditCardRepository creditCardRepository;

	public CommonServiceImpl(CartRepository cartRepository, CourseRepository courseRepository,
			CourseTypeRepository courseTypeRepository, CartsCoursesRepository cartsCoursesRepository,
			UserRepository userRepository, OrderRepository orderRepository, CreditCardRepository creditCardRepository) {
		this.cartRepository = cartRepository;
		this.courseRepository = courseRepository;
		this.courseTypeRepository = courseTypeRepository;
		this.cartsCoursesRepository = cartsCoursesRepository;
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.creditCardRepository = creditCardRepository;
	}

	/**
	 * 取得購物車課程數量
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional
	public List<CartsCourses> getCartsCourses(int userId) {
		// 取得現在時間跟3天前的時間
		long[] times = ServiceUtils.getBefore3DaysAndNow();
		// 查看使用者最近三天的購物車
		Optional<Cart> cart = cartRepository.findByUserIdAndDateAndFlag(userId, new Timestamp(times[0]), new Timestamp(times[1]));

		List<CartsCourses> cartsCourses = null;
		if (cart.isPresent()) {
			// 透過購物車編號取得課程內容
			cartsCourses = cartsCoursesRepository.findByCartId(cart.get().getId());
		}
		return cartsCourses;
	}

	@Override
	@Transactional
	public List<Order> getOrders(int userId) {
		return orderRepository.findByUserId(userId);
	}

	@Override
	@Transactional
	public List<CourseType> getCourseTypes() {
		return courseTypeRepository.findAll();
	}

	@Override
	@Transactional
	public Optional<Course> findCourseById(int courseId) throws Exception {
		return courseRepository.findById(courseId);
	}

	@Override
	@Transactional
	public List<Course> findCourseByIds(List<Integer> courseIds) {
		return courseRepository.findByIdIn(courseIds);
	}

	/**
	 * 取得信用卡資訊
	 */
	@Override
	public CreditCardInfo getCreditCardInfo(int userId) {
		Optional<User> optUser = userRepository.findById(userId);
		
		List<CreditCard> creditCards = null;
		int creditCardSize = 0;
		CreditCard defaultCard = null;
		
		if(optUser.isPresent()) {
			creditCards = optUser.map(User::getCreditCards).orElse(new ArrayList<>());
			creditCardSize = Optional.ofNullable(creditCards).isPresent()
					? Optional.ofNullable(creditCards).get().size()
					: 0;
			defaultCard = creditCards.stream().filter(creditCard -> creditCard.isDefaultFlag() == true).findAny()
					.orElse(null);
		}

		return new CreditCardInfo(creditCardSize, creditCards, defaultCard);
	}
	
	@Override
	public Optional<CreditCard> findCreditCard(String cardNo) {
		if(Optional.ofNullable(cardNo).isPresent()) {
			return creditCardRepository.findByCardNo(cardNo);
		};
		return Optional.empty();
	}

}
