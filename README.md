CRUD Operation Assessment for Movie App

API Spec:WIP

| URI               | HTTP Method | HTTP Status |      Description     |       Request        |        Response        |
|-------------------|-------------|-------------|----------------------|----------------------|------------------------|
| /movies/ |  GET           |     200     | Retrieves all movies from G Movie's data base    |  | `[{"title":"Incredibles","director":{"firstName":"Brad","lastName":"Bird"},"releaseYear":"2011","actors":[{"firstName":"Pat","lastName":"Joe"},{"firstName":"Steve","lastName":"Smith"}],"description":"","rating":[{"review":"great","rating":5,"user":{"firstName":"Test","lastName":"User"}}]}]` |
| /movies/add  | POST             |     200     | Adds movie to the database and sends the information back on successful save | `{"title":"Incredibles","releaseYear":"2011","description":"Incredible movie"}` | `{"id":2,"title":"Incredibles","director":null,"releaseYear":"2011","description":"Incredible movie","rating":[],"actors":null,"avgRating":null}` |
| /movies/byTitle  | GET             |     200     | Searches for the movie by Title and returns the details if found, otherwise sends a response as "Movie does not exist" | `http://localhost:8082/movies/byTitle?title=Incredibles` | `{"id":2,"title":"Incredibles","director":null,"releaseYear":"2011","description":"Incredible movie","rating":[],"actors":null,"avgRating":null}` |
| /movies/{id}/rating  | PATCH             |     200     | Given a movie available in database, adds the rating and review submitted by user. | `http://localhost:8082/movies/byTitle?title=Incredibles` | `{"id":2,"title":"Incredibles","director":null,"releaseYear":"2011","description":"Incredible movie","rating":[],"actors":null,"avgRating":null}` |