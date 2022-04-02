package com.yxm.service;

import com.yxm.vo.*;

import java.util.List;

public interface IHomePageService {
    HomePageWallet selectHomePageData(Integer userId);
    int selectDiscount(Integer userId);
    OrderAndMenu DataCount(Integer userId);
    List<CollectAndMenu> selectCollect(Integer userId);
    boolean removeMenu(Integer Id);
}
