package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // Controller타입의 어노테이션을 빈 스캐닝을 통해 자동으로 빈 등록
public class MemberController {
	
	 //private MemberService memberService = new MemberServiceImpl();
	
	/*
	 * 기존 객체 생성 방식
	 * 객체간의 결합도가 높아진다(소스코드의 수정이 일어날 경우 하나하나 전부 다 바꿔줘야한다.)
	 * 서비스가 동시에 매우 많이 호출될 경우 그만큼 객체가 생성된다.
	 * 
	 * Spring의 DI(Dependency Injection)를 이용한 방식
	 * 객체를 생성해서 주입해줌
	 * new라는 키워드 없이 선언만해준다. @Autowired어노테이션을 반드시 사용해야한다.
	 * 
	 */
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/*
	 * Spring에서 파라미터(요청시 전달값)을 받는 방법
	 * 
	 * 1. HttpServletRequest를 이용해서 전달받기(JSP/Servlet방식)
	 * 		해당 메소드의 매개변수로 HttpServletRequest를 작성해두면
	 * 		스프링컨테이너가 해당 메소드를 호출시 자동으로 해당 객체를 생성해서 매개변수로 주입해줌
	 */
	
	/*
	@RequestMapping(value="/login.me") // RequestMapping타입의 어노테이션을 붙여줌으로써 HandlerMapping 등록
	public String loginMember(HttpServletRequest request) {
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		return "main";
	}
	*/
	
	/*
	 * 2. @RequestPram 어노테이션을 이용하는 방법
	 * request.getParameter("키")로 벨류를 추출하는 대신 해주는 어노테이션
	 * 
	 * value속성의 jsp에서 작성했던 name속성값을 담으면 알아서 해당 매개변수로 받아올 수 있다.
	 * 만약, 넘어온 값이 비어있는 상태라면 defaultValue속성으로 기본값을 지정할 수 있다.
	 *  
	 */
	
	/*
	@RequestMapping(value="/login.me") 
	public String loginMember(@RequestParam(value="id", defaultValue="aaa") String userId,
								@RequestParam(value="pwd") String userPwd) {
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		return "main";
	}
	*/
	
	/*
	 * 3. @RequestPram 어노테이션을 생략하는 방법
	 * 단, 매개변수명을 jsp의 name속성값(요청시 전달하는 값의 키값)과 동일하게 세팅해줘야 자동으로 값이 주입됨
	 * 위에서 썼던 defaultValue 추가 속성은 사용할 수 없음
	 */
	
	/*
	@RequestMapping(value="/login.me") 
	public String loginMember(String id, String pwd) {
		
		Member m = new Member();
		m.setUserId(id);
		m.setUserPwd(pwd);
		
		System.out.println("ID : " + m.getUserId());
		System.out.println("PWD : " + m.getUserPwd());
		
		return "main";
	}
	*/
	
	
	/*
	 * 4. 커맨드 객체 방식
	 * 
	 * 해당 메소드의 매개변수로
	 * 요청시 전달값을 담고자하는 VO 클래스의 타입을 세팅 후
	 * 요청시 전달값의 키값(jsp의 name속성값)을 vo클래스에 담고자하는 필드명으로 작성
	 */
	
	/*
	@RequestMapping(value="/login.me") 
	public String loginMember(Member m) {
		
		System.out.println("ID : " + m.getUserId());
		System.out.println("PWD : " + m.getUserPwd());
		
		Member loginUser = memberService.loginMember(m);
		if(loginUser == null) { // 로그인실패 => 에러문구를 requestScope에 담고 에러페이지로 포워딩
			System.out.println("로그인 실패");
		} else { // 로그인 성공 =>? sessionScope에 로그인유저 담아서 메인으로 url재요청
			System.out.println("로그인 성공");
		}
		
		return "main";
	}
	*/
	
	/*
	 * 요청 처리 후 응답데이터를 담고 응답페이지로 포워딩 또는 url재요청하는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model객체를 이용하는 방법
	 * 포워딩할 응답뷰로 전달하고자하는 데이터를 맵형식(k-v)으로 담을 수 있는 영역
	 * Model객체는 requestScope
	 * *setAttribute가 아니라 addAttribute
	 * 
	 */
	
	/*
	@RequestMapping(value="/login.me") 
	public String loginMember(Member m, Model model, HttpSession session) {
		
		Member loginUser = memberService.loginMember(m);
		
		
		if(loginUser == null) { // 로그인실패 => 에러문구를 requestScope에 담고 에러페이지로 포워딩
			model.addAttribute("errorMsg", "로그인 실패"); 
			
			
			// /WEB-INF/views/       /common/errorPage        .jsp
			return "/common/errorPage";
		} else { // 로그인 성공 =>? sessionScope에 로그인유저 담아서 메인으로 url재요청
			session.setAttribute("loginUser", loginUser);
			
			return "redirect:/";
		}
	
	}
	*/
	
	/*
	 * 2. 스프링에서 제공하는 ModelAndView 객체를 사용하는 방법
	 * 
	 */
	
