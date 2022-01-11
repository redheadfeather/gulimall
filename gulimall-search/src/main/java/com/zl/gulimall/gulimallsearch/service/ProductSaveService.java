package com.zl.gulimall.gulimallsearch.service;

import com.zl.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author ZhuLing
 * @date 2021/11/14 - 13:53
 */
public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
