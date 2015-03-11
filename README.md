# closeness-centrality

An implementation of metric called Closeness Centrality which will be used as
score for a given customer.

## Usage

This is a web project created using the Leiningen and Pedestal.

So the first step is start Pedestal server:

```
lein repl
closeness-centrality.server=> (server/start runnable-service)
```

This project implements a restful api. First of all, you should access the entry point `/` to see which routes you can follow: `http://localhost:8000/`

Will return:

```
{"_links":
  {"self":
    {"href":"http://localhost:8080/edges"}
  }
}
```

The endpoint `/edge` can be used to insert new edges. It works sending a POST request with `origin` and `destiny`:

```
curl -d "origin=4&destiny=2" http://localhost:8080/edges
```

The response will be all customers sorted by `score`:

```
[
    {
        "_links": {
            "self": {
                "href": "http://localhost:8080/customer/44"
            },
            "fraudulent": {
                "href": "http://localhost:8080/customer/44/fraudulent"
            }
        },
        "id": "44",
        "score": 0.592814371257485
    },
    {
        "_links": {
            "self": {
                "href": "http://localhost:8080/customer/88"
            },
            "fraudulent": {
                "href": "http://localhost:8080/customer/88/fraudulent"
            }
        },
        "id": "88",
        "score": 0.5857988165680473
    }
]
```

The `fraudulent` endpoint adjusts customer's score according to the following rule:

```
- The fraudulent customer score should be zero.
- Customers directly referred by the "fraudulent" customer should have their score halved.
- Scores of customers indirectly referred by the "fraudulent" customer should be multiplied by a coefficient F:
    F(k) = (1 - (1/2)^k)

    where k is the shortest path from the "fraudulent" customer
    to the customer in question.
```

## Test

Just to run: 

```
lein test
```

## Roadmap

1. Create tests for Pedestal `service.clj` (I've tried this but a lot of erros happened)
2. Validate `origin` and `destiny` parameters
3. Remove all memory data and use a database to store it.
