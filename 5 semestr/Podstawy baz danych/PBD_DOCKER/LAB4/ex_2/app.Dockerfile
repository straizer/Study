FROM python:latest

RUN apt-get -y update
RUN apt-get clean && apt-get autoclean

WORKDIR /app

RUN python -m pip install --upgrade pip
RUN pip install pymongo flask

ENV PYTHONUNBUFFERED=1

HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --start-interval=1s --retries=5 CMD curl --fail http://localhost:5000/healthcheck || exit 1

CMD [ "flask", "run", "--host=0.0.0.0", "--debug" ]
