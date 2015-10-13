(ns lcmap-client.http
  (:require [clojure.tools.logging :as log]
            [clj-http.client :as http]
            [leiningen.core.project :as lein]
            [lcmap-client.util :as util])
  (:refer-clojure :exclude [get update]))

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
                      :version server-version
                      :content-type default-content-type
                      :return :body
                      :debug false})
(defn format-accept [version content-type]
  (str "application/vnd.usgs.lcmap.v"
       version
       "+"
       content-type))

(defn default-options-as-symbols []
  (into {}
        (map (fn [[k v]]
               [(symbol (name k)) v])
             (seq default-options))))

(defn get-default-headers
  ([]
   (get-default-headers server-version default-content-type ""))
  ([version]
   (get-default-headers version default-content-type ""))
  ([version content-type]
   (get-default-headers version content-type ""))
  ([version content-type api-key]
   {"user-agent" user-agent
    "accept" (format-accept version content-type)
    ;; XXX fill in the auth once the mechanism has been defined
    ;; see the following tickets for more info:
    ;;  * https://my.usgs.gov/jira/browse/LCMAP-66
    ;;  * https://my.usgs.gov/jira/browse/LCMAP-85
    ;;"authorization" ""
    }))

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

(defn combine-http-opts [opts request headers & {:keys [debug]}]
  (let [opts (get-clj-http-opts opts :debug debug)
        request (util/deep-merge headers request)]
    (util/deep-merge request opts)))

(defn get-keywords [args]
  (util/remove-nil
   (apply dissoc args [:lcmap-opts :clj-http-opts :request])))

(defn http-call [method path & {:keys [lcmap-opts clj-http-opts request]
                                :as args}]
  (let [{endpoint :endpoint version :version content-type :content-type
         return :return debug :debug} (update-lcmap-opts lcmap-opts)
        http-func (get-http-func method)
        api-key (:api-key lcmap-opts)
        url (str endpoint path)
        default-headers (get-default-headers version content-type api-key)
        request (combine-http-opts clj-http-opts request default-headers
                                   :debug debug)
        result (http-func url request)]
    (if (= return :body)
      (:body result)
      result)))
  
(defn get [path & args]
  (apply http-call (into [:get path] args)))

(defn head [path & args]
  (apply http-call (into [:head path] args)))

(defn post [path & args]
  (apply http-call (into [:post path] args)))

(defn put [path & args]
  (apply http-call (into [:put path] args)))

(defn delete [path & args]
  (apply http-call (into [:delete path] args)))

(defn options [path & args]
  (apply http-call (into [:options path] args)))

(defn copy [path & args]
  (apply http-call (into [:copy path] args)))

(defn move [path & args]
  (apply http-call (into [:move path] args)))

(defn patch [path & args]
  (apply http-call (into [:patch path] args)))

