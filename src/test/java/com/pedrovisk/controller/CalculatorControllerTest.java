package com.pedrovisk.controller;

import com.pedrovisk.api.RandomApi;
import com.pedrovisk.repository.UserRepository;
import com.pedrovisk.service.RandomGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;


class CalculatorControllerTest {


    @Test
    void getRandomString() {

        RandomApi randomApi = mock(RandomApi.class);
        UserRepository userRepository = mock(UserRepository.class);
        RandomGeneratorService randomGeneratorService = new RandomGeneratorService(randomApi);
        CalculatorController calculatorController = new CalculatorController(randomGeneratorService, userRepository);
        Mockito.when(randomApi.generateRandomString()).thenReturn("abcdefghijklmnop");


        String response = calculatorController.getRandomString();
        Assertions.assertEquals("abcdefghijklmnop", response);
        verify(randomApi, times(1)).generateRandomString();

    }



}