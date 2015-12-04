(ns lcmap-client.lcmap
  (:require [clojure.tools.logging :as log]
            [lcmap-client.config :as config]
            [lcmap-client.http :as http]))

(def context "/api")

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))

(defn follow-link [result & args]
  (let [url (str (config/get-endpoint)
                  "/"
                  (get-in result [:result :link :href]))]
    (log/debug "Following URL:" url)
    (http/get
      url
      :lcmap-opts (or args {}))))
