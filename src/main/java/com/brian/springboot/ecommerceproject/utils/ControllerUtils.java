package com.brian.springboot.ecommerceproject.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.CreditCard;
import com.brian.springboot.ecommerceproject.entity.Instructor;
import com.brian.springboot.ecommerceproject.entity.Order;
import com.brian.springboot.ecommerceproject.entity.Review;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CheckoutForm;
import com.brian.springboot.ecommerceproject.form.ReviewForm;
import com.brian.springboot.ecommerceproject.model.AllReviewForFronted;
import com.brian.springboot.ecommerceproject.model.CartAndCourseInfo;
import com.brian.springboot.ecommerceproject.model.CourseAndInstructorForFronted;
import com.brian.springboot.ecommerceproject.model.CourseForFronted;
import com.brian.springboot.ecommerceproject.model.CreditCardForFronted;
import com.brian.springboot.ecommerceproject.model.InstructorForFronted;
import com.brian.springboot.ecommerceproject.model.OrderAndCoursesForFronted;
import com.brian.springboot.ecommerceproject.model.OrderAndCoursesInfo;
import com.brian.springboot.ecommerceproject.model.OrderForFronted;
import com.brian.springboot.ecommerceproject.model.OrdersAndCoursesForFronted;
import com.brian.springboot.ecommerceproject.model.ReviewForFronted;
import com.brian.springboot.ecommerceproject.model.ReviewInfo;
import com.brian.springboot.ecommerceproject.service.ShoppingCartService;

public class ControllerUtils {
	
	private static Logger logger = Logger.getLogger(ControllerUtils.class.getName());

	/**
	 * 加入購物車處理
	 * @param shoppingCartService
	 * @param courseId
	 * @param session
	 * @param request
	 * @throws Exception
	 */
	public static void processAddCart(ShoppingCartService shoppingCartService, @RequestParam("courseId") int courseId,
			HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("in processAddCart");
		// 課程加入購物車後，再取得目前購物車編號跟購物車存放課程
		CartAndCourseInfo cartAndCourse = shoppingCartService.addShoppingCart((User) session.getAttribute("user"),
				courseId);
		
		logger.info("cart Id: " + cartAndCourse.getCartId());
		
		// 取得所有課程編號清單
		Map<Integer, Integer> courseIdsMap = null;
		if (cartAndCourse.getCourses().size() > 0) {
			courseIdsMap = cartAndCourse.getCourses().stream().collect(Collectors.toMap(Course::getId, Course::getId));
		} else {
			courseIdsMap = new HashMap<>();
		}

		// 點選相關課程的加入購物車，需要將畫面上的主要課程編號傳回明細頁
		if (Optional.ofNullable(request.getParameter("primaryCourseId")).isPresent()) {
			courseId = Integer.parseInt(request.getParameter("primaryCourseId"));
		}

		// 購物車加入第一門課程，需要將購物車編號存入session
		if (cartAndCourse.getCourses().size() == 1) {
			session.setAttribute("cartId", cartAndCourse.getCartId());
		}

		session.setAttribute("cartCourseIdMap", courseIdsMap);

		session.setAttribute("cartCourseSize", courseIdsMap.keySet().size());
	}

	

	/**
	 * 組出畫面上的課程+講師內容
	 * 
	 * @param
	 * @return
	 */
	public static CourseAndInstructorForFronted geneCourseAndInstructor(Optional<Course> optCourse) {
		CourseAndInstructorForFronted courseAndInstructor = new CourseAndInstructorForFronted();
		
		if(optCourse.isPresent()) {
			courseAndInstructor.setCourse(geneCourse(optCourse.get()));
			courseAndInstructor.setInstructor(geneInstructor(optCourse.get().getInstructor()));
		}

		return courseAndInstructor;
	}

	/**
	 * 組出畫面上的單一課程內容
	 * 
	 * @param
	 * @return
	 */
	public static CourseForFronted geneCourse(Course course) {
		CourseForFronted custCourse = new CourseForFronted();
		custCourse.setId(course.getId());
		custCourse.setName(course.getName());
		custCourse.setDescription(course.getDescription());
		custCourse.setPrice(course.getPrice());
		if (course.getDiscountPrice() != -1) {
			custCourse.setDiscountPrice(course.getDiscountPrice());
		}
		custCourse.setImage(course.getImage());

		return custCourse;
	}

