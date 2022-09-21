package com.brian.springboot.ecommerceproject.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brian.springboot.ecommerceproject.dao.CartRepository;
import com.brian.springboot.ecommerceproject.dao.CartsCoursesRepository;
import com.brian.springboot.ecommerceproject.dao.CourseRepository;
import com.brian.springboot.ecommerceproject.dao.UserRepository;
import com.brian.springboot.ecommerceproject.entity.Cart;
import com.brian.springboot.ecommerceproject.entity.CartsCourses;
import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.model.CartAndCourseInfo;
import com.brian.springboot.ecommerceproject.utils.ServiceUtils;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	private CourseRepository courseRepository;

	private CartsCoursesRepository cartsCoursesRepository;

	private CartRepository cartRepository;

	public ShoppingCartServiceImpl(CourseRepository courseRepository, UserRepository userRepository, CartsCoursesRepository cartsCoursesRepository,
			CartRepository cartRepository) {
		this.courseRepository = courseRepository;
		this.cartsCoursesRepository = cartsCoursesRepository;
		this.cartRepository = cartRepository;
	}

	@Override
	@Transactional
	public List<Course> getCourseByIds(List<Integer> courseIds) {
		List<Course> courses = courseRepository.findByIdIn(courseIds);
		return courses;
	}

	@Override
	@Transactional
	public CartAndCourseInfo addShoppingCart(User user, int courseId) throws Exception {
		// 取得現在時間跟3天前的時間
		long[] times = ServiceUtils.getBefore3DaysAndNow();

		// 查看使用者最近三天的購物車
		Optional<Cart> optCart = cartRepository.findByUserIdAndDateAndFlag(user.getId(), new Timestamp(times[0]), new Timestamp(times[1]));

		List<Course> courses = null;
		
		Cart cart = null;
		
		if (optCart.isPresent()) {
			cart = optCart.get();
			
			// 透過購物車編號取得購物車課程編號清單
			List<CartsCourses> tempOrdersCourses = cartsCoursesRepository.findByCartId(cart.getId());
			List<Integer> tempOrdersCourseIds = tempOrdersCourses.stream().map(CartsCourses::getCourseId)
					.collect(Collectors.toList());

			// 透過購物車課程編號清單取得購物車課程內容清單，並在清單加入新的課程
			courses = courseRepository.findByIdIn(tempOrdersCourseIds);
			courses.add(findCourseById(courseId));
			// 設定購物車課程資料
			cart.setCourses(courses);
		} else {
			// 無購物車，新增購物車資訊
			cart = new Cart();
			//cart.setUserId(user.getId());
			cart.setCreateTime(new Timestamp(System.currentTimeMillis()));
			cart.setCheckoutFlag(false);
			courses = Arrays.asList(findCourseById(courseId));
			cart.setCourses(courses);
		}
		
		cart.setUser(user);
		// 儲存購物車資料
		cartRepository.save(cart);

		return new CartAndCourseInfo(cart.getId(), courses);
	}

	@Override
	@Transactional
	public Map<Integer, Integer> removeShoppingCart(int cartId, int courseId) {
		// 移除購物車商品
		cartsCoursesRepository.deleteByCartAndCourseId(cartId, courseId);
		// 查看購物車是否還有商品
		List<CartsCourses> shoppingCartCourses = cartsCoursesRepository.findByCartId(cartId);
		Optional<List<CartsCourses>> optCartsCourses = Optional.ofNullable(shoppingCartCourses);
		Map<Integer, Integer> shoppingCartCourseIds = null;
		if (optCartsCourses.isPresent()) {
			// 購物車有課程就建課程編號清單，沒有的話就把購物車刪除
			if (optCartsCourses.get().size() > 0) {
				shoppingCartCourseIds = optCartsCourses.get().stream()
						.collect(Collectors.toMap(CartsCourses::getCourseId, CartsCourses::getCourseId));
			} else {
				cartRepository.deleteById(cartId);
			}
		}

		return shoppingCartCourseIds;
	}

	@Override
	@Transactional
	public Course findCourseById(int courseId) throws Exception {
		Optional<Course> course = courseRepository.findById(courseId);
		if (course.isPresent()) {
			return course.get();
		} else {
			throw new Exception("課程編號不存在");
		}
	}

}
