package com.trade.service;

import com.trade.model.Asset;
import com.trade.model.Coin;
import com.trade.model.User;

import java.util.List;

public interface AssetService {

    Asset createAsset(User user, Coin coin,double quantity);
    Asset getAssetById(Long assetId) throws Exception;
    Asset getAssetByUserId(Long userId, Long assetId);
    List<Asset> getUsersAsset(Long userId);
    Asset updateAsset(Long assetId,double quantity) throws Exception;
    Asset findAssetUserIdAndCoinId(Long userId,String coinId);
    void deleteAsset(Long assetId);
}
