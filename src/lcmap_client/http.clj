(ns lcmap-client.http
  (:require [clj-http.client :as http]
            [leiningen.core.project :as lein]
            [lcmap-client.util :as util])
  (:refer-clojure :exclude [get update]))

(def context "/api")

(def client-version (System/getProperty "lcmap-client.version"))
(def server-version "v1.0")
;; XXX once the service goes live, the endpoint will be something like
;;(def endpoint "http://lcmap.usgs.gov")
(def endpoint "http://localhost:8080")
(def project-url (:url (lein/read)))
(def user-agent (str "LCMAP REST Client/"
                     client-version
                     " (Clojure "
                     (clojure-version)
                     "; Java "
                     (System/getProperty "java.version")
                     ") (+"
                     project-url
                     ")"))

(defn format-server-version [version]
  (str "application/vnd.usgs.lcmap." version "+json"))

(def default-headers
  {"user-agent" user-agent
   "accept" (format-server-version server-version)})

;; XXX the debug parameters aren't working right now; need to look into that
(defn set-defaults [req]
  (merge-with #'merge (into {:headers default-headers}
                            util/debug)
              req))

(defn get [path & [req]]
  (:body (http/get (str endpoint path)
                   (set-defaults req))))

(defn head [path & [req]]
  (:body (http/head (str endpoint path)
                    (set-defaults req))))

(defn post [path & [req]]
  (:body (http/post (str endpoint path)
                    (set-defaults req))))

(defn put [path & [req]]
  (:body (http/put (str endpoint path)
                   (set-defaults req))))

(defn delete [path & [req]]
  (:body (http/delete (str endpoint path)
                      (set-defaults req))))

(defn options [path & [req]]
  (:body (http/options (str endpoint path)
                       (set-defaults req))))

(defn copy [path & [req]]
  (:body (http/copy (str endpoint path)
                    (set-defaults req))))

(defn move [path & [req]]
  (:body (http/move (str endpoint path)
                    (set-defaults req))))

(defn patch [path & [req]]
  (:body (http/patch (str endpoint path)
                     (set-defaults req))))
