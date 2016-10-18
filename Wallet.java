/* --------------------------------------------------------------------------------------------------------------- */ 
/* Wallet class (data type) which implements the WalletI interface.                                                */ 
/* --------------------------------------------------------------------------------------------------------------- */ 

import java.lang.IllegalArgumentException; 

public class Wallet implements WalletI { 
// Instance variable defs: 
   private MoneyI contents; 

// Invariant def: 
   private boolean invariant() {  
       return this.contents != null && this.contents.totalEuro() >= 0 && this.contents.remainingCent() >= 0;  
   } 

// Constructor defs: 
   public Wallet() { this.contents = new Money(0, 0); } 

// Method defs: 
/** Check the amount of money contained in this wallet; return the amount of money in this wallet. */ 
   public MoneyI check() { return this.contents; } 

/** Put a sum of money in to this wallet; increasing amount of money in this wallet. */  
   public void put(MoneyI money) { 
       if (money == null) throw new IllegalArgumentException("[money: " + money + "]"); 
       this.contents = this.contents.plus(money); 
   } 

/** Take a sum of money from this wallet; assuming the sum taken is least equal to the amount of money contained */

/** in this wallet; otherwise the operation raises an InsufficientMoneyInWalletException and has no effect on */

/** this wallet. */  

   public void take(MoneyI money) throws InsufficientMoneyInWalletException { 
       if (money == null) throw new IllegalArgumentException("[money: " + money + "]"); 
       try { 
         this.contents = this.contents.minus(money); /* May throw a MoneyI.InsufficientMoneyException */ 
       } 
       catch (MoneyI.InsufficientMoneyException e) { 
         throw new InsufficientMoneyInWalletException(); 
       } 
   } 
}