	/**
	 * 組出畫面上的多組課程內容
	 * 
	 * @param
	 * @return
	 */
	public static List<CourseForFronted> geneCourses(List<Course> courses) {
		List<CourseForFronted> custCourses = new ArrayList<>();
		for (int i = 0; i < courses.size(); i++) {
			custCourses.add(geneCourse(courses.get(i)));
		}

		return custCourses;
	}

	/**
	 * 組出畫面上的講師內容
	 * 
	 * @param instructor
	 * @return
	 */
	public static InstructorForFronted geneInstructor(Instructor instructor) {
		InstructorForFronted custInstructor = new InstructorForFronted();
		if(Optional.ofNullable(instructor).isPresent()) {
			custInstructor.setId(instructor.getId());
			custInstructor.setName(instructor.getFullName());
			custInstructor.setEducation(instructor.getEducation());
			custInstructor.setExperience(instructor.getExperience());
			custInstructor.setEmail(instructor.getEmail());
			custInstructor.setImage(instructor.getImage());
		}

		return custInstructor;
	}

	/**
	 * 取得購物車課程原金額(有千分號跟無千分號)
	 * 
	 * @param courses
	 * @return
	 */
	public static String[] genePrices(List<Course> courses) {
		Optional<List<Course>> ofCourses = Optional.ofNullable(courses);
		int coursePrice = 0;
		if (ofCourses.isPresent()) {
			coursePrice = ofCourses.get().stream().mapToInt(course -> course.getPrice()).sum();
		}
		DecimalFormat df = new DecimalFormat("#,###");
		return new String[] { df.format(coursePrice), String.valueOf(coursePrice) };
	}

	/**
	 * 取得購物車課程折扣後金額(有千分號跟無千分號)
	 * 
	 * @param courses
	 * @return
	 */
	public static String[] geneDiscountPrices(List<Course> courses) {
		Optional<List<Course>> ofCourses = Optional.ofNullable(courses);
		int courseDiscountPrice = 0;
		if (ofCourses.isPresent()) {
			for (Course course : courses) {
				if (course.getDiscountPrice() == -1) {
					courseDiscountPrice += course.getPrice();
				} else {
					courseDiscountPrice += course.getDiscountPrice();
				}
			}
		}

		DecimalFormat df = new DecimalFormat("#,###");
		return new String[] { df.format(courseDiscountPrice), String.valueOf(courseDiscountPrice) };
	}

	/**
	 * 取得購物車課程折扣
	 * 
	 * @param courses
	 * @return
	 */
	public static String geneDiscount(String coursePrice, String courseDiscountPrice) {
		int discount = Integer.parseInt(coursePrice) - Integer.parseInt(courseDiscountPrice);
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(discount);
	}

	/**
	 * 將畫面上的信用卡資訊放入entity中
	 * @param checkoutForm
	 * @param creditCards
	 * @param user
	 * @return
	 */
	public static CreditCard getCreditCard(CheckoutForm checkoutForm, List<CreditCard> creditCards, User user) {
		CreditCard creditCard = new CreditCard();
		if(Optional.ofNullable(checkoutForm.getId()).isPresent()) {
			if(Optional.ofNullable(checkoutForm.getId()).get() == 0) {
				// 新增
				creditCard.setUser(user);
				creditCard.setCreditCardNo(checkoutForm.getCardNo());
				creditCard.setCreditCardType(checkoutForm.getCardNo().startsWith("4") ? "Visa" : "MasterCard");
				creditCard.setExpiredDate(checkoutForm.getExpiredDate());
				creditCard.setCvv(checkoutForm.getCvv());
				// 如果使用者沒建立任何卡片，則將此卡片設為預設卡片
				if(Optional.ofNullable(creditCards).isPresent()) {
					if(creditCards.size() == 0) {
						creditCard.setDefaultFlag(true);
					} else {
						creditCard.setDefaultFlag(false);
					}
				}
				creditCard.setCreateDate(new Timestamp(System.currentTimeMillis()));
			} else {
				// 修改
				// 取得目前卡片資訊，再將form的內容，覆蓋到卡片資訊部分欄位
				if(Optional.ofNullable(creditCards).isPresent()) {
					CreditCard curCard = creditCards.stream().filter(card->card.getId() == checkoutForm.getId()).findAny().get();
					creditCard.setId(checkoutForm.getId());
					creditCard.setUser(user);
					creditCard.setCreditCardNo(checkoutForm.getCardNo());
					creditCard.setCreditCardType(curCard.getCreditCardType());
					creditCard.setExpiredDate(checkoutForm.getExpiredDate());
					creditCard.setCvv(checkoutForm.getCvv());
					creditCard.setDefaultFlag(curCard.isDefaultFlag());
					creditCard.setCreateDate(curCard.getCreateDate());
				}
			}
		}
		return creditCard;
	}

