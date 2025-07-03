package com.example.lottery.service.business;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.example.lottery.dto.response.LotteryNumbers;
import com.example.lottery.service.LotteryService;

@Service
@RefreshScope
public class StandardLotteryService implements LotteryService {
    private final int lotteryMax;
    private final int lotterySize;
    
	public StandardLotteryService(
			@Value("${lotteryMax}") int lotteryMax, 
			@Value("${lotterySize}") int lotterySize) {
		this.lotteryMax = lotteryMax;
		this.lotterySize = lotterySize;
	}

	@Override
	public List<LotteryNumbers> draw(int column) {
		return IntStream.range(0, column)
				        .mapToObj(_ -> draw())
				        .toList();
	}

	private LotteryNumbers draw() {
		return new LotteryNumbers(ThreadLocalRandom.current().ints(1, lotteryMax)
				                          .distinct()
				                          .limit(lotterySize)
				                          .sorted()
				                          .boxed()
				                          .toList());
				                          
	}
}
