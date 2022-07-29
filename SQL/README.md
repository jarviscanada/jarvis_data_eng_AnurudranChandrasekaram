#### Question 1: Show all members 

```sql
SELECT *
FROM cd.members
```

#### Exercise 1 : Create member, booking and facilities tables

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

#### Exercise 2 : Modifying data

```sql
1. 
insert into cd.facilities
(facid, Name, membercost, guestcost, initialoutlay, monthlymaintenance)
values (9, 'Spa', 20, 30, 100000, 800);

2. 
insert into cd.facilities
(facid, Name, membercost, guestcost, initialoutlay, monthlymaintenance)
select (select max(facid) from cd.facilities) + 1, 'Spa', 20, 30, 100000, 800;

3. 
UPDATE cd.facilities 
SET initialoutlay = 10000
WHERE facid = 1;

4. 
UPDATE cd.facilities
SET 
membercost = (SELECT membercost FROM cd.facilities WHERE facid = 0) *1.1,
guestcost = (SELECT guestcost FROM cd.facilities WHERE facid = 0) *1.1
WHERE facid = 1;

5.
DELETE from cd.bookings;

6.
DELETE from cd.members
WHERE memid = 37;
```

#### Exercise 2 : Basic
```sql
1. 
SELECT name,
case when (monthlymaintenance > 100) then
'expensive'
else
'cheap'
end as cost
from cd.facilities;

2.
SELECT surname
FROM cd.members
UNION
SELECT name
FROM cd.facilities;
```

#### Exercise 2 : Join
```sql
1.
SELECT starttime
FROM cd.bookings
WHERE memid = (SELECT memid
FROM cd.members
WHERE firstname = 'David'
and surname = 'Farrell');

2.
SELECT bks.starttime as start, facs.name as name
FROM cd.bookings bks
INNER JOIN cd.facilities facs on bks.facid = facs.facid
WHERE facs.name LIKE '%Tennis Court%'
and bks.starttime >='2012-09-21'
and bks.starttime <'2012-09-22'
ORDER BY bks.starttime;

3.
SELECT member.firstname as memfname, member.surname as memsname, rec.firstname as recfname, rec.surname as recsname
FROM cd.members member
LEFT OUTER JOIN cd.members rec on member.recommendedby = rec.memid
ORDER BY memsname, memfname;

4.
SELECT distinct mem.firstname, mem.surname
FROM cd.members mem
INNER JOIN cd.members ref on mem.memid = ref.recommendedby
ORDER BY mem.surname, mem.firstname;

5. 
SELECT distinct mems.firstname || ' ' ||  mems.surname as member,
(SELECT recs.firstname || ' '|| recs.surname as recommender
FROM cd.members recs
WHERE recs.memid = mems.recommendedby)
FROM cd.members mems
ORDER BY member;
```
#### Exercise 2 : Aggregation
```sql
1.
SELECT recommendedby, COUNT(*)
FROM cd.members
WHERE recommendedby is not NULL
GROUP BY recommendedby
ORDER BY recommendedby;

2.
SELECT facid, SUM(slots) as "Total Slots"
FROM cd.bookings
GROUP BY facid
ORDER BY facid;

3.
SELECT facid, SUM(slots) as "Total Slots"
FROM cd.bookings
WHERE starttime >= '2012-09-01'
and starttime < '2012-10-01'
GROUP BY facid
ORDER BY "Total Slots";

4.
SELECT facid, extract(month from starttime) as month, sum(slots) as "Total Slots"
FROM cd.bookings
WHERE extract(year from starttime) = 2012
GROUP BY facid, month
ORDER BY facid, month;

5.
SELECT COUNT(distinct memid)
FROM cd.bookings;

6.
SELECT mems.surname, mems.firstname, mems.memid, MIN(bks.starttime)
FROM cd.members mems INNER JOIN cd.bookings bks on mems.memid = bks.memid
WHERE starttime >= '2012-09-01'
GROUP BY mems.surname, mems.firstname, mems.memid
ORDER BY mems.memid;

7.
SELECT (SELECT COUNT(memid)
FROM cd.members), mems.firstname, mems.surname
FROM cd.members mems
ORDER BY joindate;

8.
select row_number() over(), firstname, surname
from cd.members
order by joindate;

9.
SELECT facid, total from
(SELECT facid, SUM(slots) total, rank() over(Order by SUM(slots) desc) rank
FROM cd.bookings
GROUP BY facid) as ranked
WHERE rank = 1;
```

#### Exercise 2 : String
```sql
1.
SELECT surname  || ', ' || firstname as name
FROM cd.members;

2.
SELECT *
FROM cd.facilities
WHERE upper(name) LIKE 'TENNIS%';

3.
SELECT memid, telephone
FROM cd.members
WHERE telephone LIKE '(___) ___-____';

4.
SELECT SUBSTR(surname, 1, 1) as letter, COUNT(*)
FROM cd.members
GROUP BY letter
ORDER BY letter;
```
