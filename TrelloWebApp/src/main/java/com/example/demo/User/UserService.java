package com.example.demo.User;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.ConfirmationToken.ConfirmationToken;
import com.example.demo.ConfirmationToken.ConfirmationTokenService;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ConfirmationTokenService confirmationTokenService;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		final Optional<User> optionalUser = userRepo.findByEmail(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			String au = user.getUserRole().name();
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(au));
			return new MyUserDetails(user.getEmail(), user.getPassword(), authorities);
        }
        else {
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", username));
        }	
	}

	public void signUpUser(User user) {
		// check user email exist in database ???
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());  
	    user.setPassword(encryptedPassword);
	    user.setAuthProvider(AuthenticationProvider.GMAIL);
	    userRepo.save(user);
	    final ConfirmationToken confirmationToken = new ConfirmationToken(user);
	    confirmationTokenService.saveConfirmationToken(confirmationToken);
	    sendConfirmationMail(user.getEmail(), confirmationToken.getConfirmationToken());
	}
	
	void sendConfirmationMail(String userMail, String token) {

		final SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userMail);
		mailMessage.setSubject("Mail Confirmation Link!");
		mailMessage.setFrom("<MAIL>");
		mailMessage.setText(
				"Thank you for registering. Please click on the below link to activate your account." 
						+ "http://localhost:8080/sign-up/confirm?token="
						+ token);

		emailSenderService.sendEmail(mailMessage);
	}
	
	void confirmUser(ConfirmationToken confirmationToken) {
		final User user = confirmationToken.getUser();
		user.setEnabled(1);
		userRepo.save(user);
		confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
	
	}
}
