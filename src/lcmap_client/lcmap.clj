(ns lcmap-client.lcmap
  (:require [clojure.tools.logging :as log]
            [lcmap-client.config :as config]
            [lcmap-client.http :as http]))

(def context "/api")

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))

(defn follow-link [result & {:keys [] :as args}]
  (let [path (get-in result [:result :link :href])]
    (log/debug "Following path:" path)
    (log/debug "Passing options: " args)
    (http/get
      path
      :lcmap-opts (or args {}))))
