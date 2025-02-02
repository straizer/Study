FROM python:latest

RUN apt-get -y update

WORKDIR /app

RUN python -m pip install --upgrade pip
RUN pip install pymysql[rsa]

ENV PYTHONUNBUFFERED=1

COPY *.py .

CMD [ "python", "./copy_db.py" ]