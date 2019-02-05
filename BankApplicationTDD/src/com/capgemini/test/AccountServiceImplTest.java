package com.capgemini.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientOpeningBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repo.AccountRepo;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

public class AccountServiceImplTest {

	AccountService accountService;
	
	@Mock
	AccountRepo accountRepo;
	
	@Before
	public void setUp() throws Exception {
	
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepo); 
		
	}

	@Test(expected=com.capgemini.exceptions.InsufficientOpeningBalanceException.class)
	public void whenTheAmountIsLessThan500() throws InsufficientOpeningBalanceException
	{
	  accountService.createAccount(1001,200);
	}

	@Test
	public void  whenTheValidInfoIsPassed() throws InsufficientOpeningBalanceException
	{
		Account account=new Account();
		account.setAccountNumber(1001);
		account.setAmount(5000);
		when(accountRepo.save(account)).thenReturn(true);
		assertEquals(account,accountService.createAccount(1001,5000));
	}
	/*
	 * --------------Deposit Amount----------
	 * 
	 */
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	
	public void whenTheValidInfoIsNotPassed() throws InvalidAccountNumberException
	{
		accountService.depositAmount(1001,5000);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAmountShouldBeDepositedSuccessfully() throws InvalidAccountNumberException, InsufficientOpeningBalanceException
	{
		Account account=new Account();
		account.setAccountNumber(1001);
		account.setAmount(5000);
		when(accountRepo.searchAccount(1001)).thenReturn(account);
		assertEquals(account.getAmount()+ 500,accountService.depositAmount(1001,500));
	}

	/*
	 * --------------Withdraw Amount----------
	 * 
	 */	
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	
	public void whenTheValidAccountNumberIsNotPassedForWithdrawsystemShouldThrowException() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		accountService.withdrawAmount(1001,5000);
	}
	
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	
	public void whenTheAmountIsNotSufficient() throws InvalidAccountNumberException, InsufficientBalanceException
	
	{
		Account account=new Account();
		account.setAccountNumber(1001);
		account.setAmount(5000);
		when(accountRepo.searchAccount(1001)).thenReturn(account);
		accountService.withdrawAmount(1001, 6000);
	}

	@Test
	public void whenTheValidInfoIsPassedAmountShouldBeWithdraw() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		Account account=new Account();
		account.setAccountNumber(1001);
		account.setAmount(5000);
		when(accountRepo.searchAccount(1001)).thenReturn(account);
		assertEquals(account.getAmount()-500, accountService.withdrawAmount(1001, 500));
	}
}











