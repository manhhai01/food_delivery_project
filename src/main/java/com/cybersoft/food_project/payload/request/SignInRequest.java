package com.cybersoft.food_project.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SignInRequest {

    @NotBlank(message = "Vui lòng nhập username!")
    private String username;

    @NotBlank(message = "Vui lòng nhập password!")
//    @Pattern(regexp = "^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}$",
//            message = "Mật khẩu phải bao gồm chữ hoa, chữ thường, ký tự đặc biệt, phải lớn hơn hoặc bằng 8 ký tự.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