	/**
	 * 組出畫面上的多組訂單內容
	 * @param orders
	 * @return
	 */
	public static List<OrderForFronted> geneOrders(List<Order> orders) {
		List<OrderForFronted> custOrders = new ArrayList<>();
		for (int i = 0; i < orders.size(); i++) {
			custOrders.add(geneOrder(orders.get(i)));
		}

		return custOrders;
	}

	/**
	 * 組出畫面上的單一訂單內容
	 * @param order
	 * @return
	 */
	public static OrderForFronted geneOrder(Order order) {
		OrderForFronted orderForFronted = new OrderForFronted();
		orderForFronted.setId(order.getId());
		orderForFronted
				.setCourseSize(new StringBuffer().append("已購買").append(order.getCourseSize()).append("堂課程").toString());
		orderForFronted.setPurchaseDate(geneFormatDate(order.getCreateTime()));
		DecimalFormat df = new DecimalFormat("#,###");
		orderForFronted.setAmount(df.format(order.getAmount()));
		String cardType = getCardType(order.getCreditCardNo());
		orderForFronted.setCreditCard(new StringBuffer().append(cardType).append(" ")
				.append(order.getCreditCardNo()).toString());
		return orderForFronted;
	}
	
	/**
	 * 取得卡片種類
	 * @param cardNo
	 * @return
	 */
	private static String getCardType(String cardNo) {
		String cardType = "";
		if(Optional.ofNullable(cardNo).isPresent()) {
			cardType = Optional.ofNullable(cardNo).get().startsWith("4") ? "Visa": "MasterCard";
		}
		return cardType;
	}

	/**
	 * 日期格式化處理
	 * @param date
	 * @return
	 */
	public static String geneFormatDate(Timestamp date) {
		Date dateTime = new Date(date.getTime());
		DateFormat DFormat = DateFormat.getDateInstance();
		String dateTimeStr = DFormat.format(dateTime);
		return dateTimeStr;
	}

	/**
	 * 組出畫面上的訂單課程內容
	 * @param orderAndCourses
	 * @return
	 */
	public static List<OrderAndCoursesForFronted> geneOrderAndCoursesForFronteds(OrderAndCoursesInfo orderAndCourses) {
		List<OrderAndCoursesForFronted> orderAndCoursesForFronteds = new ArrayList<>();

		Map<Integer, Integer> coursePriceMap = getCoursePriceMap(orderAndCourses.getOrder());
		List<Course> courses = orderAndCourses.getCourses();

		for (Course course : courses) {
			OrderAndCoursesForFronted orderAndCoursesForFronted = new OrderAndCoursesForFronted();
			orderAndCoursesForFronted.setImage(course.getImage());
			orderAndCoursesForFronted.setCourseName(course.getName());
			DecimalFormat df = new DecimalFormat("#,###");
			orderAndCoursesForFronted.setCoursePrice(df.format(coursePriceMap.get(course.getId())));

			orderAndCoursesForFronteds.add(orderAndCoursesForFronted);
		}
		return orderAndCoursesForFronteds;
	}

