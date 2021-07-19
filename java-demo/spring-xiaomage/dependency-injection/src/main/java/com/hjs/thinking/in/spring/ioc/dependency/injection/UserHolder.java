package com.hjs.thinking.in.spring.ioc.dependency.injection;

import com.hjs.thinking.in.spring.ioc.overview.domain.User;

/**
 * @author 俊语
 * @date 2021/7/18 下午7:23
 */
public class UserHolder {
    private User user;

    public UserHolder() {
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}
