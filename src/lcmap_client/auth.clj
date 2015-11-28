(ns lcmap-client.auth
  (:require [clojure.tools.logging :as log]
            [lcmap-client.http :as http]
            [lcmap-client.lcmap :as lcmap]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

(def context (str lcmap/context "/auth"))

(defn login [& {:keys [username password] :as args}]
  (http/post (str context "/login")
             :clj-http-opts {:form-params {:username username
                                           :password password}}
             :lcmap-opts (or args {})))
