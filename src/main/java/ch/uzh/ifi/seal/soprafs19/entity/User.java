package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false) 
	private String password;

	@Column(nullable = false, unique = true) 
	private String token;

	@Column(nullable = false)
	private UserStatus status;

	@Column(nullable = false)
	private String creationDate;

	@Column(nullable = false)
	private int err;



	@Column(nullable = false)
	private String Birthday;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getDate() {
		return creationDate;
	}

	public void setDate() {
		this.creationDate = new Date().toString();
	}

	public int getError() {
		return err;
	}

	public void setError(int err) {
		this.err = err;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setBirthday(String birthday) {
		Birthday = birthday;
	}



	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return this.getId().equals(user.getId());
	}
}
