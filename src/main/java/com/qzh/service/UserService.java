package com.qzh.service;

import com.qzh.domain.User;
import com.qzh.req.UserQueryReq;
import com.qzh.req.UserResetPasswordReq;
import com.qzh.req.UserSaveReq;
import com.qzh.resp.PageResp;
import com.qzh.resp.UserQueryResp;

/**
 * @packageName:com.qzh.service
 * @ClassName:EBookService
 * @date:2021/7/913:37
 */
public interface UserService {
    public PageResp<UserQueryResp> list(UserQueryReq req);

    public void save(UserSaveReq req);

    public void delete(Long id);

    public User selectByLoginName(String loginName);

    void resetPassword(UserResetPasswordReq req);
}
