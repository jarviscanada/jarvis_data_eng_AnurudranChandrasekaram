###### Question 1: Show all members 

```sql
SELECT *
FROM cd.members
```

###### Exercise 1 : Create member, booking and facilities tables

```sql
CREATE TABLE cd.members
(
    memid INTEGER NOT NULL,
    surname CHARACTER VARYING(200) NOT NULL,
    firstname CHARACTER VARYING(200) NOT NULL,
    address CHARACTER VARYING(300) NOT NULL, 
    zipcode INTEGER NOT NULL,
    telephone CHARACTER VARYING(20) NOT NULL,
    recommendedbyid INTEGER NOT NULL,
    joindate TIMESTAMP NOT NULL,
    CONSTRAINT members_pk PRIMARY KEY (memid),
       CONSTRAINT fk_members_recommendedby FOREIGN KEY (recommendedby)
            REFERENCES cd.members(memid)
);

CREATE TABLE cd.facilities
(
    facid INTEGER NOT NULL,
    name CHARACTER VARYING(100) NOT NULL,
    membercost NUMERIC NOT NULL,
    guestcost NUMERIC NOT NULL,
    initialoutlay NUMERIC NOT NULL,
    monthlymaintenance NUMERIC NOT NULL,
    PRIMARY KEY(facid)
);

CREATE TABLE cd.bookings
(
    bookid INTEGER NOT NULL,
    facid INTEGER NOT NULL,
    memid INTEGER NOT NULL,
    starttime TIMESTAMP NOT NULL,
    slots INTEGER NOT NULL,
    PRIMARY KEY(bookid),
    FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
    FOREIGN KEY (memid) REFERENCES cd.members(memid)
);

```