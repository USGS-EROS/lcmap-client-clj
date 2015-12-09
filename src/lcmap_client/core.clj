(ns lcmap-client.core
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [lcmap-client.components :as components]
            [lcmap-client.http :as http]))

(defn connect []
  (component/start
    (components/init)))

(def disconnect #'components/stop)

(defn reconnect [client]
  (disconnect client)
  (connect))

(def refresh #'reconnect)

(defn follow-link [result & {:keys [] :as args}]
  (let [path (get-in result [:result :link :href])]
    (log/tracef "Following path %s with options: %s" path args)
    (http/get
      path
      :lcmap-opts (or args {}))))
