package com.apartment.website.property.service;

import com.apartment.website.property.dto.PageLink;
import com.apartment.website.property.dto.PageResult;
import com.apartment.website.property.dto.PropertyFilter;
import com.apartment.website.property.entity.Property;
import com.apartment.website.property.repository.PropertyRepository;
import com.apartment.website.user.entity.Owner;

import java.util.ArrayList;
import java.util.List;

/** Logic nghiệp vụ về căn hộ. */
public class PropertyService {
    // căn bán 12 can 1 trang
    public static final int PAGE_SIZE = 12;
    // căn thue 12 can 1 trang
    public static final int RENT_PAGE_SIZE = 12;
    // số căn tối đa ở Căn hộ tương tự
    public static final int SIMILAR_LIMIT = 6;
    // mục "Gợi ý ở khu vực khác >= 3 căn
    public static final int OTHER_DISTRICT_MIN = 3;

    private final PropertyRepository repository;

    public PropertyService(PropertyRepository repository) {
        this.repository = repository;
    }

    public PageResult<Property> search(PropertyFilter filter, int page) {
        int size;
        if (filter.isRent()) {
            size = RENT_PAGE_SIZE;
        } else {
            size = PAGE_SIZE;
        }
        if (page < 1) {
            page = 1;
        }
        return repository.search(filter, page, size); //Nghiệp vụ giới hạn số căn 1 trang
    }


    public List<PageLink> pageLinks(String baseUrl, int current, int total) {
        List<PageLink> links = new ArrayList<PageLink>();

        String previousHref = null;
        if (current > 1) {
            previousHref = baseUrl + (current - 1);
        }
        links.add(createLink("«", previousHref, false));

        int lastShown = 0;
        for (int i = 1; i <= total; i++) {
            boolean isFirstOrLast = (i == 1 || i == total);
            boolean nearCurrent = Math.abs(i - current) <= 2;
            if (!isFirstOrLast && !nearCurrent) {
                continue;
            }
            if (i - lastShown > 1) {
                links.add(createLink("…", null, false));
            }
            links.add(createLink(String.valueOf(i), baseUrl + i, i == current));
            lastShown = i;
        }

        String nextHref = null;
        if (current < total) {
            nextHref = baseUrl + (current + 1);
        }
        links.add(createLink("»", nextHref, false));
        return links;
    }
    // nghiệp vụ phân trang
    private static PageLink createLink(String label, String href, boolean active) {
        PageLink link = new PageLink();
        link.setLabel(label);
        link.setHref(href);
        link.setActive(active);
        return link;
    }

    //Các căn mới nhất cho trang chủ. */
    public List<Property> featured(int count) {
        PropertyFilter noFilter = new PropertyFilter();
        PageResult<Property> firstPage = repository.search(noFilter, 1, count);
        return firstPage.getItems();
    }

    public Property findById(long id) {
        return repository.findById(id);
    }

    public List<String> districts() {
        return repository.districts();
    }

    public Owner ownerOf(long propertyId) {
        return repository.ownerOf(propertyId);
    }

    public int activeListingCount(long ownerId) {
        return repository.activeListingCount(ownerId);
    }

    public List<Property> sameDistrict(Property p) {
        return repository.similar(p, true, SIMILAR_LIMIT);
    }

    public List<Property> otherDistrict(Property p, int sameCount) {
        int limit = SIMILAR_LIMIT - sameCount;
        if (limit < OTHER_DISTRICT_MIN) {
            limit = OTHER_DISTRICT_MIN;
        }
        return repository.similar(p, false, limit);
    }
}
