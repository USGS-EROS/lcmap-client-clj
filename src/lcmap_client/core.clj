(ns lcmap-client.core
  (:require [clojure.tools.logging :as log]
            [lcmap-client.http :refer :all])
  (:refer-clojure :exclude [get update]))

(def noop :noop)
