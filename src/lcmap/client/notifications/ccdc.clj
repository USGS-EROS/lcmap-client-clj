(ns lcmap.client.notifications.ccdc
  (:require [clojure.tools.logging :as log]
            [lcmap.client.http :as http]
            [lcmap.client.notifications :as notifications]
            [lcmap.client.lcmap :as lcmap]))

(def context (str notifications/context "/ccdc"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
