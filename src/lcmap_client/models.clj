(ns lcmap-client.models
  (:require [clojure.tools.logging :as log]
            [lcmap-client.http :as http]
            [lcmap-client.lcmap :as lcmap]))

(def context (str lcmap/context "/models"))

