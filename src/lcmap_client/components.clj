;;;; LCMAP client components
;;;;
;;;; Large applications often consist of many stateful processes which must be
;;;; started and stopped in a particular order. The component model makes
;;;; those relationships explicit and declarative, instead of implicit in
;;;; imperative code. The LCMAP REST service project is one such application
;;;; and early on in its existence it was refactored to support the
;;;; component/dependency-injection approach.
;;;;
;;;; For more information on the Clojure component library, see:
;;;;  * https://github.com/stuartsierra/component
;;;;  * https://www.youtube.com/watch?v=13cmHf_kt-Q
(ns lcmap-client.components
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [lcmap-client.components.config :as config]
            [lcmap-client.components.logger :as logger]))

(defn init []
  (component/system-map
    :cfg (config/new-configuration)
    :logger (component/using
              (logger/new-logger)
              [:cfg])))
