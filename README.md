Trava
=====

## API

To run the API with docker:

    docker build -t trava-api .


To run the APi with docker:

    docker run -it --rm -p 8080:8080 trava-api


To run API and PostgreSQL database, run:

    docker-compose up -d
