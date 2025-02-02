FROM python:latest

RUN apt-get -y update
RUN apt-get clean && apt-get autoclean

WORKDIR /app

RUN pip install pymysql

ENV PYTHONUNBUFFERED=1

COPY main.py .

CMD [ "python", "./main.py" ]
