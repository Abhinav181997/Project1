package com.capgemini.repo;

import com.capgemini.beans.Account;

public interface AccountRepo {

	boolean save(Account account);
	Account searchAccount(int Accountnumber);
}
