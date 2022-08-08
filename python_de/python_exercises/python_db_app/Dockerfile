FROM python:3.8-slim-buster

WORKDIR /app

COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt

COPY . .

CMD [ "python", "python_db_app.py", "localhost", "5432", "postgres", "password", "exercises", "cd.members", "members.csv "]