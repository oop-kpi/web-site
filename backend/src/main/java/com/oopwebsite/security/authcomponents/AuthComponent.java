package com.oopwebsite.security.authcomponents;

import com.oopwebsite.wrappers.UserWrapper;

public interface AuthComponent {
    boolean canModify(String labId, UserWrapper wrapper, int ball);

    boolean canUpdateUser(String login, UserWrapper userWrapper);
}
