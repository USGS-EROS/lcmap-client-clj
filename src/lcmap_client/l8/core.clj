(ns lcmap-client.l8.core
  (:require [lcmap-client.http :as http]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context "/L1/T/Landsat/8")
