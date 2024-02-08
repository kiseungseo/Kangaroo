package com.mysite.kangaroo.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysite.kangaroo.entity.Users;
import com.mysite.kangaroo.entity.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository; // UserRepository 주입
    private final UserProfileRepository userProfileRepository; 
    
    @Autowired
    private AuthenticationManager authenticationManager;

    //회원가입
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup";
    }
    
    //회원가입 from
    @PostMapping("/signup")
    public String signup(UserCreateForm userCreateForm, BindingResult bindingResult, HttpServletRequest request) {
        // 사용자 이름 필드 검증
        if (userCreateForm.getUserId() == null || userCreateForm.getUserId().trim().isEmpty()) {
            bindingResult.rejectValue("userId", "userId.required", "사용자ID는 필수항목입니다.");
            return "user/signup";
        }

        // 비밀번호 필드 검증
        if (userCreateForm.getPassword1() == null || userCreateForm.getPassword1().trim().isEmpty()) {
            bindingResult.rejectValue("password1", "password.required", "비밀번호는 필수항목입니다.");
            return "user/signup";
        }

        // 비밀번호 확인 필드 검증
        if (!userCreateForm.getPassword2().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user/signup";
        }
        
        // 이름 필드 검증
        if (userCreateForm.getUserName() == null || userCreateForm.getUserName().trim().isEmpty()) {
            bindingResult.rejectValue("userName", "userName.required", "이름은 필수항목입니다.");
            return "user/signup";
        }
        
        // 이메일 필드 검증
        if (userCreateForm.getEmail() == null || userCreateForm.getEmail().trim().isEmpty()) {
            bindingResult.rejectValue("email", "email.required", "이메일은 필수항목입니다.");
            return "user/signup";
        }
        
        // 생년월일 필드 검증
        if (userCreateForm.getBirth() == null) {
            bindingResult.rejectValue("birth", "birth.required", "생년월일은 필수항목입니다.");
            return "user/signup";
        }
        
        // 핸드폰번호 필드 검증
        if (userCreateForm.getPhone() == null || userCreateForm.getPhone().trim().isEmpty()) {
            bindingResult.rejectValue("phone", "phone.required", "생년월일은 필수항목입니다.");
            return "user/signup";
        }

        Users user = userService.create(userCreateForm.getUserId(), userCreateForm.getEmail(), userCreateForm.getPassword1(), 
                userCreateForm.getUserName(), userCreateForm.getBirth(),userCreateForm.getPhone());

            // 사용자를 바로 인증합니다.
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userCreateForm.getUserId(), userCreateForm.getPassword1());
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            // 세션에 SPRING_SECURITY_CONTEXT 속성을 추가합니다.
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            // 세션에 사용자 정보 저장
            session.setAttribute("user", user);

            return "redirect:/user/profile";
    }
    
    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails, HttpSession session) {
        // userDetails에서 사용자 정보를 가져옵니다.
        String username = userDetails.getUsername();

        // username으로 UserDTO를 조회합니다.
        Users user = userRepository.findByUserId(username).orElse(null);

        // 사용자 정보가 null이 아닌 경우에만 모델에 추가
        if (user != null) {
            model.addAttribute("user", user);
        }

        Users sessionUser = (Users) session.getAttribute("user");


        // 데이터베이스에서 사용자 프로필 정보 조회
        UserProfile userProfile = userProfileRepository.findByUser_UserId(sessionUser.getUserId()).orElse(null);

        // 모델에 사용자 프로필 정보 추가
        model.addAttribute("userProfile", userProfile);

        // UserService의 getStatusMessage 메서드를 이용해 statusMessage를 가져오고 모델에 추가
        String statusMessage = userService.getStatusMessage(sessionUser.getUserId());
        model.addAttribute("statusMessage", statusMessage);
        

        return "user/profile";
    }
    
    //프로필 업로드
    @PostMapping("/profile/upload")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file,
                                       @RequestParam("message") String message,
                                       RedirectAttributes redirectAttributes,
                                       HttpSession session) {
        try {
            // 파일이 비어있는지 체크
            if (file.isEmpty()) {
                // 파일이 없으면 메시지를 추가하고 프로필 페이지로 리다이렉트
                redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
                return "redirect:/user/profile";
            }

            // 세션에서 사용자 정보 가져오기
            Users sessionUser = (Users) session.getAttribute("user");

            // 데이터베이스에서 사용자 정보 재조회
            Users user = userRepository.findByUserId(sessionUser.getUserId()).orElse(null);
            if(user == null) {
                // 세션에 유효한 사용자 정보가 없으면 메시지를 추가하고 프로필 페이지로 리다이렉트
                redirectAttributes.addFlashAttribute("message", "Invalid user session.");
                return "redirect:/user/profile";
            }

            // 파일을 서버에 저장
            byte[] bytes = file.getBytes();
            String relativePath = user.getUserId() + "/" + file.getOriginalFilename();
            Path path = Paths.get("C:/uploads/" + relativePath); // 경로 변경
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);

            // 사용자 프로필 사진 정보 업데이트
            UserProfile userProfile = user.getUserProfile();
            if(userProfile == null) {
                userProfile = new UserProfile();
                userProfile.setUser(user);
            }
            userProfile.setProfilePicture(relativePath);
            userProfile.setStatusMessage(message);
            userProfileRepository.save(userProfile);

            // 상태 메시지 업데이트 후 세션 업데이트
            Users updatedUser = userRepository.findByUserId(user.getUserId()).orElse(null);
            if (updatedUser != null) {
                session.setAttribute("user", updatedUser);
            }

            // 타임스탬프를 생성하고 이를 리다이렉트 속성에 추가
            redirectAttributes.addFlashAttribute("timestamp", Instant.now().toEpochMilli());

            // 성공 메시지를 추가하고 프로필 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            // 파일 저장 중 오류가 발생하면 스택 트레이스를 출력
            e.printStackTrace();
        }

        return "redirect:/user/profile";
    }

	//로그인 
	@GetMapping("/login")
	public String login() {
	  return "user/login";
	}
	
	//메인페이지
	@GetMapping("/")
	public String main(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	    // userDetails에서 사용자 정보를 가져옵니다.
	    String username = userDetails.getUsername();

	    // username으로 UserDTO를 조회합니다.
	    Users user = userRepository.findByUserId(username).orElse(null);

	    // 사용자 정보가 null이 아닌 경우에만 모델에 추가
	    if (user != null) {
	    	model.addAttribute("user", user); // 추가
	        model.addAttribute("userProfile", user.getUserProfile());
	    }
	    return "kangaroo_main";
	}
	
	//구글 로그인
	@GetMapping("/loginForm")
    public String home() {
        return "user/loginForm";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "user/privatePage";
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "user/adminPage";
    }
}
