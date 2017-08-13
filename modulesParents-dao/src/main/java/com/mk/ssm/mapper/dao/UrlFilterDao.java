package com.mk.ssm.mapper.dao;

import com.mk.ssm.bean.entity.UrlFilter;

import java.util.List;

/**
 * <p>Resource: yankee
 * <p>Date: 17-4-3
 * <p>Version: 1.0
 */
public interface UrlFilterDao {

    int createUrlFilter(UrlFilter urlFilter);
    UrlFilter updateUrlFilter(UrlFilter urlFilter);
    void deleteUrlFilter(Long urlFilterId);

    UrlFilter findOne(Long urlFilterId);
    List<UrlFilter> findAll();
}
