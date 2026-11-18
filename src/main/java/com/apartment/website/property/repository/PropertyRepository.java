package com.apartment.website.property.repository;

import com.apartment.website.property.dto.PageResult;
import com.apartment.website.property.dto.PropertyFilter;
import com.apartment.website.property.entity.Property;
import com.apartment.website.user.entity.Owner;

import java.util.List;

public interface PropertyRepository {
    PageResult<Property> search(PropertyFilter filter, int page, int size);

    Property findById(long id);

    List<String> districts();

    List<Property> similar(Property p, boolean sameDistrict, int limit);

    Owner ownerOf(long propertyId);

        int activeListingCount(long ownerId);
}
