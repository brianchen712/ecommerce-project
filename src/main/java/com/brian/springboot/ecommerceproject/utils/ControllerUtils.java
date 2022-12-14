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
	 * ?????????????????????
	 * @param shoppingCartService
	 * @param courseId
	 * @param session
	 * @param request
	 * @throws Exception
	 */
	public static void processAddCart(ShoppingCartService shoppingCartService, @RequestParam("courseId") int courseId,
			HttpSession session, HttpServletRequest request) throws Exception {
		logger.info("in processAddCart");
		// ?????????????????????????????????????????????????????????????????????????????????
		CartAndCourseInfo cartAndCourse = shoppingCartService.addShoppingCart((User) session.getAttribute("user"),
				courseId);
		
		logger.info("cart Id: " + cartAndCourse.getCartId());
		
		// ??????????????????????????????
		Map<Integer, Integer> courseIdsMap = null;
		if (cartAndCourse.getCourses().size() > 0) {
			courseIdsMap = cartAndCourse.getCourses().stream().collect(Collectors.toMap(Course::getId, Course::getId));
		} else {
			courseIdsMap = new HashMap<>();
		}

		// ?????????????????????????????????????????????????????????????????????????????????????????????
		if (Optional.ofNullable(request.getParameter("primaryCourseId")).isPresent()) {
			courseId = Integer.parseInt(request.getParameter("primaryCourseId"));
		}

		// ???????????????????????????????????????????????????????????????session
		if (cartAndCourse.getCourses().size() == 1) {
			session.setAttribute("cartId", cartAndCourse.getCartId());
		}

		session.setAttribute("cartCourseIdMap", courseIdsMap);

		session.setAttribute("cartCourseSize", courseIdsMap.keySet().size());
	}

	

	/**
	 * ????????????????????????+????????????
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
	 * ????????????????????????????????????
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
	 * ????????????????????????????????????
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
	 * ??????????????????????????????
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
	 * ??????????????????????????????(???????????????????????????)
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
	 * ????????????????????????????????????(???????????????????????????)
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
	 * ???????????????????????????
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
	 * ????????????????????????????????????entity???
	 * @param checkoutForm
	 * @param creditCards
	 * @param user
	 * @return
	 */
	public static CreditCard getCreditCard(CheckoutForm checkoutForm, List<CreditCard> creditCards, User user) {
		CreditCard creditCard = new CreditCard();
		if(Optional.ofNullable(checkoutForm.getId()).isPresent()) {
			if(Optional.ofNullable(checkoutForm.getId()).get() == 0) {
				// ??????
				creditCard.setUser(user);
				creditCard.setCreditCardNo(checkoutForm.getCardNo());
				creditCard.setCreditCardType(checkoutForm.getCardNo().startsWith("4") ? "Visa" : "MasterCard");
				creditCard.setExpiredDate(checkoutForm.getExpiredDate());
				creditCard.setCvv(checkoutForm.getCvv());
				// ????????????????????????????????????????????????????????????????????????
				if(Optional.ofNullable(creditCards).isPresent()) {
					if(creditCards.size() == 0) {
						creditCard.setDefaultFlag(true);
					} else {
						creditCard.setDefaultFlag(false);
					}
				}
				creditCard.setCreateDate(new Timestamp(System.currentTimeMillis()));
			} else {
				// ??????
				// ?????????????????????????????????form?????????????????????????????????????????????
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
	 * ????????????????????????????????????
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
	 * ????????????????????????????????????
	 * @param order
	 * @return
	 */
	public static OrderForFronted geneOrder(Order order) {
		OrderForFronted orderForFronted = new OrderForFronted();
		orderForFronted.setId(order.getId());
		orderForFronted
				.setCourseSize(new StringBuffer().append("?????????").append(order.getCourseSize()).append("?????????").toString());
		orderForFronted.setPurchaseDate(geneFormatDate(order.getCreateTime()));
		DecimalFormat df = new DecimalFormat("#,###");
		orderForFronted.setAmount(df.format(order.getAmount()));
		String cardType = getCardType(order.getCreditCardNo());
		orderForFronted.setCreditCard(new StringBuffer().append(cardType).append(" ")
				.append(order.getCreditCardNo()).toString());
		return orderForFronted;
	}
	
	/**
	 * ??????????????????
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
	 * ?????????????????????
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
	 * ????????????????????????????????????
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
	 * ??????????????????????????????
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
	 * ??????????????????????????????
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
			creditCardForFronted.setIsDefault(creditCard.isDefaultFlag() ? "???" : "???");
			creditCardForFronteds.add(creditCardForFronted);
		}

		return creditCardForFronteds;
	}

	/**
	 * ??????????????????????????????????????????
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
	 * ????????????entity
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
	 * ?????????????????????????????????????????????
	 * @param orders
	 * @return
	 */
	public static OrdersAndCoursesForFronted getOrdersAndCoursesForFronted(List<Order> orders) {
		int orderSize = Optional.ofNullable(orders).isPresent() ? Optional.ofNullable(orders).get().size() : 0;
		
		// ?????????Map<Integer, List<Course>>
		Map<Integer, List<Course>> ordersCoursesMap = orders.stream()
				.collect(Collectors.toMap(Order::getId, Order::getCourses));
		
		// ??????List<Course>
		List<List<Course>> coursess = ordersCoursesMap.values().stream().collect(Collectors.toList());
		List<Course> courses = coursess.stream().flatMap(List::stream).collect(Collectors.toList());
		// ???????????????Map<Integer, Integer>
		Map<Integer, Integer> ordersCoursesIdMap = courses.stream()
				.collect(Collectors.toMap(Course::getId, Course::getId));

		return new OrdersAndCoursesForFronted(orderSize, orders, ordersCoursesIdMap);
	}
	
	/**
	 * ??????????????????????????????
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
	 * ????????????entity
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
	 * ??????????????????????????????
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
	 * ??????????????????????????????
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
	 * ??????????????????????????????
	 * @param reviewInfo
	 * @return
	 */
	public static AllReviewForFronted geneAllReviewForFronted(ReviewInfo reviewInfo) {
		AllReviewForFronted allReviewForFronted = geneAllReviewForFronted(reviewInfo.getAllReviews());
		
		// ????????????????????????
		ReviewForFronted userReview = ControllerUtils.getReviewForFronted(reviewInfo.getUserReview());
		// ??????????????????
		List<ReviewForFronted> otherReviews = ControllerUtils.getReviewForFronted(reviewInfo.getOtherReviews());
		
		allReviewForFronted.setUserReview(userReview);
		allReviewForFronted.setOtherReviews(otherReviews);
		
		return allReviewForFronted;
	}
	
	/**
	 * ??????????????????????????????
	 * @param reviewInfo
	 * @return
	 */
	public static AllReviewForFronted geneAllReviewForFronted(List<Review> reviews) {
		AllReviewForFronted allReviewForFronted = new AllReviewForFronted();
		
		int reviewSize = reviews.size();		
		
		// ?????????????????????
		Map<Integer, Double> ratingSumMap = reviews.stream().collect(Collectors.groupingBy(Review::getRating, Collectors.summingDouble(Review::getRating)));
		double ratingValuesSum = ratingSumMap.entrySet().stream().mapToDouble(x -> x.getValue()).sum();
		// ?????????????????????
		Map<Integer, Long> ratingCountMap = reviews.stream().collect(Collectors.groupingBy(Review::getRating, Collectors.counting()));
		Long ratingKeysSum = ratingCountMap.entrySet().stream().mapToLong(x -> x.getValue()).sum();
		double averageRating =  Math.round(ratingValuesSum/ratingKeysSum * 10.0) / 10.0;
		
		allReviewForFronted.setRatingCountMap(ratingCountMap);
		allReviewForFronted.setReviewCount(reviewSize);
		allReviewForFronted.setAverageRating(averageRating);
		
		return allReviewForFronted;
	}
}
