FROM python:latest

RUN apt-get -y update
RUN apt-get clean && apt-get autoclean

WORKDIR /app

RUN pip install pymongo flask

ENV PYTHONUNBUFFERED=1

CMD [ "python", "-m" , "flask", "run", "--host=0.0.0.0"]
