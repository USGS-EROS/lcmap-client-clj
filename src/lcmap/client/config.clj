(ns lcmap.client.config
  (:require [lcmap.config.helpers :refer :all]
            [lcmap.logger.config :as logger-cfg]
            [schema.core :as schema]))

;; CLI options for config only, not command parameters for CLI tools!
(def opt-spec [])

(def client-schema
  {:lcmap.client {:username schema/Str
                  :password schema/Str
                  :version schema/Str
                  :endpoint schema/Str
                  :content-type schema/Str
                  schema/Keyword schema/Str}})

(def cfg-schema
  (merge client-schema
         logger-cfg/cfg-schema
         {schema/Keyword schema/Any}))

(def defaults
  {:ini *lcmap-config-ini*
   :args *command-line-args*
   :spec opt-spec
   :schema cfg-schema})
