package com.java4raju.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4raju.userservice.dto.TransactionRequestDto;
import com.java4raju.userservice.dto.TransactionResponseDto;
import com.java4raju.userservice.dto.TransactionStatus;
import com.java4raju.userservice.entity.UserTransaction;
import com.java4raju.userservice.repository.UserRepository;
import com.java4raju.userservice.repository.UserTransactionRepository;
import com.java4raju.userservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;

    /**
     * Check for update if updated then Status set to approved else declined
     * @param requestDto
     * @return Mono<TransactionResponseDto>
     */
    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto){
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                        .filter(Boolean::booleanValue)
                        .map(b -> EntityDtoUtil.toEntity(requestDto))
                        .flatMap(this.transactionRepository::save)
                        .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                        .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(int userId){
        return this.transactionRepository.findByUserId(userId);
    }

}
