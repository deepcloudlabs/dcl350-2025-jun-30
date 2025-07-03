package com.example.lottery.client.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lottery.client.model.LotteryModel;

@FeignClient("lottery")
public interface LotteryService {

	@GetMapping(value="/api/v1/numbers",params = {"column"})
	List<LotteryModel> getLotteryNumbers(@RequestParam int column);
}
