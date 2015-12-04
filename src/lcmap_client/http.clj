(ns lcmap-client.http
  (:require [clojure.tools.logging :as log]
            [clojure.data.json :as json]
            [clj-http.client :as http]
            [leiningen.core.project :as lein]
            [lcmap-client.config :as config]
            [lcmap-client.util :as util])
  (:refer-clojure :exclude [get]))

(def context "/api")
(def server-version "1.0")
;; XXX once the service goes live, the endpoint will be something like
;;(def endpoint "http://lcmap.usgs.gov")
(def endpoint "http://localhost:8080")
(def client-version (System/getProperty "lcmap-client.version"))
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
(def default-content-type "json")
(def default-options {:endpoint endpoint
                      :return :body
                      :debug false})

(defn format-accept [version content-type]
  ;; XXX split content-type with "/" and use below, e.g.: "application/json"
  (str "application/vnd.usgs.lcmap.v"
       version
       "+"
       content-type))

(defn default-options-as-symbols []
  (into {}
        (map (fn [[k v]]
               [(symbol (name k)) v])
             (seq default-options))))

(defn get-base-headers
  ([]
   (get-base-headers nil nil ""))
  ([version]
   (get-base-headers version nil ""))
  ([version content-type]
   (get-base-headers version content-type ""))
  ([version content-type token]
    (let [api-version (or version (config/get-version server-version))
          api-content-type (or content-type
                               (config/get-content-type default-content-type))]
     {:user-agent user-agent
      :accept (format-accept api-version api-content-type)
      :x-authtoken token})))

(defn get-http-func [method]
  (case method
    :get #'http/get
    :head #'http/head
    :post #'http/post
    :put #'http/put
    :delete #'http/delete
    :options #'http/options
    :copy #'http/copy
    :move #'http/move
    :patch #'http/patch))

(defn get-clj-http-opts [opts & {:keys [debug]}]
  (if debug
    (into opts [util/debug])
    opts))

(defn update-lcmap-opts
  "This combines the options specific to the LCMAP client in the following
  order of precedence:
   * the default options are the least important, overridden by all
   * an explicit map of options overrides the defaults
   * any keyword args provided override the defaults and an options with the
     same keyword"
  [opts]
  (into default-options (util/remove-nil opts)))

(defn combine-http-opts [opts headers request & {:keys [debug]}]
  (let [opts (get-clj-http-opts opts :debug debug)
        request (util/deep-merge request {:headers headers})]
    (util/deep-merge request opts)))

(defn get-keywords [args]
  (util/remove-nil
    (apply dissoc args [:lcmap-opts :clj-http-opts :request])))

(defn- -http-call [method path & {:keys [lcmap-opts clj-http-opts request
                                         headers token]
                                  :or {lcmap-opts {} clj-http-opts {} request {}
                                       headers {} token ""}
                                  :as args}]
  (log/debug "Got args:" args)
  (let [{endpoint :endpoint version :version content-type :content-type
         return :return debug :debug} (update-lcmap-opts lcmap-opts)
        http-func (get-http-func method)
        url (str endpoint path)
        default-headers (get-base-headers version content-type token)
        request (combine-http-opts clj-http-opts
                                   (into default-headers headers)
                                   request
                                   :debug debug)]
    (log/debug "Making request:" request)
    {:result (http-func url request)
     :return return}))

(defn http-call [method path args]
  (let [{result :result
         return :return} (apply -http-call (into [method path] args))]
    (if (= return :body)
        (json/read-str (:body result) :key-fn keyword)
        result)))

(defn get [path & args]
  (http-call :get path args))

(defn head [path & args]
  (http-call :head path args))

(defn post [path & args]
  (http-call :post path args))

(defn put [path & args]
  (http-call :put path args))

(defn delete [path & args]
  (http-call :delete path args))

(defn options [path & args]
  (http-call :options path args))

(defn copy [path & args]
  (http-call :copy path args))

(defn move [path & args]
  (http-call :move path args))

(defn patch [path & args]
  (http-call :patch path args))

