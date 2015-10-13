(ns lcmap-client.l8.core
  (:require [clojure.tools.logging :as log]
            [lcmap-client.http :as lcmap]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str lcmap/context "/L1/T/Landsat/8"))
