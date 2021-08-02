test api:

- sbt test

to start the server:

- docker-compose -f docker-compose.yml up 

- curl 'localhost:9000/evaluations?query=mostSpeaches&date=2012'
- curl 'localhost:9000/evaluations?query=mostSpeaches&subject=security'
- curl 'localhost:9000/evaluations'
- curl 'localhost:9000/raw-evaluations'

//TODOS:
  - OpenApi3 (aka swagger documentation for enpoints)
  - CI/cd yml file
  - .env file for various env
  - Deployment somewhere
