;;;; Event LCMAP REST Service system component
;;;; For more information, see the module-level code comments in
;;;; lcmap.client.components.
(ns lcmap.client.components.config
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [lcmap.client.config :as config]
            [lcmap.client.util :as util]))

(defrecord Configuration []
  component/Lifecycle

  (start [component]
    (log/info "Setting up LCMAP client configuration ...")
    (let [cfg ((config/read) (keyword "LCMAP Client"))]
      (log/debug "Successfully generated LCMAP client configuration.")
      cfg))

  (stop [component]
    (log/info "Tearing down LCMAP configuration ...")
    (log/debug "Component keys" (keys component))
    (assoc component :cfg nil)))

(defn new-configuration []
  (->Configuration))
