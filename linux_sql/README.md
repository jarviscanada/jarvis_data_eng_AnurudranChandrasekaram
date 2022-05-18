# Linux Cluster Monitoring Agent

## Introduction
This project is used to retrieve hardware information of different servers to determine how much usage each server uses such as how much memory it uses or CPU. The tools used to create this project are bash scripts, postgress sql database, sql, and docker.

## Quick Start
1. Start a psql instance using psql_docker.sh
    ```
    ./scripts/psql_docker.sh start|stop|create [db_username][db_password]

    ```
- Create tables using ddl.sql
    ```
    Do steps 1-3 only once to create databse

    1. connect to the psql instance
    psql -h localhost -U postgres -W

    2. list all database
    postgres=# \l

    3. create a database
    postgres=# CREATE DATABASE host_agent;

    4. Run the create ddl script to create the host_usage and host_info tables
    psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
    ```
- Insert hardware specs data into the DB using host_info.sh
    ```
    bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
    ```
- Insert hardware usage data into the DB using host_usage.sh
    ```
    bash scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
    ```
- Crontab setup to record host usage every minute
    ```
    #edit crontab jobs
    bash> crontab -e

    #add this to crontab
    * * * * * bash /home/centos/dev/jrvs_data_eng_[FirstnameLastname]/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log
    ```

## Implemenation
The project is implemented through a docker container. It gets the host's info of memeory and CPU and gets the host usage of this memory and CPU usage through bash scripts. Which those values get added to their respective tables in the database. This project is only a MVP and is ran on one host only right now.

## Architecture
![Architecture of the linux/sql project](/assets/architecture.png)

## Scripts
Shell script description and usage
- psql_docker.sh
    This script is used to start the dicker container so your are able to work in that environment
    ```
    Usage:
    ./scripts/psql_docker.sh start|stop|create [db_username][db_password]

    where you first create the container setting the username and password and then being able to start or stop the container from running

    ```
- host_info.sh  
    Script collects the hardware information of the host computer and inserts those data to the postgress database
    ```
    Usage: 
    ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
    ```
- host_usage.sh  
    This script collects the server usage data and inserts it to the database
    ```
    Usage: 
    ./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
    ```
- crontab  
This Script sets the host_usage to run every minute
- queries.sql   
This query has three queries, first on is to group harware info based on CPU number. Second script average memory used for each host every 5 minutes. Last query is to detect host failure which is whenever host doesn't insert minimum three data points every 5 minutes.

## Database Modeling
`Host_info`
| Column name      | Description | Data type     | Constraints   |  
| :---        |    :----:   |          ---: |  :----:   | 
| id      | Unique id number for each node       | SERIAL   | Primary Key   |
| hostname      | The full name of the host machine       | VARCHAR   | Unique   |
| cpu_number      | the number of the cpu       | INTEGER   | Not Null   |
| cpu_architecture      |architecture of host computer       | VARCHAR   | Not Null   |
| cpu_model      | The model of the cpu       | VARCHAR   | Not Null   |
| cpu_mhz      | cpu speed        | FLOAT(3)   | Not Null   |
| L2_cache      | cache memory in kB      | INTEGER   | Not Null   |
| total_mem      | Total memory of the CPU in kB      | INTEGER   | Not Null   |
| timestamp      | Current time of retriving in UTC format      | TIMESTAMP   | Not Null   |

`host_usage`

| Column name      | Description | Data type     | Constraints   |  
| :---        |    :----:   |          ---: |  :----:   | 
| timestamp      | Current time of retriving in UTC format      | TIMESTAMP   | Not Null   |
| host_id      | Unique id number for each node       | SERIAL   | Foreign Key   |
| memory_free      | Free memory of the CPU in mB       | INTEGER   | Not Null   |
| cpu_idle      | number of CPUs idle      | INTEGER   | Not Null   |
| cpu_kernel      | number CPUs in kernel      | INTEGER   | Not Null   |
| disk_io      | number of disk_io      | INTEGER   | Not Null   |
| disk_available      | number of available disks      | INTEGER   | Not Null   |


# Test

The bash scripts and SQL queries are tested in a docker container. These scripts are tested by first creating the database and tables and then running the scripts to add the values. Then can run the queries in sql/queries.sql file to see if queries are ran properly and also crontab executes properly.

# Deployment
The code base is stored on GitHub and the postgress SQL is used by the docker container.

# Improvements
- Handle hardware update: being able to automatically update the data of the host machines whenever there is a hardware update is a plus.
- Add more sql queries to be able to get more type of informations on the host servers
- Being able to have efficent storage since crontab is running host_usage every minute, the storage in the database will be overwhelemed and should have a way to delete the data after analyzing it every day or so
