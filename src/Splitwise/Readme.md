# Requirements
1. Manage friends
2. Manage groups
3. Manage expenses in 1 and 2
4. Split expenses capability
   1. Equal
   2. Unequal
   3. Percentage wise
5. Balance sheet of each user

# Object Identification
1. Splitwise (driver app)
2. User
3. Group
4. Expense
5. SplitCapability
6. BalanceSheet


# Some points:
Point: What happens when expense is created?
Say, 
Lunch -> 1000
-> Equally : 
1. f1 = 250
2. f2 = 250
3. f3 = 250
4. f4 = 250

What balance shows?
1. Total owe/owes
2. Friend-wise owe/owes

******
# UML
***Expense***
1. id : String
2. desc : String
3. amount : double
4. paidBy : User
5. splitType : SplitType
6. splits : List<Split>

HAS -> Split

***Split***
1. user : User
2. amount : double 
3. percent : double

ENUM ***SplitType***
1. EQUAL
2. UNEQUAL
3. PERCENTAGE

INTERFACE ***ExpenseSplit***
validateRequest()

1. EqualSplit
2. UnequalSplit
3. PercentSplit

***User*** 
1. id : String
2. name : String
3. balances : BalanceSheet

***Group***
1. id : String
2. name : String
3. user : List<User>
4. expenses : List<Expense>

HAS ->ExpenseController

***BalanceSheet***
1. friendBalance : Map<User, Balance>
2. totalExpense : double
3. totalOwe : double
4. totalOwes : double

***Balance***
1. owe : double
2. owes : double 

***Splitwise***
1. UserController

******
# Controllers
1. ExpenseController
- Create an expense (for now, can be extended in future) 
- Calls BalanceSheetController
2. UserController
- Contains list of users
3. GroupController 
- List of groups
4. BalanceSheetController 
- Business logic
******