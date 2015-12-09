(ns lcmap-client.dev
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clojure.tools.namespace.repl :as repl]
            [com.stuartsierra.component :as component]
            [lcmap-client.auth :as auth]
            [lcmap-client.components :as components]
            [lcmap-client.config :as config]
            [lcmap-client.core :as lcmap]
            [lcmap-client.data :as data]
            [lcmap-client.data.sample_os_process]
            [lcmap-client.http :as http]
            [lcmap-client.jobs :as jobs]
            [lcmap-client.jobs.sample_os_process]
            [lcmap-client.models :as models]
            [lcmap-client.models.sample_os_process]
            [lcmap-client.notifications :as notifications]
            [lcmap-client.operations :as operations]
            [lcmap-client.system :as system]
            [lcmap-client.users :as users]
            [lcmap-client.util :as util]))

(def state :stopped)
(def system nil)

(defn init []
  (if (util/in? [:initialized :started :running] state)
    (log/error "System has aready been initialized.")
    (do
      (alter-var-root #'system
        (constantly (components/init)))
      (alter-var-root #'state (fn [_] :initialized))))
  state)

(defn deinit []
  (if (util/in? [:started :running] state)
    (log/error "System is not stopped; please stop before deinitializing.")
    (do
      (alter-var-root #'system (fn [_] nil))
      (alter-var-root #'state (fn [_] :uninitialized))))
  state)

(defn start []
  (if (nil? system)
    (init))
  (if (util/in? [:started :running] state)
    (log/error "System has already been started.")
    (do
      (alter-var-root #'system component/start)
      (alter-var-root #'state (fn [_] :started))))
  state)

(defn stop []
  (if (= state :stopped)
    (log/error "System already stopped.")
    (do
      (alter-var-root #'system
        (fn [s] (when s (component/stop s))))
      (alter-var-root #'state (fn [_] :stopped))))
  state)

(defn run []
  (if (= state :running)
    (log/error "System is already running.")
    (do
      (if (not (util/in? [:initialized :started :running] state))
        (init))
      (if (not= state :started)
        (start))
      (alter-var-root #'state (fn [_] :running))))
  state)

(defn -refresh
  ([]
    (repl/refresh))
  ([& args]
    (apply #'repl/refresh args)))

(defn refresh [& args]
  "This is essentially an alias for clojure.tools.namespace.repl/refresh."
  (if (util/in? [:started :running] state)
    (stop))
  (apply -refresh args))

(defn reset []
  (stop)
  (deinit)
  (config/read :force-reload)
  (refresh :after 'lcmap-client.dev/run))

(def reload #'reset)