	/**
	 * 取得訂單上的課程價錢
	 * @param order
	 * @return
	 */
	public static Map<Integer, Integer> getCoursePriceMap(Order order) {
		Map<Integer, Integer> coursePriceMap = new HashMap<>();

		String[] prices = order.getPrices().split(",");
		for (String price : prices) {
			if (!price.isEmpty()) {
				String[] idAndPrice = price.split(":");
				if (Optional.ofNullable(idAndPrice[0]).isPresent() && Optional.ofNullable(idAndPrice[1]).isPresent()) {
					coursePriceMap.put(Integer.parseInt(idAndPrice[0]), Integer.parseInt(idAndPrice[1]));
				}
			}
		}

		return coursePriceMap;
	}

	/**
	 * 組出畫面的信用卡資訊
	 * @param creditCards
	 * @return
	 */
	public static List<CreditCardForFronted> geneCreditCardForFronted(List<CreditCard> creditCards) {
		List<CreditCardForFronted> creditCardForFronteds = new ArrayList<>();

		for (CreditCard creditCard : creditCards) {
			CreditCardForFronted creditCardForFronted = new CreditCardForFronted();
			creditCardForFronted.setId(creditCard.getId());
			creditCardForFronted.setType(creditCard.getCreditCardType());
			creditCardForFronted.setNo(creditCard.getCreditCardNo());
			creditCardForFronted.setIsDefault(creditCard.isDefaultFlag() ? "是" : "否");
			creditCardForFronteds.add(creditCardForFronted);
		}

		return creditCardForFronteds;
	}

	/**
	 * 透過信用卡編號取得信用片資訊
	 * @param creditCards
	 * @param cardId
	 * @return
	 */
	public static CreditCard findCredit(List<CreditCard> creditCards, int cardId) {
		CreditCard filterCreditCard = null;
		if(Optional.ofNullable(creditCards).isPresent()) {
			filterCreditCard = creditCards.stream().filter(creditCard -> creditCard.getId() == cardId).findAny()
					.orElse(null);
		}
		return filterCreditCard;
	}
	
	/**
	 * 組出訂單entity
	 * @param user
	 * @param courses
	 * @param courseDiscountPrice
	 * @param card
	 * @return
	 */
	public static Order getOrder(User user, List<Course> courses, String courseDiscountPrice, CreditCard card) {
		Order order = new Order();
		order.setUser(user);
		order.setCourseSize(courses.size());
		order.setAmount(Integer.parseInt(courseDiscountPrice.replace(",", "")));
		String prices = getPrices(courses);
		order.setPrices(prices);
		order.setCreateTime(new Timestamp(System.currentTimeMillis()));
		order.setCourses(courses);
		order.setCreditCardNo(card.getCreditCardNo());
		return order;
	}
	
	/**
	 * 組出畫面上的所有訂單跟課程內容
	 * @param orders
	 * @return
	 */
	public static OrdersAndCoursesForFronted getOrdersAndCoursesForFronted(List<Order> orders) {
		int orderSize = Optional.ofNullable(orders).isPresent() ? Optional.ofNullable(orders).get().size() : 0;
		
		// 先轉成Map<Integer, List<Course>>
		Map<Integer, List<Course>> ordersCoursesMap = orders.stream()
				.collect(Collectors.toMap(Order::getId, Order::getCourses));
		
		// 轉成List<Course>
		List<List<Course>> coursess = ordersCoursesMap.values().stream().collect(Collectors.toList());
		List<Course> courses = coursess.stream().flatMap(List::stream).collect(Collectors.toList());
		// 最後再轉成Map<Integer, Integer>
		Map<Integer, Integer> ordersCoursesIdMap = courses.stream()
				.collect(Collectors.toMap(Course::getId, Course::getId));

		return new OrdersAndCoursesForFronted(orderSize, orders, ordersCoursesIdMap);
	}
	
