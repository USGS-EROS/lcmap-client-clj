# lcmap-client-clj

LCMAP REST Service Client for Clojure

[Very WIP ... not ready for use]

## Usage

```bash
$ lein repl
```

```clojure
user=> (require '[lcmap-client.l8.surface-reflectance :as sr])
nil
user=> (sr/get-rod)
{:status 200,
 :headers {"Content-Type" "text/html; charset=utf-8",
           "Content-Length" "20",
           "Server" "http-kit",
           "Date" "Thu, 08 Oct 2015 00:17:13 GMT"},
 :body "TBD: rod forthcoming",
 :request-time 55,
 :trace-redirects ["http://localhost:8080/api/L1/T/Landsat/8/SurfaceReflectance/rod"],
 :orig-content-encoding nil}
user=>
```

## License

TBD
