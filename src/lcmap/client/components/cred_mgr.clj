(ns lcmap.client.components.cred-mgr
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [lcmap.client.auth :as auth]))

(defrecord CredentialsManager []
  component/Lifecycle

  (start [component]
    (log/info "Setting up LCMAP client credentials manager ...")
    (let [cfg (:cfg component)
          user-data (auth/login :username (:username cfg)
                                :password (:password cfg))]
      (log/debug "Successfully set up credentials manager.")
      (assoc component :creds user-data)))

  (stop [component]
    (log/info "Shutting down LCMAP client credentials manager ...")
    (log/debug "Component keys" (keys component))
    (assoc component :creds nil)))

(defn new-manager []
  (->CredentialsManager))
