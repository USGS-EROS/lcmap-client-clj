(ns lcmap.client.notifications.ccdc
  (:require [lcmap.client.http :as http]
            [lcmap.client.notifications :as notifications]))

(def context (str notifications/context "/ccdc"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
