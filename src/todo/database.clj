(ns todo.database
  (:require [korma.db :as korma]))

(def db-connection-info 
    (korma/postgres
        {:db "clojure-api"
        :user "postgres"
        :password "root"}))

; set up korma
(korma/defdb db db-connection-info)