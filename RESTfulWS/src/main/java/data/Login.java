package data;

import java.io.Serializable;

public class Login implements Serializable {
    
    private static final long serialVersionUID = 1L;
	private String username;
    private String password;

    public Login() {

    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUSername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}