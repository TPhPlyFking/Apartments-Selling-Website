package com.apartment.website.property.repository;

import com.apartment.website.property.dto.PageResult;
import com.apartment.website.property.dto.PropertyFilter;
import com.apartment.website.property.entity.Property;
import com.apartment.website.user.entity.Owner;

import java.util.List;

public interface PropertyRepository {
    PageResult<Property> search(PropertyFilter filter, int page, int size);

    Property findById(long id);

    // ===== [DEMO SQLi - KHÔNG AN TOÀN, chỉ để học môn ATTT, xóa trước khi dùng thật] =====
    /** Giống findById nhưng id truyền thẳng vào SQL bằng chuỗi (không PreparedStatement) - demo ?id= trên URL. */
    Property findByIdUnsafe(String id);

    List<String> districts();

    List<Property> similar(Property p, boolean sameDistrict, int limit);

    Owner ownerOf(long propertyId);

        int activeListingCount(long ownerId);
}
