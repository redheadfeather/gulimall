package com.zl.gulimall.gulimallsearch.service;

import com.zl.gulimall.gulimallsearch.vo.SearchParam;
import com.zl.gulimall.gulimallsearch.vo.SearchResult;

/**
 * @author ZhuLing
 * @date 2021/11/17 - 15:39
 */
public interface MallSearchService {
    SearchResult search(SearchParam searchParam);
}
