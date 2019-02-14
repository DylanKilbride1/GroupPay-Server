**Lessons Learned From Mistakes**
-

**Receiving data from URL Parameter**
- Don't specify the 'consumes = xxx'. Allow spring to decide how to handle data


**Updating Data in DB**
- PUT expects a full object - Use it for updating. It will replace the existing object with the new one
- PATCH Can accept just the fields to be updated. This will replace just the column in the request

**GSON**
- When sending JSON Requests, if a key has a NULL value, it wont include that key/value pair in the body