	/**
	 * 取得多筆課程價錢清單
	 * @param courses
	 * @return
	 */
	private static String getPrices(List<Course> courses) {
		List<String> prices = new ArrayList<>();
		for(Course course : courses) {
			StringBuffer sb = new StringBuffer();
			if(course.getDiscountPrice() == -1) {
				prices.add(sb.append(course.getId()).append(":").append(String.valueOf(course.getPrice())).toString());
			} else {
				prices.add(sb.append(course.getId()).append(":").append(String.valueOf(course.getDiscountPrice())).toString());
			}
		}
		return String.join(",", prices);
	}
	
	/**
	 * 取得評論entity
	 * @param user
	 * @param course
	 * @param reviewForm
	 * @return
	 */
	public static Review getReview(User user, Optional<Course> course, ReviewForm reviewForm) {
		Review review = new Review();
		review.setUser(user);
		review.setRating(Integer.parseInt(reviewForm.getRating()));
		review.setComment(reviewForm.getComment());
		review.setCourse(course.orElse(null));
		review.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return review;
	}
	
	/**
	 * 組出畫面上的多組評論
	 * @param reviews
	 * @return
	 */
	public static List<ReviewForFronted> getReviewForFronted(List<Review> reviews) {
		List<ReviewForFronted> reviewForFronteds = new ArrayList<>();
		for(Review review : reviews) {
			ReviewForFronted reviewForFronted = getReviewForFronted(review);
			reviewForFronteds.add(reviewForFronted);
		}
		return reviewForFronteds;
	}
	
	/**
	 * 組出畫面上的單一評論
	 * @param review
	 * @return
	 */
	public static ReviewForFronted getReviewForFronted(Review review) {
		ReviewForFronted reviewForFronted = null;
		if(Optional.ofNullable(review).isPresent()) {
			reviewForFronted = new ReviewForFronted();
			reviewForFronted.setUsername((Optional.ofNullable(review.getUser().getUsername()).isPresent() ? Optional.ofNullable(review.getUser().getUsername()).get().substring(0, 1) : ""));
			reviewForFronted.setRating(review.getRating());
			reviewForFronted.setComment(review.getComment());
			reviewForFronted.setReviewDate(geneFormatDate(review.getCreateDate()));
		}
		return reviewForFronted;
	}
	
	/**
	 * 組出畫面上的評論資訊
	 * @param reviewInfo
	 * @return
	 */
	public static AllReviewForFronted geneAllReviewForFronted(ReviewInfo reviewInfo) {
		AllReviewForFronted allReviewForFronted = geneAllReviewForFronted(reviewInfo.getAllReviews());
		
		// 使用者本身的評論
		ReviewForFronted userReview = ControllerUtils.getReviewForFronted(reviewInfo.getUserReview());
		// 其他人的評論
		List<ReviewForFronted> otherReviews = ControllerUtils.getReviewForFronted(reviewInfo.getOtherReviews());
		
		allReviewForFronted.setUserReview(userReview);
		allReviewForFronted.setOtherReviews(otherReviews);
		
		return allReviewForFronted;
	}
	
	/**
	 * 組出畫面上的評論資訊
	 * @param reviewInfo
	 * @return
	 */
	public static AllReviewForFronted geneAllReviewForFronted(List<Review> reviews) {
		AllReviewForFronted allReviewForFronted = new AllReviewForFronted();
		
		int reviewSize = reviews.size();		
		
		// 不同評分的總和
		Map<Integer, Double> ratingSumMap = reviews.stream().collect(Collectors.groupingBy(Review::getRating, Collectors.summingDouble(Review::getRating)));
		double ratingValuesSum = ratingSumMap.entrySet().stream().mapToDouble(x -> x.getValue()).sum();
		// 不同評分的數量
		Map<Integer, Long> ratingCountMap = reviews.stream().collect(Collectors.groupingBy(Review::getRating, Collectors.counting()));
		Long ratingKeysSum = ratingCountMap.entrySet().stream().mapToLong(x -> x.getValue()).sum();
		double averageRating =  Math.round(ratingValuesSum/ratingKeysSum * 10.0) / 10.0;
		
		allReviewForFronted.setRatingCountMap(ratingCountMap);
		allReviewForFronted.setReviewCount(reviewSize);
		allReviewForFronted.setAverageRating(averageRating);
		
		return allReviewForFronted;
	}
}
