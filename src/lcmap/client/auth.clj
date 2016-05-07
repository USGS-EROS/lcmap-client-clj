(ns lcmap.client.auth
  (:require [clojure.tools.logging :as log]
            [lcmap.client.config :as config]
            [lcmap.client.http :as http]
            [lcmap.client.lcmap :as lcmap]))

;; Note that the client endpoint is defined  using the "/api" prefix, so the
;; following context is appended to that.

;;; Functions in this namespace do not use components, so they
;;; have no other way to get config... yet.
(def auth-config (-> (config/init {}) :lcmap.client.auth))

(def context (str lcmap/context "/auth"))

(defn login [& {:keys [username password]
                :or {username (auth-config :username)
                     password (auth-config :password)}
                :as args}]
  (http/post (str context "/login")
             :clj-http-opts {:form-params {:username username
                                           :password password}}
             :lcmap-opts (or args {})))

(defn get-token [client]
  (get-in client [:cred-mgr :creds :token]))

(defn get-userid [client]
  (get-in client [:cred-mgr :creds :user-id]))

(defn get-username [client]
  (get-in client [:cred-mgr :creds :username]))

(defn get-roles [client]
  (get-in client [:cred-mgr :creds :roles]))

(defn get-email [client]
  (get-in client [:cred-mgr :creds :email]))
