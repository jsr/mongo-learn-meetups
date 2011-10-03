These classes will be used throughout our code as we move forward. 

**Nothing here which is MongoDB specific.**

### BCrypt
We'll use [BCrypt](http://en.wikipedia.org/wiki/Bcrypt) to hash a user's password

### ExactSizeList + ExactSizeListCheck
Validation annotation and logic to ensure that a list is of an exact size/length.

### FieldError
This just wraps validation errors, either raised by the Play framework, or manually be our code, into a single object.

### MaxListItemSize + MaxListItemSizeCheck
Validation annotation and logic to ensure that items within a list aren't larger than the  specified size (this checks the items within the list)

### MaxListSize + MaxListSizeCheck
Validation annotation and logic to ensure that a list isn't larger than the specified size (this checks the list itself)

### PagedResult + PagedResultGsonAdapter
A paged results includes everything our UI needs to render a generic list of objects with paging. The PagedResultGsonAdapter tells GSON how to serialize this object to JSON.