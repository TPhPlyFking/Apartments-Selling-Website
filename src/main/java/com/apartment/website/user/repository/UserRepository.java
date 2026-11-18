package com.apartment.website.user.repository;

import com.apartment.website.user.entity.User;

public interface UserRepository {
    /** Tìm tài khoản theo số điện thoại HOẶC email (ô "SĐT chính hoặc email" ở trang đăng nhập). Không có thì null. */
    User findByPhoneOrEmail(String phoneOrEmail);

    // ===== [DEMO SQLi - KHÔNG AN TOÀN, chỉ để học môn ATTT, xóa trước khi dùng thật] =====
    /** Giống findByPhoneOrEmail nhưng kiểm tra luôn mật khẩu ngay trong câu SQL (không PreparedStatement). */
    User findByPhoneOrEmailAndPasswordUnsafe(String phoneOrEmail, String password);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByCccd(String cccd);

    /**
     * Tạo tài khoản mới: INSERT users rồi INSERT customers hoặc owners (theo role), trong cùng 1 transaction.
     * Trả về user_id vừa tạo.
     */
    long create(User user);

    /** Cập nhật users.last_login_at = thời điểm hiện tại. */
    void updateLastLogin(long userId);
}