	@RequestMapping("/login.me") 
	public ModelAndView loginMember(Member m, ModelAndView mv, HttpSession session) {
		
		
		//Member loginUser = memberService.loginMember(m); 
		// 암호화 작업 전
//		if(loginUser == null) { // 로그인실패 => 에러문구를 requestScope에 담고 에러페이지로 포워딩
//			//model.addAttribute("errorMsg", "로그인 실패"); 
//			mv.addObject("errorMsg", "로그인 실패");
//			
//			// /WEB-INF/views/       /common/errorPage        .jsp
//			mv.setViewName("common/errorPage");
//		} else { // 로그인 성공 =>? sessionScope에 로그인유저 담아서 메인으로 url재요청
//			session.setAttribute("loginUser", loginUser);
//			
//			mv.setViewName("redirect:/");
//		}
	
		
		//암호화 작업 후
		// Member m의 userId : 사용자가 입력한 아이디
		//		  m의 userPwd : 사용자가 입력한 비밀번호(평문)
		
		Member loginUser = memberService.loginMember(m); //아이디로만 멤버객체 가져오기
		
		// loginUser userId : 아이디로 호출한 데이터베이스에서 가져온 아이디
		// loginUser userPwd : DB에 기록된 암호화된 비밀번호
		
		//bcryptPasswordEncoder객체 matches()
		// matches(평문, 암호문)을 작성하면 내부적으로 복호화등의 작업이 이루어짐
		// 두 구문이 일치하는지 확인 후 일치하면 true를 반환
		
		if(loginUser == null || !bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) { // 로그인실패 => 에러문구를 requestScope에 담고 에러페이지로 포워딩
			//model.addAttribute("errorMsg", "로그인 실패"); 
			mv.addObject("errorMsg", "로그인 실패");
			
			// /WEB-INF/views/       /common/errorPage        .jsp
			mv.setViewName("common/errorPage");
		} else { // 로그인 성공 =>? sessionScope에 로그인유저 담아서 메인으로 url재요청
			session.setAttribute("loginUser", loginUser);
			
			mv.setViewName("redirect:/");
		}
		
		return mv;
	}
	
	@RequestMapping("/logout.me")
	public ModelAndView logoutMember(ModelAndView mv, HttpSession session) {
		// session.invalidate();
		session.removeAttribute("loginUser");
		
		mv.setViewName("redirect:/");
		
		return mv;
	}
	
	@RequestMapping("/enrollForm.me")
	public String enrollForm() {
		return "member/memberEnrollForm";
	}
	
	@RequestMapping("/insert.me")
	public String insertMember(Member m, HttpSession session, Model model) {
		// 1. 한글깨짐문제 발생 =>> web.xml에 스프링에서 제공하는 인코딩 필터 등록
		// 2. 나이를 입력하지 않을 경우 int자료형에 빈 문자열이 넘어와 자료형이 맞지 많는 문제 발생
		// 	(400 Bad Request Error 발생)
		// Member클래스의 age필드 자료형을 int => String으로 변경
		// 3.비밀번호가 사용자가 입력한 있는 그대로의 평문
		// Bcrypt방식을 이용해서 암호화를 한 후 저장을 하겠다.
		// => 스프링 시큐리팀 모듈에서 제공<pom.xml에 라이브러리 추가>
		
		//암호화작업
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		//System.out.println("암호문 : " + encPwd);
		
		m.setUserPwd(encPwd); // Member객체에 userPwd필드에 평문이 아닌 암호문으로 변경
		
		int result = memberService.insertMember(m);
		
		if (result > 0) {
			session.setAttribute("alertMsg", "성공적으로 회원가입이 완료되었습니다.");
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "회원가입 실패");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("/myPage.me")
	public String myPage() {
		return "member/myPage";
	}
	
	@RequestMapping("/update.me")
	public String updateMember(Member m, HttpSession session, Model model) {
		
		int result = memberService.updateMember(m);
		
		if (result > 0) {
			// DB로부터 수정된 회원정보를 다시 조회해서
			// session영역에 loginUser라는 키값으로 덮어씌워야한다.
			session.setAttribute("loginUser", memberService.loginMember(m));
			session.setAttribute("alertMsg", "회원정보 수정 성공");
			return "redirect:/myPage.me";
		} else {
			model.addAttribute("errorMsg", "회원정보 수정 실패");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("/delete.me")
	public String deleteMember(Member m, HttpSession session) {
		
		//1. 암호화된 비밀번호 가져오기
		String encPwd = ((Member)session.getAttribute("loginUser")).getUserPwd();
		
		if (bcryptPasswordEncoder.matches(m.getUserPwd(), encPwd )) {
			//비밀번호 일치 =>탈퇴가능
			int result = memberService.deleteMember(m.getUserId());
			
			if (result > 0) { //탈퇴처리 성공
				session.removeAttribute("loginUser");
				session.setAttribute("alertMsg", "탈퇴가 성공적으로 이루어졌습니다.");
				return "redirect:/";
			} else { // 탈퇴처리 실패
				session.setAttribute("alertMsg", "탈퇴처리 실패");
				return "redirect:/myPage.me";
			}
			
		} else {
			//비밀번호 불일치 => 탈퇴불가
			session.setAttribute("alertMsg", "비밀번호를 다시 확인해주세요");
			return "redirect:/myPage.me";
		}
	}
	
	@ResponseBody
	@RequestMapping("/idCheck.me")
	public String idCheck(String checkId) {
//		int result = memberService.idCheck(checkId);
//		
//		if (result > 0) { // 이미 존재하는 아이디가 있을경우
//			return "NNNNN";
//		} else { // 사용가능한 아이디일 경우
//			return "NNNNY";
//		}
		
		return memberService.idCheck(checkId) > 0 ? "NNNNN" : "NNNNY";
	}
	
}
