package Novem.Finance.Transaction;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Novem.Finance.Models.Account;
import Novem.Finance.Models.BankTransaction;
import Novem.Finance.Service.AccountService;
@Service
public class Withdraw implements Itransaction
{

	private String AccountNumber;	//Holds The Account Number to WithDraw from
	@Autowired	
	private AccountService accountService; //Instance of the Account Service
	
	private Optional<Account> Taccount;		//Hold Account
	private Account existingAccount;		//Hold Account
	
			
	
	private BigDecimal balance;
	private BigDecimal amount;
	private BigDecimal newBalance;
	
	
	public Withdraw() {}
	
	public Withdraw(BankTransaction Transaction, AccountService Aserve) {
		amount = new BigDecimal(Transaction.getAmount()).setScale(2);
		this.accountService=Aserve;
		
	
		this.AccountNumber = Transaction.getAccount1();
	}


	
	
	public boolean execute() 
		{
		Taccount=accountService.GetAccount(AccountNumber);
		
			if(Taccount.isPresent()) 
			{
		
			existingAccount= Taccount.get();
		    balance= new BigDecimal(existingAccount.getAccountBalance()).setScale(2);
		    
		    newBalance =balance.subtract(amount);
		    
		    BigDecimal Zero = new BigDecimal(0); 
		    int res= newBalance.compareTo(Zero);
		    if(res>=0)
		    {
		    	existingAccount.setAccountBalance(newBalance.toString());
		    	accountService.AddAccount(existingAccount);
		    return true;
			}
		    else return false;
			}
		else		
			return false;
		}

}
