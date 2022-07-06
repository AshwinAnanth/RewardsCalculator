package com.rewards.rewardsCalculator.service;

import com.rewards.rewardsCalculator.constants.Constants;
import com.rewards.rewardsCalculator.entity.Transaction;
import com.rewards.rewardsCalculator.model.Rewards;
import com.rewards.rewardsCalculator.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RewardsServiceImpl implements RewardsService {

    @Autowired
    TransactionRepository transactionRepository;

    public Rewards getRewardsByCustomerId(Long customerId) {

        Timestamp lastMonthTimestamp = getDateBasedOnOffSetDays(Constants.DAYS_IN_MONTH);
        Timestamp lastSecondMonthTimestamp = getDateBasedOnOffSetDays(2*Constants.DAYS_IN_MONTH);
        Timestamp lastThirdMonthTimestamp = getDateBasedOnOffSetDays(3*Constants.DAYS_IN_MONTH);

        List<Transaction> lastMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
                customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
        List<Transaction> lastSecondMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
        List<Transaction> lastThirdMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,
                        lastSecondMonthTimestamp);

        Long lastMonthRewardPoints = getRewardsCount(lastMonthTransactions);
        Long lastTwoMonthRewardPoints = getRewardsCount(lastSecondMonthTransactions);
        Long lastThreeMonthRewardPoints = getRewardsCount(lastThirdMonthTransactions);

        return Rewards.builder()
                .customerId(customerId)
                .lastMonthRewardPoints(lastMonthRewardPoints)
                .lastSecondMonthRewardPoints(lastTwoMonthRewardPoints)
                .lastThirdMonthRewardPoints(lastThreeMonthRewardPoints)
                .totalRewards(lastMonthRewardPoints + lastTwoMonthRewardPoints + lastThreeMonthRewardPoints)
                .build();

    }

    public Timestamp getDateBasedOnOffSetDays(int days) {
        return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
    }

    private Long getRewardsCount(List<Transaction> transactions) {
        return transactions.stream().map(this::calculateRewards).mapToLong(r -> r.longValue()).sum();
    }

    private Long calculateRewards(Transaction transaction) {
        if (transaction.getTransactionAmount() > Constants.INITIAL_REWARDS_LIMIT &&
                transaction.getTransactionAmount() <= Constants.SECOND_REWARDS_LIMIT) {
            return Math.round(transaction.getTransactionAmount() - Constants.INITIAL_REWARDS_LIMIT);
        } else if (transaction.getTransactionAmount() > Constants.SECOND_REWARDS_LIMIT) {
            return Math.round(transaction.getTransactionAmount() - Constants.SECOND_REWARDS_LIMIT) * 2
                    + (Constants.SECOND_REWARDS_LIMIT - Constants.INITIAL_REWARDS_LIMIT);
        } else
            return 0L;

    }

}
