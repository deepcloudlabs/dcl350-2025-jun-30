package com.example.lottery.service;

import java.util.List;

import com.example.lottery.dto.response.LotteryNumbers;

public interface LotteryService {

	List<LotteryNumbers> draw(int column);

}
