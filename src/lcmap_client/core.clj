(ns lcmap-client.core
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [lcmap-client.components :as components]))

(defn connect []
  (component/start
    (components/init)))

(def disconnect #'components/stop)

(defn reconnect [client]
  (disconnect client)
  (connect))

(def refresh #'reconnect)

(defn get-pool [client]
  (get-in client [:conn-mgr :pool]))


