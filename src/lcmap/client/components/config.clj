;;;; Event LCMAP REST Service system component
;;;; For more information, see the module-level code comments in
;;;; lcmap.client.components.
(ns lcmap.client.components.config
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [lcmap.config.components.config :as config]))

(defrecord Configuration [cfg-opts]
  component/Lifecycle

  (start [component]
    (log/info "Setting up LCMAP client configuration ...")
    (let [cfg-map (config/init-cfg cfg-opts)]
      (log/debug "Successfully generated LCMAP client configuration.")
      (merge component cfg-map)))

  (stop [component]
    (log/info "Tearing down LCMAP configuration ...")
    (log/debug "Component keys" (keys component))
    (assoc component :cfg-opts nil)))

(defn new-configuration [cfg-opts]
  (->Configuration cfg-opts))
