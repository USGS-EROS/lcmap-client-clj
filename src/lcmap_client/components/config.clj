;;;; Event LCMAP REST Service system component
;;;; For more information, see the module-level code comments in
;;;; lcmap-client.components.
(ns lcmap-client.components.config
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [lcmap-client.config :as config]
            [lcmap-client.util :as util]))

(defn update-namespaces [cfg]
  {:logging-namespaces
    (->> (string/split (:logging-namespaces cfg) #" ")
         (map symbol)
         (into []))})

(defn update-loglevel [cfg]
  {:log-level
    (-> (:log-level cfg)
        (string/lower-case)
        (keyword))})

(defrecord Configuration []
  component/Lifecycle

  (start [component]
    (log/info "Setting up LCMAP client configuration ...")
    (let [cfg ((config/read) (keyword "LCMAP Client"))]
      (log/debug "Successfully generated LCMAP client configuration.")
      (into cfg [(update-namespaces cfg)
                 (update-loglevel cfg)])))

  (stop [component]
    (log/info "Tearing down LCMAP configuration ...")
    (log/debug "Component keys" (keys component))
    (assoc component :cfg nil)))

(defn new-configuration []
  (->Configuration))
