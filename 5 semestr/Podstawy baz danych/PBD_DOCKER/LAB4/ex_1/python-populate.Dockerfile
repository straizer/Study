FROM python:latest

RUN apt-get -y update

WORKDIR /app

RUN python -m pip install --upgrade pip
RUN pip install pymysql[rsa] faker

ENV PYTHONUNBUFFERED=1

COPY *.py .

CMD [ "python", "./populate_db.py" ]