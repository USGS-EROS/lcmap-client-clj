(ns lcmap-client.core
  (:require [clj-http.client :as http]
            [leiningen.core.project :as lein])
  (:declare make-server-version))

(def client-version (System/getProperty "lcmap-client.version"))
(def server-version (make-server-version "v1"))
;; XXX once the service goes live, the endpoint will be something like
;;(def endpoint "http://lcmap.usgs.gov/api")
(def endpoint "http://localhost:8080/api")
(def project-url (:url (lein/read)))
(def default-headers {"User Agent" user-agent
                      "Accept" server-version})
(def user-agent (str "LCMAP REST Client/"
                     client-version
                     " (Clojure "
                     (clojure-version)
                     "; Java "
                     (System/getProperty "java.version")
                     ") (+"
                     project-url
                     ")"))

(defun make-server-version (version)
  (str "application/vnd.usgs.lcmap." version "+json"))

(defn get [path & [req]]
  (http/get (str endpoint path)
            (merge default-headers req)))

(defn head [path & [req]]
  (http/head (str endpoint path)
             (merge default-headers req)))

(defn post [path & [req]]
  (http/post (str endpoint path)
             (merge default-headers req)))

(defn put [path & [req]]
  (http/put (str endpoint path)
            (merge default-headers req)))

(defn delete [path & [req]]
  (http/delete (str endpoint path)
               (merge default-headers req)))

(defn options [path & [req]]
  (http/options (str endpoint path)
                (merge default-headers req)))

(defn copy [path & [req]]
  (http/copy (str endpoint path)
             (merge default-headers req)))

(defn move [path & [req]]
  (http/move (str endpoint path)
             (merge default-headers req)))

(defn patch [path & [req]]
  (http/patch (str endpoint path)
              (merge default-headers req)))
