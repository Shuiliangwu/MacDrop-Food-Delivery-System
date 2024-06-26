## The statuses of food order
- ```location & time needed``` After the community member sent and paid the order, location and time are needed to be selected by community member. 
- ```placed``` After location and time are selected, the food order turns to be placed. but not placed. 
- ```refused``` The food order turns to be refused if food provider refused it. 
- ```accepted & waiting for pickup``` If the food provider is satified with the location and the estimated pickup time, food provider can accept it. The food order turns to be accepted & waiting for pickup. 
- ```delivering``` After the biker picked up the food, the food order turns to be delivering. 
- ```dropped-off & complete``` After the biker dropped off the food, the food order turns to be dropped-off & complete. 

## Patterns for role ID
- CommunityMemberID: ```1xxxxxxx``` (starts with 1)
- FoodProviderID: ```2xxxxxxx``` (starts with 2)
- BikerID: ```3xxxxxxx``` (starts with 3)
- BookstoreOperatorID: ```4xxxxxxx``` (starts with 4)
- AdminID: ```5xxxxxxx``` (starts with 5)
- FoodID: FoodProviderID+xxxx (It ensures the uniqueness)
- BookStoreItemID: BookstoreOperatorID+xxxx (It ensures the uniqueness)
- LocaltionID: ```8xxxxxxx``` (starts with 8)