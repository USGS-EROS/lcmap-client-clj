(ns lcmap-client.components.conn-mgr
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [clj-http.conn-mgr :as conn-mgr]))

(defrecord ConnectionManager []
  component/Lifecycle

  (start [component]
    (log/info "Setting up LCMAP HTTP client connection manager ...")
    (let [pool (conn-mgr/make-reusable-conn-manager {:threads 4
                                                     :timeout 5
                                                     :default-per-route 4})]
      (log/debug "Successfully set up connection manager.")
      (assoc component :pool pool)))

  (stop [component]
    (log/info "Shutting down LCMAP HTTP client connection manager ...")
    (log/debug "Component keys" (keys component))
    (conn-mgr/shutdown-manager (:pool component))
    (assoc component :pool nil)))

(defn new-manager []
  (->ConnectionManager))
