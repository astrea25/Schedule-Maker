package app.services;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.entities.User;
import app.repositories.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    private Map<String, User> activeSessions = new ConcurrentHashMap<>();

    public User register(String username, String password, String name, String course, String phoneNumber) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setCourse(course);
        user.setPhoneNumber(phoneNumber);
        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        return user;
    }
    
    public String login(String username, String password) {
        User user = authenticate(username, password);
        String sessionId = UUID.randomUUID().toString();
        activeSessions.put(sessionId, user);
        return sessionId;
    }
    
    public void logout(String sessionId) {
    	if (!activeSessions.containsKey(sessionId)) {
            throw new RuntimeException("Invalid session ID or session already logged out");
        }
        activeSessions.remove(sessionId);
    }

    public User getUserFromSession(String sessionId) {
        return activeSessions.get(sessionId);
    }
    
    public Map<String, User> getActiveSessions() {
    	return this.activeSessions;
    }
}
