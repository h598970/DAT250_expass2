# DAT250 Expass2 hand-in for Andreas Øihaugen

### [Repo of created code](https://github.com/h598970/DAT250_expass2)

## Issues during coding
- First obstacle that needed extra attention from me was the domain model as I am used to being able to think of objects
as being in a relation like a relation database. Due to the nature of the assignment entity relations was not to be
used at this point. This limitation created a bit of confusion for me and therefore some time was spent setting up the
relations correctly.
  

- Creating automated testing was an area of spring boot I have not ventured too much into and I therefore had to do some
research making this a time-consuming part of the task and also a tiny bit difficult to use in the start.
  

- Reference loops is an issue I used some time getting all the annotations correct and this was partly due to the next point.
To solve some of the issues I had in my reference loops I had to introduce an ID parameter to my objects that was used in
serialization.
  

- Deserialization of nested objects was a task I did not successfully complete. The nested object of a "creator" (class user)
inside the poll object was not correctly deserialized. I tried multiple approaches including having a separate 
constructor in the user class that took the ID and created a new object with only this info. That approach failed as there would then
be two different objects with same ID. I also tried different approaches with different annotations, but I could not get
the serialization to successfully create a new poll when referencing an existing User.


## Pending issues
As written in the previous paragraph Deserialization of the object when nested was an issue for me during this expass.
I believe the issue is partly due to me not using an entity framework. I believe there is a way of having the
deserialization work without utilizing the entity framework I however did not succeed at this in the time allotted.

## List of steps
- [X] Step 0: Get a HTTP client for testing
- [X] Step 1: Set up Spring project repository
- [X] Step 2: Domain Model
- [X] Step 3: Implement test scenarios
- [ ] Step 4: Implement controller(s)
- [X] Step 5: Automate testing
- [X] Step 6: API documentation (optional)
- [X] Step 7: Build automation (optional)