package com.rewards.rewardsCalculator.service;

import com.rewards.rewardsCalculator.model.Rewards;

public interface RewardsService {
    Rewards getRewardsByCustomerId(Long customerId);
}
